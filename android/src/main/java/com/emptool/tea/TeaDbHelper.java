package com.emptool.tea;

import java.util.List;
import java.util.ArrayList;

import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;


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

    public long GetCount(String status)
    {
        String selection = "status = ?";
        String[] selectionArgs = { status };


        SQLiteDatabase db = this.getReadableDatabase();
        long count = DatabaseUtils.queryNumEntries(
            db, "scan_data", selection, selectionArgs);
        db.close();
        return count;
    }

    public ArrayList GetRecords(String status)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection of columns from the database, that you use from query.
        String[] projection = {
            "id",
            "tag",
            "data",
            "cre_dt"
        };

        // Filter results WHERE "title" = 'My Title'
        String selection = "status = ?";
        String[] selectionArgs = { status };

        // How you want the results sorted in the resulting Cursor
        String sortOrder = "id ASC";

        Cursor cursor = db.query(
            "scan_data",                     // The table to query
            projection,                               // The columns to return
            selection,                                // The columns for the WHERE clause
            selectionArgs,                            // The values for the WHERE clause
            null,                                     // don't group the rows
            null,                                     // don't filter by row groups
            sortOrder                                 // The sort order
            );

        List items = new ArrayList<>();
        while (cursor.moveToNext()) {
            // public class ScanDataRecord {
            // public int Id;
            // public String Tag;
            // public String Data;
            // public String CreDt;

            ScanDataRecord newRecord = new ScanDataRecord();
            newRecord.Id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            newRecord.Tag = cursor.getString(cursor.getColumnIndexOrThrow("tag"));
            newRecord.Data = cursor.getString(cursor.getColumnIndexOrThrow("data"));
            newRecord.CreDt = cursor.getString(cursor.getColumnIndexOrThrow("cre_dt"));


            //String itemId = cursor.getString(cursor.getColumnIndexOrThrow(0));
            //itemIds.add(itemId);
            items.add(newRecord);
        }

        cursor.close();

        return (ArrayList)items;
    }


    public void Update(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        String title = "MyNewTitle";
        ContentValues values = new ContentValues();
        values.put("status", 1);

        // Which row to update, based on the title
        String selection = "id = ?";
        String[] selectionArgs = { Integer.toString(id) };

        int count = db.update(
            "scan_data",
            values,
            selection,
            selectionArgs);
    }

    public void Reset(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        String title = "MyNewTitle";
        ContentValues values = new ContentValues();
        values.put("status", 0);

        // Which row to update, based on the title
        String selection = "id = ?";
        String[] selectionArgs = { Integer.toString(id) };

        int count = db.update(
            "scan_data",
            values,
            selection,
            selectionArgs);
    }

    public void ClearRecords()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define 'where' part of query.
        String selection = "status = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { "1" };
        
        // Issue SQL statement.
        int deletedRows = db.delete("scan_data", selection, selectionArgs);
    }

}