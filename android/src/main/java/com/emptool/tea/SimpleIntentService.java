package com.emptool.tea;

import android.util.Log;
import android.content.Intent;
import android.app.IntentService;

public class SimpleIntentService extends IntentService {
                                         //12345678901234567890
    private static final String LOG_TAG = "SimpleIntentService"; // This needs to within 23 char

    public static final String PARAM_IN_MSG = "imsg";
    public static final String PARAM_OUT_MSG = "omsg";
 
    public SimpleIntentService() {
        super("SimpleIntentService");

        Log.v(LOG_TAG, "at SimpleIntentService constructor");
    }
 
    @Override
    protected void onHandleIntent(Intent intent) {
 
        String msg = intent.getStringExtra(PARAM_IN_MSG);

        for (int i = 0; i < 100; i++) {
            try {
                Log.v(LOG_TAG, String.format("onHandleIntent %d", i));
                Log.v(LOG_TAG, msg);
                Thread.sleep(1000);
            }
            catch (Exception e) {
                Log.v(LOG_TAG, e.toString());
            }
        }
        
        // SystemClock.sleep(30000); // 30 seconds
        // String resultTxt = msg + " "
        //     + DateFormat.format("MM/dd/yy h:mmaa", System.currentTimeMillis());
    }
}