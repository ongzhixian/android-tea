package com.emptool.tea;

import android.util.Log;
import android.util.SparseArray;
import android.os.Bundle;
import android.os.Vibrator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class ScanBarcodeActivity extends AppCompatActivity implements BarcodeHandler
{
    private static final String LOG_TAG = "ScanBarcodeActivity"; // This needs to within 23 char

    // Constants use for scanner
    public static final String SCANNER_MODE = "SCANNER_MODE";
    public static final String SCANNER_MODE_DEBUG = "DEBUG";
    public static final String SCANNER_MODE_NORMAL = "NORMAL";

    CameraSource cameraSource = null;
    BarcodeDetector barcodeDetector = null;

    Vibrator vibrator = null;

    // UI variables
    SurfaceView cameraView = null;
    TextView barcodeFormat = null;
    TextView barcodeValue = null;

    CameraCallback cameraCallback = null;
    BarcodeProcessor barcodeProcessor = null;

    String tagText = null;
    String scannerMode = null;
    String previousValue = "";

    TeaDbHelper db = null;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_barcode_activity);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                scannerMode = extras.getString(SCANNER_MODE);
                tagText = extras.getString(MainActivity.TAG);
            }
        }

        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        barcodeFormat = (TextView)findViewById(R.id.barcode_format);
        barcodeValue = (TextView)findViewById(R.id.barcode_value);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        db = new TeaDbHelper(this);

        InitializeCamera();
        SetDisplayMode();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onSaveInstanceState");

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is killed and restarted.
        savedInstanceState.putString(SCANNER_MODE, scannerMode);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onRestoreInstanceState");

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        scannerMode = savedInstanceState.getString(SCANNER_MODE);
        SetDisplayMode();
    }

    void SetDisplayMode() {
        Log.v(LOG_TAG, "SetDisplayMode");
        if ((scannerMode != null) && (SCANNER_MODE_DEBUG.equals(scannerMode)))
        {
            barcodeFormat.setVisibility(View.VISIBLE);
            barcodeValue.setVisibility(View.VISIBLE);
        } else {
            barcodeFormat.setVisibility(View.GONE);
            barcodeValue.setVisibility(View.GONE);
        }
        // barcodeFormat.setText("Last scanned format: N/A");
        // barcodeValue.setText("")
    }

    void InitializeCamera()
    {
        this.barcodeDetector = new BarcodeDetector.Builder(this)
            //.setBarcodeFormats(Barcode.ALL_FORMATS)
            .build();
        
        this.cameraSource = new CameraSource
            .Builder(this, barcodeDetector)
            .setRequestedPreviewSize(480, 480)
            .setRequestedFps(30)
            .setAutoFocusEnabled(true)
            .build();

        this.cameraCallback = new CameraCallback(this.cameraSource);
        this.cameraView.getHolder().addCallback(this.cameraCallback);
        this.barcodeProcessor = new BarcodeProcessor(this);
        this.barcodeProcessor.barcodeFormatTextView = barcodeFormat;
        this.barcodeProcessor.barcodeValueTextView = barcodeValue;
        
        this.barcodeDetector.setProcessor(this.barcodeProcessor);


    }

    String getBarcodeFormat(int format) {
        switch (format)
        {
            case Barcode.ALL_FORMATS:   // 0
                return "ALL_FORMATS";
            case Barcode.AZTEC:         // 4096
                return "AZTEC";
            case Barcode.CODABAR:       // 8
                return "CODABAR"; 
            case Barcode.CODE_128:      // 1
                return "CODE_128";
            case Barcode.CODE_39:       // 2
                return "CODE_39";
            case Barcode.CODE_93:       //4
                return "CODE_93";
            case Barcode.DATA_MATRIX:   // 16
                return "DATA_MATRIX";
            case Barcode.EAN_13:        // 32
                return "EAN_13";
            case Barcode.EAN_8:         // 64
                return "EAN_8";
            case Barcode.ITF:           // 128
                return "ITF";
            case Barcode.PDF417:        // 2048
                return "PDF417";
            case Barcode.QR_CODE:       // 256
                return "QR_CODE";
            case Barcode.UPC_A:         // 512
                return "UPC_A";
            case Barcode.UPC_E:         // 1024 
                return "UPC_E";
            // case Barcode.URL: // 8
            //     return "URL";
            // case Barcode.WIFI: // 9
            //     return "WIFI";
            // case Barcode.CALENDAR_EVENT: // 11
            //     return "CALENDAR_EVENT";
            // case Barcode.CONTACT_INFO: // 1
            //     return "CONTACT_INFO";
            // case Barcode.EMAIL: // 2
            //     return "EMAIL";
            // case Barcode.GEO: // 10
            //     return "GEO";
            // case Barcode.ISBN: // 3
            //     return "ISBN";
            // case Barcode.PHONE: // 4
            //     return "PHONE";
            // case Barcode.SMS: // 6
            //     return "SMS";
            // case Barcode.TEXT: // 7
            //     return "TEXT";
            // case Barcode.PRODUCT: // 5 
            //     return "PRODUCT";
            // case Barcode.DRIVER_LICENSE: // 12
            //     return "DRIVER_LICENSE";
            default:
                return "UNKNOWN_FORMAT";
        }
    }

    @Override
    public void handleBarcode(int format, String barcode)
    {
        if ((scannerMode != null) && (SCANNER_MODE_DEBUG.equals(scannerMode))) {
            // Do nothing
            Log.v(LOG_TAG, "handleBarcodeB - do nothing");

            Log.v(LOG_TAG, "handleBarcodeB1 - previous is [" + previousValue + "], barcode is [" + barcode + "]");
            if (!previousValue.equals(barcode)) {

                db.SaveScanData(tagText, barcode);
                // 
                vibrator.vibrate(250);
                previousValue = barcode;
                Log.v(LOG_TAG, "handleBarcodeB2 - previous is [" + previousValue + "], barcode is [" + barcode + "]");
            }

            

        } else {
            Log.v(LOG_TAG, "handleBarcodeB - NORMAL");
            // Handling for normal scanner usage
            Intent intent = new Intent();
            intent.putExtra("BARCODE", barcode);
            intent.putExtra("BARCODE_FORMAT", format);
            intent.putExtra("BARCODE_FORMAT_TEXT", getBarcodeFormat(format));
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.loan_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(LOG_TAG, "onOptionsItemSelected");

        switch (item.getItemId()) {
            // case R.id.load_menu_toggle_torchlight:
            //     Log.v(LOG_TAG, "load_menu_toggle_torchlight");
            //     this.cameraCallback.ToggleTorchLight();
            //     break;
            default:
                //This will break back button; comment out
                //return super.onOptionsItemSelected(item);
                break;
        }

        return true;
    }

    
    public void toggleFlash(View view) {
        Log.v(LOG_TAG, "toggle flash");
        this.cameraCallback.ToggleTorchLight();
    }

    



}
