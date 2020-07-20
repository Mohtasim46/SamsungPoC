package com.samsungpoc.samsungpocsensormobile;

public class StepData {

    private int stepCount;
    private int target;
    private long lastSyncTimeMilliseconds;

    public int getStepCount() {
        return stepCount;
    }

    public void setStepCount(int stepCount) {
        this.stepCount = stepCount;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public long getLastSyncTimeMilliseconds() {
        return lastSyncTimeMilliseconds;
    }

    public void setLastSyncTimeMilliseconds(long lastSyncTimeMilliseconds) {
        this.lastSyncTimeMilliseconds = lastSyncTimeMilliseconds;
    }
}
