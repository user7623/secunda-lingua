package com.example.diplomska;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("Info" , "Alert receiver --------------- --------------------------------");
        //if device was turned off reset/reactivate the alarm
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent activateService = new Intent(context, AlarmService.class);
            context.startService(activateService);
        }
        if (intent.getAction().equals("reminderAction")) {
            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
            notificationHelper.getManager().notify(1, nb.build());
        }
    }
}