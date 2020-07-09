package com.samsungpoc.samsungpocsensormobile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.Random;

public class MainViewModel extends AndroidViewModel {

    private int stepCount;
    private int stepCountTarget;

    public MutableLiveData<Float> stepProgressMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> stepCountMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> stepCountTargetMutableLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    private int getStepCount() {
        return stepCount;
    }

    private int getStepCountTarget() {
        return stepCountTarget;
    }

    private float getStepProgress() {
        float progress = getStepCount();
        float target = getStepCountTarget();
        return ((progress / target) * 100.0f);
    }

    private void loadStepCount() {
        // TODO
        this.stepCount = PreferenceHelper.getCurrentStepCount();
    }

    private void loadStepCountTarget() {
        // TODO
        this.stepCountTarget = 6000;
    }

    public void loadStepData() {
        loadStepCount();
        loadStepCountTarget();
        updateStepCard();
    }

    private void updateStepCard() {
        stepProgressMutableLiveData.setValue(getStepProgress());
        stepCountMutableLiveData.setValue(Integer.toString(getStepCount()));
        stepCountTargetMutableLiveData.setValue(getApplication().getResources().getString(R.string.step_target_count_text, getStepCountTarget()));
    }
}
