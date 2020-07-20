package com.samsungpoc.samsungpocsensormobile;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MessageService extends WearableListenerService {

    private static final String TAG = MessageService.class.getSimpleName();

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals("/my_path")) {
            final String message = new String(messageEvent.getData());
            Log.d(TAG, "onMessageReceived called, message = " + message);
            if (message.equals("stepData")) {
                loadStepData();
            }
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

    private void loadStepData() {
        StepData stepData = new StepData();
        stepData.setStepCount(PreferenceHelper.getCurrentStepCount());
        stepData.setTarget(6000);
        stepData.setLastSyncTimeMilliseconds(System.currentTimeMillis());
        String gsonStepData = new Gson().toJson(stepData);
        Log.d(TAG, "gson " + gsonStepData);
        sendMessage(gsonStepData);
    }

    private void sendMessage(String result) {
        Task<List<Node>> wearableList = Wearable.getNodeClient(getApplicationContext()).getConnectedNodes();
        try {
            List<Node> nodes = Tasks.await(wearableList);
            for (Node node: nodes) {
                // Send this message
                Wearable.getMessageClient(getApplicationContext()).sendMessage(node.getId(), "/my_path", result.getBytes());
            }
        } catch (InterruptedException e) {
            Log.e(TAG, "Error - " + e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e(TAG, "Error - " + e.getMessage());
            e.printStackTrace();
        }
    }
}
