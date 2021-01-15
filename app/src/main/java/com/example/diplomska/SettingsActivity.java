package com.example.diplomska;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    int hourTextViewValue = 12;
    boolean notificationsAreActivated = false;
    public boolean wantsMotivationalMsg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button backButton = (Button) findViewById(R.id.settings_menu_back_button);
        Button plusButton = (Button) findViewById(R.id.plusHourButton);
        Button minusButton = (Button) findViewById(R.id.minusHourButton);

        TextView hourTextView = (TextView) findViewById(R.id.hourTextView);

        Switch notificationsSwitch = (Switch) findViewById(R.id.notification_switch);
        Switch motivationSwitch = (Switch) findViewById(R.id.motivations_switch);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainMenu = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(backToMainMenu);
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourTextViewValue = hourTextViewValue + 1;
                hourTextView.setText(String.valueOf(hourTextViewValue));
            }
        });
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hourTextViewValue = hourTextViewValue - 1;
                hourTextView.setText(String.valueOf(hourTextViewValue));
            }
        });
        notificationsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notificationsAreActivated)
                {
                    Toast.makeText(SettingsActivity.this, "Notifications are turned off", Toast.LENGTH_SHORT).show();
                    notificationsAreActivated = false;
                    // kod za deaktiviranje na notifications
                }else {
                    // Tuka vnesi kod za promena na promenlivata za notifikacii
                    notificationsAreActivated = true;
                    Toast.makeText(SettingsActivity.this, "Notifications are turned off", Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.HOUR_OF_DAY, hourTextViewValue);
                    calendar.set(Calendar.MINUTE, 15);

                    Intent notificationIntent = new Intent(SettingsActivity.this, BroadcastReceiver.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                }
            }
        });

        motivationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!wantsMotivationalMsg)
                {
                    wantsMotivationalMsg = true;
                    Toast.makeText(SettingsActivity.this, "Motivational messages are turned on", Toast.LENGTH_SHORT).show();
                }else{
                    wantsMotivationalMsg = false;
                    Toast.makeText(SettingsActivity.this, "Motivational messages are turned off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}