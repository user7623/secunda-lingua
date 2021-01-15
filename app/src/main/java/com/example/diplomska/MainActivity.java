package com.example.diplomska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        Button vocabularyButton = (Button) findViewById(R.id.basicVocabularyButton);
        Button translateButton = (Button) findViewById(R.id.translateButton);
        Button understandButton = (Button) findViewById(R.id.understandButton);
        Button testOneButton = (Button) findViewById(R.id.testOneButton);
        Button testTwoButton = (Button) findViewById(R.id.testTwoButton);
        Button testThreeButton = (Button) findViewById(R.id.testThreeButton);

        vocabularyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent basicVocabularyIntent = new Intent(MainActivity.this, VocabularyActivity.class);
                startActivity(basicVocabularyIntent);
            }
        });
        translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent translateIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(translateIntent);
            }
        });
        understandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent understandIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(understandIntent);
            }
        });
        testOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testOneIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(testOneIntent);
            }
        });
        testTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testTwoIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(testTwoIntent);
            }
        });
        testThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testThreeIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(testThreeIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_to_open:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                Toast.makeText(this, "settings selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.achievements_to_open:
                Toast.makeText(this, "achievements selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.download_more_exercises:
                Toast.makeText(this, "more exercises selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logoutButton:
                Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}