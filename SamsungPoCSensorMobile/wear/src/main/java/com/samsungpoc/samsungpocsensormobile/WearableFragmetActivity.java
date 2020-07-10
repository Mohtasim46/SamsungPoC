package com.samsungpoc.samsungpocsensormobile;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.wear.ambient.AmbientModeSupport;

public abstract class WearableFragmetActivity extends FragmentActivity implements AmbientModeSupport.AmbientCallbackProvider {

    private static final String TAG = WearableFragmetActivity.class.getSimpleName();

    protected void onEnterAmbientMode() { }
    protected void onExitAmbientMode() { }
    protected void onUpdateAmbientMode() { }

    protected void setAmbientEnabled() {
        AmbientModeSupport.attach(this);
    }

    @Override
    public AmbientModeSupport.AmbientCallback getAmbientCallback() {
        return new MyAmbientCallback();
    }

    private class MyAmbientCallback extends AmbientModeSupport.AmbientCallback {

        @Override
        public void onEnterAmbient(Bundle ambientDetails) {
            // Handle entering ambient mode
            Log.e(TAG, "onEnterAmbient");
            onEnterAmbientMode();
        }

        @Override
        public void onExitAmbient() {
            // Handle exiting ambient mode
            Log.e(TAG, "onExitAmbient");
            onExitAmbientMode();
        }

        @Override
        public void onUpdateAmbient() {
            // Update the content
            Log.e(TAG, "onUpdateAmbient");
            onUpdateAmbientMode();
        }
    }
}
