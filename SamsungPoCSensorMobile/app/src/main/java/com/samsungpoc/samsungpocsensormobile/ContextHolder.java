package com.samsungpoc.samsungpocsensormobile;

import android.content.Context;
import android.util.Log;

public class ContextHolder {

    private static final String TAG = ContextHolder.class.getSimpleName();

    private Context applicationContext = null;

    private static ContextHolder instance = new ContextHolder();

    private ContextHolder() {
        Log.e(TAG, "ContextHolder private constructor called.");
    }

    public static ContextHolder getInstance() {
        return instance;
    }

    public Context getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(Context context) {
        this.applicationContext = context;
    }
}
