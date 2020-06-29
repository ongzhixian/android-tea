package com.emptool.tea;

import java.io.IOException;
import java.lang.reflect.Field;
import android.support.annotation.NonNull;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.android.gms.vision.CameraSource;

public class CameraCallback implements SurfaceHolder.Callback
{
    private static final String LOG_TAG = "CameraCallback"; // This needs to within 23 char

    CameraSource cameraSource = null;
    Camera camera = null;
    boolean isTorchLightOn = false;

    public CameraCallback(CameraSource cameraSource) {
        Log.v(LOG_TAG, ".ctor");

        this.cameraSource = cameraSource;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v(LOG_TAG, "surfaceCreated");
        Log.v(LOG_TAG, this.getClass().getName());

        try {
            this.cameraSource.start(holder);
            this.camera = getCamera(this.cameraSource);
        } catch (IOException ie) {
            Log.e("CAMERA SOURCE", ie.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.v(LOG_TAG, "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v(LOG_TAG, "surfaceDestroyed");

        cameraSource.stop();
    }

    public void ToggleTorchLight() {
        if (this.camera != null)
        {
            Log.v(LOG_TAG, "camera exists");
            Camera.Parameters cameraParameters = camera.getParameters();

            if (isTorchLightOn) { // turn off torch light
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(cameraParameters);
                isTorchLightOn = false;
            } else { // turn on torch light
                cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(cameraParameters);
                isTorchLightOn = true;
            }
        } 
        else
        {
            Log.v(LOG_TAG, "camera is null");
        }

    }

    public static Camera getCamera(@NonNull CameraSource cameraSource) {

        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    Camera camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        return camera;
                    }
                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

        return null;
    }

}