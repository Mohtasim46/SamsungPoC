package com.samsungpoc.samsungpocsensormobile;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private int stepCount;
    private int stepCountTarget;

    public MutableLiveData<Float> stepProgressMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<String> stepCountAndTargetMutableLiveData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        setupMessageCommunication();
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

    public void loadStepData() {
        loadStepDataSendMessage("stepData");
    }

    private void updateStepCard() {
        stepProgressMutableLiveData.setValue(getStepProgress());
        stepCountAndTargetMutableLiveData.setValue(getApplication().getResources().getString(
                R.string.step_count_and_target_text,
                getStepCount(), getStepCountTarget()));
    }

    private void loadStepDataSendMessage(String message) {
        Log.e(TAG, "sendMessage called");
        String dataPath = "/my_path";
        new SendMessage(dataPath, message).start();
    }

    private void onReceivedMessage(String message) {
        Log.e(TAG, "onReceivedMessage called, message = " + message);
        StepData stepData = new Gson().fromJson(message, StepData.class);
        this.stepCount = stepData.getStepCount();
        this.stepCountTarget = stepData.getTarget();
        updateStepCard();
    }

    private void setupMessageCommunication() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SEND);
        Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(getApplication().getApplicationContext()).registerReceiver(messageReceiver, intentFilter);
    }

    private class Receiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive called");
            onReceivedMessage(intent.getStringExtra("message"));
        }
    }

    private class SendMessage extends Thread {
        String path;
        String message;

        // Constructor for sending information to the Data Layer
        SendMessage(String p, String m) {
            path = p;
            message = m;
        }

        public void run() {
            // Retrieve the connected devices
            Task<List<Node>> nodeListTask = Wearable.getNodeClient(getApplication().getApplicationContext()).getConnectedNodes();
            try {
                // Block on a task and get the result synchronously
                List<Node> nodes = Tasks.await(nodeListTask);
                for (Node node : nodes) {
                    // Send the message
                    Task<Integer> sendMessageTask = Wearable.getMessageClient(getApplication()).sendMessage(node.getId(), path, message.getBytes());
                    try {
                        Integer result = Tasks.await(sendMessageTask);
                        // Handle the errors
                    } catch (ExecutionException exception) {
                        Log.e(TAG, "Error - " + exception.getMessage());
                    } catch (InterruptedException exception) {
                        Log.e(TAG, "Error - " + exception.getMessage());
                    }
                }
            } catch (ExecutionException exception) {
                Log.e(TAG, "Error - " + exception.getMessage());
            } catch (InterruptedException exception) {
                Log.e(TAG, "Error - " + exception.getMessage());
            }
        }
    }
}
