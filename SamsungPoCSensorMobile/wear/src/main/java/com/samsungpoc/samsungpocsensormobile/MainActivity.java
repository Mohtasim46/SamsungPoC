package com.samsungpoc.samsungpocsensormobile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class MainActivity extends WearableFragmetActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final double FACTOR = 0.146467f; // c = a * sqrt(2)
    private static final long STEP_PROGRESS_BAR_ANIMATION_TIME = 1500;

    private ConstraintLayout constraintLayout;
    private CardView stepCardView;
    private CircularProgressBar stepProgressBar;
    private TextView stepCountAndTargetTextView;

    /*
        Overriding methods
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WearContextHolder.getInstance().setApplicationContext(this);

        initFindViewById();
        initOnClickListener();
        observeMutableLiveData();
        adjustInset();

        loadStepData();

        // Enables Always-on
        setAmbientEnabled();
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
        constraintLayout = findViewById(R.id.constraint_layout);
        stepCardView = findViewById(R.id.step_card_view);
        stepProgressBar = findViewById(R.id.step_progress_bar);
        stepCountAndTargetTextView = findViewById(R.id.step_count_and_target_text_view);
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
        observeStepCountAndTargetMutableLiveData();
    }

    private void observeStepProgressMutableLiveData() {
        Log.d(TAG, "observeStepProgressMutableLiveData called");
        getMainViewModel().stepProgressMutableLiveData.observe(this, stepProgress -> {
            Log.d(TAG, "stepProgress is = " + stepProgress + "%");
            stepProgressBar.setProgressWithAnimation(stepProgress, STEP_PROGRESS_BAR_ANIMATION_TIME);
        });
    }

    private void observeStepCountAndTargetMutableLiveData() {
        Log.d(TAG, "observeStepCountAndTargetMutableLiveData called");
        getMainViewModel().stepCountAndTargetMutableLiveData.observe(this, stepCountAndTarget -> {
            Log.d(TAG, "stepCount and target is = " + stepCountAndTarget);
            stepCountAndTargetTextView.setText(stepCountAndTarget);
        });
    }

    /*
        Loading data
     */
    private void loadStepData() {
        Log.d(TAG, "loadStepData called");
        getMainViewModel().loadStepData();
    }

    /*
        Ambient Mode callback
     */

    @Override
    protected void onEnterAmbientMode() {
        Log.d(TAG, "onEnterAmbientMode called");
    }

    @Override
    protected void onExitAmbientMode() {
        Log.d(TAG, "onExitAmbientMode called");
    }

    @Override
    protected void onUpdateAmbientMode() {
        Log.d(TAG, "onUpdateAmbientMode called");
    }

    /*
        To adjust round wearable
     */
    private void adjustInset() {
        if (getApplicationContext().getResources().getConfiguration().isScreenRound()) {
            int inset = (int) (FACTOR * getResources().getDisplayMetrics().widthPixels);
            constraintLayout.setPadding(inset, inset, inset, inset);
        }
    }
}
