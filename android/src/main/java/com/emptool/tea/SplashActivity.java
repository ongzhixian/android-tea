package com.emptool.tea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Simulate loading of resources use by application here
	    android.os.SystemClock.sleep(1667);

        // After we load the resource, run main activity
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        
        // close splash activity
        finish();
    }
}
