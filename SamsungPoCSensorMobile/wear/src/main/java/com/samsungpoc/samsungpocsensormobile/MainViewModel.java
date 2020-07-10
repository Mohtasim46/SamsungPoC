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
    public MutableLiveData<String> stepCountAndTargetMutableLiveData = new MutableLiveData<>();

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

    private void loadStepCountAndTargetData() {
        // TODO
        this.stepCount = new Random().nextInt(5000) + 500;
        this.stepCountTarget = 6000;
    }

    public void loadStepData() {
        loadStepCountAndTargetData();
        updateStepCard();
    }

    private void updateStepCard() {
        stepProgressMutableLiveData.setValue(getStepProgress());
        stepCountAndTargetMutableLiveData.setValue(getApplication().getResources().getString(
                R.string.step_count_and_target_text,
                getStepCount(), getStepCountTarget()));
    }
}
