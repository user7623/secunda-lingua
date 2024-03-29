package com.example.diplomska;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.preference.PreferenceManager;

import java.util.Calendar;

import static com.example.diplomska.App.CHANNEL_1_ID;

public class AlarmService extends Service {

    private NotificationManagerCompat notificationManager;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = NotificationManagerCompat.from(this);

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("Info" , "onStartCommand in AlarmService --------------------------------");
        enableReminder();


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void enableReminder()
    {
        Log.e("Info" , "enable reminder --------------------------------");
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        int hourOfDay = PreferenceManager.getDefaultSharedPreferences(AlarmService.this).getInt("hourOfDay", 0);

        try {
            if (hourOfDay != 0) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                startAlarm(c);
            } else {
                cancelAlarm();
            }
        }catch (Exception e)
        {
            Log.e("Exception" , e.getLocalizedMessage());
        }
    }

    private void startAlarm(Calendar c) {
        Log.e("Info" , "start Alarm ---------------------------------------------------");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        intent.setAction("reminderAction");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);

        Log.d("info", "Alarm canceled -------------------------------------------------");
    }



}
