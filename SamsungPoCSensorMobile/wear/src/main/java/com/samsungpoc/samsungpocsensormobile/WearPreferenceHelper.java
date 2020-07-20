package com.samsungpoc.samsungpocsensormobile;

import android.content.Context;
import android.content.SharedPreferences;

public class WearPreferenceHelper {

    private static final String TAG = WearPreferenceHelper.class.getSimpleName();
    private static final String SharedPreferenceKey = "shared_preference_wear_1";
    private static final String LAST_SYNC_TIME_KEY = "last_sync_time";

    private static synchronized SharedPreferences getSharedPreferences() {
        return WearContextHolder.getInstance().getApplicationContext().getSharedPreferences(SharedPreferenceKey, Context.MODE_PRIVATE);
    }

    public static void setCurrentStepCount(int stepCount) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt("sensor_steps", stepCount);
        editor.apply();
    }

    public static int getCurrentStepCount() {
        return getSharedPreferences().getInt("sensor_steps", 0);
    }

    public static void setStepsTarget(int target) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt("steps_target", target);
        editor.apply();
    }

    public static int getStepsTarget() {
        return getSharedPreferences().getInt("steps_target", 6000);
    }

    public static void setLastSyncTime(long lastSyncTime) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putLong(LAST_SYNC_TIME_KEY, lastSyncTime);
        editor.apply();
    }

    public static long getLastSyncTime() {
        return getSharedPreferences().getLong(LAST_SYNC_TIME_KEY, 0);
    }
}
