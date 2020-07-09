package com.samsungpoc.samsungpocsensormobile;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends BaseActivity implements View.OnClickListener, SensorEventListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final long STEP_PROGRESS_BAR_ANIMATION_TIME = 1500;

    private CardView stepCardView;
    private CircularProgressBar stepProgressBar;
    private TextView stepCountTextView;
    private TextView stepCountTargetTextView;

    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private boolean isSensorPresent;
    int stepCount = 0;

    /*
        Overriding methods
     */

    @Override
    protected int getLayout() {
        Log.d(TAG, "getLayout called");
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        Log.d(TAG, "init called");
        ContextHolder.getInstance().setApplicationContext(this);
        initFindViewById();
        initOnClickListener();
        observeMutableLiveData();
        initSensorManager();
        loadStepData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.step_card_view:
                onStepCardClick();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            mSensorManager.registerListener(this, mStepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
            mSensorManager.unregisterListener(this, mStepCounterSensor);
    }

    /*
                Initializing methods
             */
    private void initFindViewById() {
        Log.d(TAG, "initFindViewById called");
        stepCardView = findViewById(R.id.step_card_view);
        stepProgressBar = findViewById(R.id.step_progress_bar);
        stepCountTextView = findViewById(R.id.step_count_text_view);
        stepCountTargetTextView = findViewById(R.id.step_count_target_text_view);
    }

    private void initSensorManager() {
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        } else {
            Log.i(TAG, "Step sensor is not present in this device.");
            isSensorPresent = false;
        }
    }
    /*
        Initializing onClickListener
     */
    private void initOnClickListener() {
        Log.d(TAG, "initOnClickListener called");
        stepCardView.setOnClickListener(this);
    }

    private void onStepCardClick() {
        Log.d(TAG, "onStepCardClick called");
        loadStepData();
    }

    /*
        Getting view model object
     */
    private MainViewModel getMainViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    /*
        Observing Live data
     */
    private void observeMutableLiveData() {
        Log.d(TAG, "observeMutableLiveData called");
        observeStepProgressMutableLiveData();
        observeStepCountMutableLiveData();
        observeStepCountTargetMutableLiveData();
    }

    private void observeStepProgressMutableLiveData() {
        Log.d(TAG, "observeStepProgressMutableLiveData called");
        getMainViewModel().stepProgressMutableLiveData.observe(this, stepProgress -> {
            Log.d(TAG, "stepProgress is = " + stepProgress + "%");
            stepProgressBar.setProgressWithAnimation(stepProgress, STEP_PROGRESS_BAR_ANIMATION_TIME);
        });
    }

    private void observeStepCountMutableLiveData() {
        Log.d(TAG, "observeStepCountMutableLiveData called");
        getMainViewModel().stepCountMutableLiveData.observe(this, stepCount -> {
            Log.d(TAG, "stepCount is = " + stepCount);
            stepCountTextView.setText(stepCount);
        });
    }

    private void observeStepCountTargetMutableLiveData() {
        Log.d(TAG, "observeStepCountTargetMutableLiveData called");
        getMainViewModel().stepCountTargetMutableLiveData.observe(this, stepCountTarget -> {
            Log.d(TAG, "stepCountTarget is = " + stepCountTarget);
            stepCountTargetTextView.setText(stepCountTarget);
        });
    }

    /*
        Loading data
     */
    private void loadStepData() {
        Log.d(TAG, "loadStepData called");
        getMainViewModel().loadStepData();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor == mStepCounterSensor) {
            stepCount = (int) event.values[0];
            PreferenceHelper.setCurrentStepCount(stepCount);
            Log.d(TAG, "Current step count " + stepCount);
            loadStepData();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.i(TAG, "Accuracy of sensor has been changed to " + accuracy);
    }
}
