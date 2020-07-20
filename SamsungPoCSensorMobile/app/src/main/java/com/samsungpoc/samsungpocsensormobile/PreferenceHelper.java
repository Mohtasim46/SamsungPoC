package com.samsungpoc.samsungpocsensormobile;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceHelper {

    private static final String TAG = PreferenceHelper.class.getSimpleName();
    private static final String SharedPreferenceKey = "shared_preference_mobile_1";

    private static synchronized SharedPreferences getSharedPreferences() {
        return ContextHolder.getInstance().getApplicationContext().getSharedPreferences(SharedPreferenceKey, Context.MODE_PRIVATE);
    }

    public static void setCurrentStepCount(int stepCount) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt("sensor_steps", stepCount);
        editor.apply();
    }

    public static int getCurrentStepCount() {
        return getSharedPreferences().getInt("sensor_steps", 0);
    }
}
