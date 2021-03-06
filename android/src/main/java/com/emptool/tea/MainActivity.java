package com.emptool.tea;

import java.util.ArrayList;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;



// ZX: Status OK
public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = "MainActivity"; // This needs to within 23 char

    // Constants use for main activity
    public static final String TAG = "TAG";

    EditText tagEditText = null;
    TextView pendingUploadTextView = null;

    Thread uploadThread = null;
    TeaDbHelper db = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        tagEditText = (EditText)findViewById(R.id.tag_edittext);
        pendingUploadTextView = (TextView)findViewById(R.id.pending_upload_textview);

        db = new TeaDbHelper(this);
        pendingUploadTextView.setText(
            String.format("Pending upload(s): %d", db.GetCount("0"))
        );
    }

    // void startBackgroundThread() {
    //     uploadThread = new Thread(new Runnable() {
    //         @Override
    //         public void run() {

    //             for (int i = 0; i < 5; i++) {

    //                 try {

    //                     Thread.sleep(2000);
    //                     pendingUploadTextView.setText(Integer.toString(i));
    //                     Log.v(LOG_TAG, "startBackgroundThread");

    //                 } catch (Exception e) {
    //                     pendingUploadTextView.setText("err");
    //                 }
    //             }

    //             // a potentially time consuming task
    //             // final Bitmap bitmap = processBitMap("image.png");
    //             // imageView.post(new Runnable() {
    //             //     public void run() {
    //             //         imageView.setImageBitmap(bitmap);
    //             //     }
    //             // });
    //         }
    //     });

    //     uploadThread.start();
    // }

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
            intent.putExtra(MainActivity.TAG, tagEditText.getText().toString()); 

            Log.v(LOG_TAG, "startActivity");
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Log.e(LOG_TAG, "scanBarcode error", ex);
        }

        
    }

    public void uploadScanned(View view) {
        Log.v(LOG_TAG, "uploadScanned");


        // DoApiCall("android asdad some mess");

        ArrayList recordList = db.GetRecords("0");

        int listLen = recordList.size();

        JSONArray scanDataList = new JSONArray();
        // for(int i=0; i < yourarray.length(); i++) {
        // }

        // for (int i = 0; i < recordList.size(); i++)
        // {
        //     ScanDataRecord rec = (ScanDataRecord)recordList.get(i);
        //     // public int Id;
        //     // public String Tag;
        //     // public String Data;
        //     // public String CreDt;
        //     Log.v(LOG_TAG, 
        //         String.format("Id: %d, Tag: %s, Data: %s, CreDt: %s", rec.Id, rec.Tag, rec.Data, rec.CreDt)
        //     );

        //     JSONObject newScanDataRec = new JSONObject();
        //     try {
        //         newScanDataRec.put("id", rec.Id);
        //         newScanDataRec.put("tag", rec.Tag);
        //         newScanDataRec.put("data", rec.Data);
        //         newScanDataRec.put("cre_dt", rec.CreDt);
        //         scanDataList.put(newScanDataRec);   // create array and add items into that 
        //     } catch (JSONException e) {
        //         //some exception handler code.
        //     } 
        // }

        
        ScanDataRecord rec = (ScanDataRecord)recordList.get(0);
        Log.v(LOG_TAG, 
            String.format("Id: %d, Tag: %s, Data: %s, CreDt: %s", rec.Id, rec.Tag, rec.Data, rec.CreDt)
        );

        JSONObject newScanDataRec = new JSONObject();
        try {
            newScanDataRec.put("id", rec.Id);
            newScanDataRec.put("tag", rec.Tag);
            newScanDataRec.put("data", rec.Data);
            newScanDataRec.put("cre_dt", rec.CreDt);
            scanDataList.put(newScanDataRec);   // create array and add items into that 
        } catch (JSONException e) {
            //some exception handler code.
        }

        
        SendTeaScanData(scanDataList);

        // 
        pendingUploadTextView.setText(
            String.format("Pending upload(s): %d", db.GetCount("0"))
        );
        
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
    // }''

    void ShowToast(String message) {
        Context context = getApplicationContext();
        //CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    void SendTeaScanData(JSONArray dataList) {

        Log.v(LOG_TAG, "SendTeaScanData");

        boolean result = false;
        RequestQueue queue = Volley.newRequestQueue(this);
        String api_url ="https://asia-east2-zxshell.cloudfunctions.net/tea_scan_data";

        Log.v(LOG_TAG, dataList.toString());
        
        //JSONObject requestParameters = null;
        JSONObject requestParameters = new JSONObject();
        
        try {

            //requestParameters = new JSONObject(dataList.toString());
            requestParameters.put("data_type", "tea_scan_data");
            requestParameters.put("data", dataList);

            // Other parameters from db
            // requestParameters.put("id", "some sample msg");
            // requestParameters.put("tag", "some sample msg");
            // requestParameters.put("data", "some sample msg");
            // requestParameters.put("cre_dt", "some sample msg");

        } catch (JSONException e) {
            //some exception handler code.
        } 

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, api_url, requestParameters, 
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.v(LOG_TAG, response.toString());

                    try
                    {

                        int resultCount = response.getInt("count");
                        String result = response.getString("result");
                        JSONArray resultIdList = response.getJSONArray("id_list");

                        Log.v(LOG_TAG, String.format("result count %d", resultCount));
                        Log.v(LOG_TAG, String.format("result xxxxx %s", result));
                        Log.v(LOG_TAG, String.format("result count %d", resultIdList.length()));

                        for (int i = 0; i < resultIdList.length(); i++) {
                            int id = resultIdList.getInt(i);
                            
                            Log.v(LOG_TAG, String.format("result i %d, id %d", i, id));

                            db.Update(id);
                        }
                    } catch (JSONException e) {
                        //some exception handler code.
                    }

                    // Response should be a JSON like the below
                    // {
                    //     "result_message": "OK", 
                    //     "name": "api_borrow_blazer", 
                    //     "blazer_id": "XS-0001", 
                    //     "result": true, 
                    //     "student_id": "STUD-1234566"
                    // }

                    // try {
                    //     // Log.v(LOG_TAG, response.get("result").toString());
                    //     // String result = response.get("result").toString();
                    //     // String resultMessage = response.get("result_message").toString();
                    //     // String blazerId = response.get("blazer_id").toString();

                    //     // if ((result.equals("true")) && (resultMessage.equals("OK"))) {
                    //     //     //Log.v(LOG_TAG, "onActivityResult : BARCODE[" + studentId + "]");
                    //     //     instructions_textview.setText("Blazer " + blazerId + " is return to store.");
                    //     //     SetDisplayState(STATE_RETURN_SUCCESS);
                    //     // } else {
                    //     //     instructions_textview.setText("Return action failed. " + resultMessage);
                    //     // }
                    // } catch (JSONException e) {
                    //     //some exception handler code.
                    //     //instructions_textview.setText("JSONException: " + e.toString());
                    //     Log.v(LOG_TAG, "JSONException: " + e.toString());
                    //     throw e;
                    // }
                    
                }
            }, 
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Log.v(LOG_TAG, error.toString());
                }
            }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(jsObjRequest);
    }


    void DoApiCall(String msg) {

        boolean result = false;
        RequestQueue queue = Volley.newRequestQueue(this);
        String api_url ="https://asia-east2-zxshell.cloudfunctions.net/tea_message";

        //ShowToast("asd");
        // JSONArray care_type = new JSONArray();
        // for(int i=0; i < yourarray.length(); i++) {
        //     care_type.put(yourarray[i]);   // create array and add items into that 
        // }

        
        JSONObject requestParameters = new JSONObject();
        try {
            requestParameters.put("msg", msg);

            // Other parameters from db
            // requestParameters.put("id", "some sample msg");
            // requestParameters.put("tag", "some sample msg");
            // requestParameters.put("data", "some sample msg");
            // requestParameters.put("cre_dt", "some sample msg");

        } catch (JSONException e) {
            //some exception handler code.
        } 

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, api_url, requestParameters, 
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    Log.v(LOG_TAG, response.toString());


                    // Response should be a JSON like the below
                    // {
                    //     "result_message": "OK", 
                    //     "name": "api_borrow_blazer", 
                    //     "blazer_id": "XS-0001", 
                    //     "result": true, 
                    //     "student_id": "STUD-1234566"
                    // }

                    // try {
                    //     // Log.v(LOG_TAG, response.get("result").toString());
                    //     // String result = response.get("result").toString();
                    //     // String resultMessage = response.get("result_message").toString();
                    //     // String blazerId = response.get("blazer_id").toString();

                    //     // if ((result.equals("true")) && (resultMessage.equals("OK"))) {
                    //     //     //Log.v(LOG_TAG, "onActivityResult : BARCODE[" + studentId + "]");
                    //     //     instructions_textview.setText("Blazer " + blazerId + " is return to store.");
                    //     //     SetDisplayState(STATE_RETURN_SUCCESS);
                    //     // } else {
                    //     //     instructions_textview.setText("Return action failed. " + resultMessage);
                    //     // }
                    // } catch (JSONException e) {
                    //     //some exception handler code.
                    //     //instructions_textview.setText("JSONException: " + e.toString());
                    //     Log.v(LOG_TAG, "JSONException: " + e.toString());
                    //     throw e;
                    // }
                    
                }
            }, 
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    Log.v(LOG_TAG, error.toString());
                }
            }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        queue.add(jsObjRequest);
    }

}
