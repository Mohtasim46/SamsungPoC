package com.samsungpoc.samsungpocsensormobile;

import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
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
    private Toolbar toolbar;
    private AlertDialog.Builder dialogBuilder;
    private EditText dialogInput;

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

        setSupportActionBar(toolbar);

        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Set User Target");

        dialogInput = new EditText(this);
        dialogInput.setMaxLines(1);
        dialogInput.setPadding(48, 24, 24, 24);

        dialogInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogInput.setText("" + PreferenceHelper.getCurrentStepsTarget());

        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String inputText = dialogInput.getText().toString();
                if(inputText != null && inputText.length() > 0) {
                    PreferenceHelper.setCurrentStepsTarget(Integer.parseInt(dialogInput.getText().toString()));
                }
                loadStepData();
                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
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
        toolbar = findViewById(R.id.toolbar);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.set_target:
                if(dialogInput.getParent()!=null)
                    ((ViewGroup)dialogInput.getParent()).removeView(dialogInput); // <- fix
                dialogBuilder.setView(dialogInput);
                dialogBuilder.create().show();
                Toast.makeText(this, "Set user target is tapped !!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(this, "Help is tapped !!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
