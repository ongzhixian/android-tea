package com.emptool.tea;

import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;


public class TeaDbHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = "TeaDbHelper"; // This needs to within 23 char

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tea.sqlite3";

    public TeaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v(LOG_TAG, "ctor");
    }

    public void onCreate(SQLiteDatabase db) {
        Log.v(LOG_TAG, "onCreate");

        String sql = 
            "CREATE TABLE `scan_data` (" +
            "    `id`	    INTEGER," +
            "    `tag`	    TEXT," +
            "    `data`	    TEXT," +
            "    `cre_dt`	TEXT DEFAULT CURRENT_TIMESTAMP," +
            "    `status`	INTEGER DEFAULT 0," +
            "    PRIMARY KEY (`id`)" +
            ");";

        db.execSQL(sql);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        Log.v(LOG_TAG, "onUpgrade");
        String sql = "DROP TABLE `scan_data`;";
        
        db.execSQL(sql);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(LOG_TAG, "onDowngrade");
        onUpgrade(db, oldVersion, newVersion);
    }

    public void SaveScanData(String tag, String data) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put("tag", tag);
        values.put("data", data);
        
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert("scan_data", null, values);

        Log.v(LOG_TAG, "scan_data newRowId is " + Long.toString(newRowId));
    }
}