package com.samsungpoc.samsungpocsensormobile;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final long STEP_PROGRESS_BAR_ANIMATION_TIME = 1500;

    private CardView stepCardView;
    private CircularProgressBar stepProgressBar;
    private TextView stepCountTextView;
    private TextView stepCountTargetTextView;

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
        initFindViewById();
        initOnClickListener();
        observeMutableLiveData();
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
}
