package com.example.diplomska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Button backButton = (Button) findViewById(R.id.settings_menu_back_button);

        Switch notificationsSwitch = (Switch) findViewById(R.id.notification_switch);
        Switch motivationSwitch = (Switch) findViewById(R.id.motivations_switch);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToMainMenu = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(backToMainMenu);
            }
        });

        notificationsSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tuka vnesi kod za promena na promenlivata za notifikacii
            }
        });

        motivationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // tuka vnesi kod za promena na promenlivata za motivacioni poraki
            }
        });
    }
}