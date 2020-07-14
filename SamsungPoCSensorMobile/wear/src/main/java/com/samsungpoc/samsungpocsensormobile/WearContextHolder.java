package com.samsungpoc.samsungpocsensormobile;

import android.content.Context;
import android.util.Log;

public class WearContextHolder {
    private static final String TAG = WearContextHolder.class.getSimpleName();

    private Context applicationContext = null;

    private static WearContextHolder instance = new WearContextHolder();

    private WearContextHolder() {
        Log.e(TAG, "WearContextHolder private constructor called.");
    }

    public static WearContextHolder getInstance() {
        return instance;
    }

    public Context getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(Context context) {
        this.applicationContext = context;
    }
}
