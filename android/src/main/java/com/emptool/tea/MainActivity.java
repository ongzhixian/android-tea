package com.emptool.tea;

import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;

// ZX: Status OK
public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "MainActivity"; // This needs to within 23 char

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    // ZX: We do not need menu for this activity; Comment out 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // switch (item.getItemId()) {
        // // action with ID action_refresh was selected
        // case R.id.show_scanner:
        //     Log.v(LOG_TAG, "onOptionsItemSelected - show_scanner");
        //     Intent intent = new Intent(this, ScanBarcodeActivity.class);
        //     intent.putExtra(ScanBarcodeActivity.SCANNER_MODE, ScanBarcodeActivity.SCANNER_MODE_DEBUG);
        //     startActivity(intent);
        //     break;
        // default:
        //     break;
        // }
        return true;
    }

    // KIV: Until we want to implement network monitoring
    // Reference:
    // https://www.grokkingandroid.com/android-getting-notified-of-connectivity-changes/
    //https://stackoverflow.com/questions/25678216/android-internet-connectivity-change-listener
    //https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
    // public bool HasConnectivity()
    // {
    //     ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
    //     NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    //     boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    //     return isConnected;
    //     // boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
    // }

    public void scanBarcode(View view) {
        Log.v(LOG_TAG, "scan barcode");

        // Intent intent = new Intent(this, BorrowBlazerActivity.class);
        // startActivity(intent);

        try
        {
            Intent intent = new Intent(this, ScanBarcodeActivity.class);

            Log.v(LOG_TAG, "putExtra");
            intent.putExtra(ScanBarcodeActivity.SCANNER_MODE, ScanBarcodeActivity.SCANNER_MODE_DEBUG);

            Log.v(LOG_TAG, "startActivity");
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG, "scanBarcode error", ex);
        }

        
    }

    public void returnBlazer(View view) {
        Log.v(LOG_TAG, "return blazer");

        // Intent intent = new Intent(this, ReturnBlazerActivity.class);
        // startActivity(intent);

        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 1);
    }

    // public void viewLoans(View view) {
    //     Log.v(LOG_TAG, "viewStats");

    //     Intent intent = new Intent(this, ViewLoansActivity.class);
    //     startActivity(intent);
    // }
    

    // ZX: Can delete (probably)
    // public void viewStatistics(View view) {
    //     Log.v(LOG_TAG, "viewStats");
    //     Intent intent = new Intent(this, ViewStatisticsActivity.class);
    //     startActivity(intent);
    // }

    // ZX: Can delete (probably)
    // public void runScanner(View view) {
    //     Log.v(LOG_TAG, "runScanner");
    //     // Intent intent = new Intent(this, ScannerActivity.class);
    //     // startActivity(intent);
    // }

    // ZX: Can delete (probably)
    // public void viewDatabase(View view) {
    //     Log.v(LOG_TAG, "viewDatabase");
    //     Intent intent = new Intent(this, ViewDatabaseActivity.class);
    //     startActivity(intent);
    // }

    // ZX: Can delete (probably)
    // public void sendMessage(View view) {
    //     Do something in response to button
    //     Intent intent = new Intent(this, DisplayMessageActivity.class);
    //     EditText editText = (EditText) findViewById(R.id.editText);
    //     String message = editText.getText().toString();
    //     intent.putExtra(EXTRA_MESSAGE, message);
    //     startActivity(intent);
    // }

}
