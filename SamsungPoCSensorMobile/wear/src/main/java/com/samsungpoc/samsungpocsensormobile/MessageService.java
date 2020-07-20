package com.samsungpoc.samsungpocsensormobile;

import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

public class MessageService extends WearableListenerService {

    private static final String TAG = MessageService.class.getSimpleName();

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

        // If the message path equals to "/my_path"
        if (messageEvent.getPath().equals("/my_path")) {

            final String message = new String(messageEvent.getData());
            Log.e(TAG, "onMessageReceived wear - " + message);

            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);
            Log.d(TAG, "Message : " + message);

            // Broadcast the received Data layer message locally
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }

}
