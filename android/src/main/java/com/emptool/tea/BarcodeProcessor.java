package com.emptool.tea;

import android.util.Log;
import android.util.SparseArray;
import android.os.Handler;

import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeProcessor implements Detector.Processor<Barcode>
{
    private static final String LOG_TAG = "BarcodeProcessor"; // This needs to within 23 char

    Handler uiHandler = null;
    BarcodeHandler barcodeHandler = null;
    public TextView barcodeFormatTextView = null;
    public TextView barcodeValueTextView = null;

    public BarcodeProcessor(BarcodeHandler barcodeHandler) {

        Log.v(LOG_TAG, ".ctor");
        
        this.barcodeHandler = barcodeHandler;
    }
        
    @Override
    public void receiveDetections(Detector.Detections<Barcode> detections) {

        final SparseArray<Barcode> barcodes = detections.getDetectedItems();

        Log.v(LOG_TAG, "receiveDetections:" + new java.util.Date().toString() + "; " + String.valueOf(barcodes.size()) );

        if (barcodes.size() != 0) {
            final Barcode scannedBarcode = barcodes.valueAt(0);
            Log.v(LOG_TAG, "barcodes.size greater than 0: " + barcodes.valueAt(0).displayValue);
            Log.v(LOG_TAG, "barcodes.format: " + barcodes.valueAt(0).format);
            
            if (barcodeFormatTextView != null) {
                Log.v(LOG_TAG, "barcodeFormatTextView is not null ");
                barcodeFormatTextView.post(new Runnable() { // Use the post method of the TextView
                    public void run() {
                        Log.v(LOG_TAG, "barcodeFormatTextView IN POST -Run ");
                        barcodeFormatTextView.setText("Last scanned format: " + getBarcodeFormat(scannedBarcode.format));
                        Log.v(LOG_TAG, "barcodeFormatTextView IN POST -Run2 ");
                    }
                });
            } else {
                Log.v(LOG_TAG, "barcodeFormatTextView IS NULL ");
            }

            if (barcodeValueTextView != null) {
                Log.v(LOG_TAG, "barcodeValueTextView is not null ");
                barcodeValueTextView.post(new Runnable() { // Use the post method of the TextView
                    public void run() {
                        Log.v(LOG_TAG, "barcodeValueTextView IN POST -Run ");
                        barcodeValueTextView.setText("Last scanned value: " + scannedBarcode.displayValue);
                        Log.v(LOG_TAG, "barcodeValueTextView IN POST -Run2 ");
                    }
                });
            } else {
                Log.v(LOG_TAG, "barcodeValueTextView IS NULL ");
            }
            // this.barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
            //     public void run() {
            //         barcodeInfo.setText(    // Update the TextView
            //             barcodes.valueAt(0).displayValue
            //         );
            //     }
            // });
            
            barcodeHandler.handleBarcode(scannedBarcode.format, scannedBarcode.displayValue);

            //barcodeHandler.handleBarcode(scannedBarcode.format, scannedBarcode.displayValue);
        }
    }

    @Override
    public void release() {
        Log.v(LOG_TAG, "release barcode processor");
    }

    String getBarcodeFormat(int format) {
        switch (format)
        {
            // case Barcode.AZTEC:         // 4096
            //     return "AZTEC";
            case Barcode.CODABAR:       // 8
                return "CODABAR"; 
            case Barcode.CODE_128:      // 1
                return "CODE 128";
            case Barcode.CODE_39:       // 2
                return "CODE 39";
            case Barcode.CODE_93:       //4
                return "CODE 93";
            case Barcode.DATA_MATRIX:   // 16
                return "DATA MATRIX";
            case Barcode.EAN_13:        // 32
                return "EAN 13";
            case Barcode.EAN_8:         // 64
                return "EAN 8";
            case Barcode.ITF:           // 128
                return "ITF";
            case Barcode.PDF417:        // 2048
                return "PDF417";
            case Barcode.QR_CODE:       // 256
                return "QR CODE";
            case Barcode.UPC_A:         // 512
                return "UPC A";
            case Barcode.UPC_E:         // 1024 
                return "UPC E";
            default:
                return "UNKNOWN_FORMAT";
        }
    }
}
