package com.example.diplomska;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatingIntent = new Intent(context, MainActivity.class);
        repeatingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeatingIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setContentTitle("Време е за денешните вежби :) ")
                .setContentText("Кликнете тука за да почнете")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_notification_book_icon)
                .setVibrate(new long[]{250, 250, 250, 250, 250});

        notificationManager.notify(100, builder.build());
    }
}
