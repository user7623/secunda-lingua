package com.example.diplomska;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Calendar;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    int hourTextViewValue = 12;
    boolean notificationsAreActivated = false;
    public static boolean wantsMotivationalMsg = false;

    Button backButton;
    Button openTimePickerButton;
    Switch notificationsSwitch;
    Switch motivationSwitch;
    int hour,minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        backButton = (Button) findViewById(R.id.settings_menu_back_button);
        notificationsSwitch = (Switch) findViewById(R.id.notification_switch);
        motivationSwitch = (Switch) findViewById(R.id.motivations_switch);
        openTimePickerButton = (Button) findViewById(R.id.openTimePickerButton);

        setOnClickListeners();

    }

    private void activateNotificationsFunction()
    {
        Intent alarmServiceIntent = new Intent(SettingsActivity.this , AlarmService.class);
        startService(alarmServiceIntent);
    }

    private void setOnClickListeners()
    {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (notificationsSwitch.isChecked()) {
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit().putInt("timeOfDayHour", hour).apply();
                    PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit().putInt("timeOfDayMinute", minute).apply();

                    activateNotificationsFunction();
                }else if (!notificationsSwitch.isChecked())
                {
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit().putInt("timeOfDay", 0).apply();

                    activateNotificationsFunction();
                }
                if (motivationSwitch.isActivated())
                {
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit().putBoolean("motivation", true).apply();
                }else if (!motivationSwitch.isActivated())
                {
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit().putBoolean("motivation", false).apply();
                }
                Intent backToMainMenu = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(backToMainMenu);
            }
        });

        notificationsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notificationsAreActivated)
                {
                    Toast.makeText(SettingsActivity.this, "Notifications are turned off", Toast.LENGTH_SHORT).show();
                    notificationsAreActivated = false;

                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit().putInt("timeOfDayHour", hour).apply();
                    PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this).edit().putInt("timeOfDayMinute", minute).apply();


                    activateNotificationsFunction();
                }else {
                    notificationsAreActivated = true;
                    Toast.makeText(SettingsActivity.this, "Notifications are turned on", Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();

                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);

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


    public void popTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                openTimePickerButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}