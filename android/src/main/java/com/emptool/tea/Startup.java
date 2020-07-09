package com.emptool.tea;

import android.content.Intent;
import android.util.Log;
import android.app.Application;

public class Startup extends Application {
    
    private static final String LOG_TAG = "Startup"; // This needs to within 23 char

    @Override
    public void onCreate(){
        super.onCreate();
        // Place your code here which will be executed only once

        Log.v(LOG_TAG, "[Startup] Application");

        Log.v(LOG_TAG, "start bg intent");
        String strInputMsg = "some sample txt for intent";
        Intent msgIntent = new Intent(this, SimpleIntentService.class);
        msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, strInputMsg);
        startService(msgIntent);
    }
}