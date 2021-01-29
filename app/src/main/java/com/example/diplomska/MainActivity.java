package com.example.diplomska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.diplomska.translations.getAlternativeTranslationsarrayGlobal;
import static com.example.diplomska.translations.getSentencesArrayGlobal;
import static com.example.diplomska.translations.getTranslationsArrayGlobal;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    static String[] sentences;
    static String[] translations;
    static String[] altTranslations;
    private static Timer timer;
    private static TimerTask timerTask;
    long oldTime = 0;
    private int counter = 0;


    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TranslateDBHelper dbHelper = new TranslateDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        Button vocabularyButton = (Button) findViewById(R.id.basicVocabularyButton);
        Button translateButton = (Button) findViewById(R.id.translateButton);
        Button understandButton = (Button) findViewById(R.id.understandButton);
        Button testOneButton = (Button) findViewById(R.id.testOneButton);
        Button testTwoButton = (Button) findViewById(R.id.testTwoButton);
        Button testThreeButton = (Button) findViewById(R.id.testThreeButton);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        int Score = PreferenceManager.getDefaultSharedPreferences(this).getInt("Score", 0);
        String scoreString = scoreTextView.getText().toString();
        scoreString = scoreString + Integer.toString(Score);
        scoreTextView.setText(scoreString);




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
                Intent translateIntent = new Intent(MainActivity.this, TranslateActivity.class);
                startActivity(translateIntent);
            }
        });
        understandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent understandIntent = new Intent(MainActivity.this, UnderstandActivity.class);
                startActivity(understandIntent);
            }
        });
        testOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testOneIntent = new Intent(MainActivity.this, TestOneActivity.class);
                startActivity(testOneIntent);
            }
        });
        testTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testTwoIntent = new Intent(MainActivity.this, TestActivity.class);
                //da smeni od mk na uk da se preveduva
                testTwoIntent.putExtra("reverse", "NO");
                startActivity(testTwoIntent);
            }
        });
        testThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testThreeIntent = new Intent(MainActivity.this, TestActivity.class);
                //da smeni od mk na uk da se preveduva
                testThreeIntent.putExtra("reverse", "YES");
                startActivity(testThreeIntent);
            }
        });
        Log.e("important!" , " !!!!!!!!!!!");
        //databaseInitializationFunc();
        addItem();

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

    private void addItem() {

        //TODO: smeni go inicijalniot kod da ne koristi static final za debagiranje sto se
        Log.d("info :" , "Entered in add item function **********");
        sentences = getSentencesArrayGlobal();
        translations = getTranslationsArrayGlobal();
        altTranslations = getAlternativeTranslationsarrayGlobal();
        ContentValues cv = new ContentValues();
        int count = sentences.length;
        Log.e("Number" , "count is" + String.valueOf(count));
        for (int i = 0 ; i < count ; i++)
        {
            cv.put(translateExercise.TranslateEntry.COLUMN_SENTENCE, sentences[i]);
            cv.put(translateExercise.TranslateEntry.COLUMN_TRANSLATION, translations[i]);
            cv.put(translateExercise.TranslateEntry.COLUMN_ALT_TRANSLATION, altTranslations[i]);
            Log.d("info: " , "now putting number" + String.valueOf(i));
            mDatabase.insert(translateExercise.TranslateEntry.TABLE_NAME, null, cv);
        }


        Log.e("database: " , "initialized");
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public void startTimer() {
        Log.i("info: ", "Starting timer");
        stopTimerTask();
        timer = new Timer();

        initializeTimerTask();

        Log.i("info: ", "Scheduling timer...");
        timer.schedule(timerTask, 1000, 1000);
    }

    public void initializeTimerTask() {
        Log.i("info: ", "initialising TimerTask");
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));
                // na sekoi 3 sec azuriraj go rezultatot-scoretextView
                if(counter % 3 == 0)
                {
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();

                    int Score = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getInt("Score", 0);
                    String scoreString = scoreTextView.getText().toString();
                    scoreString = scoreString + Integer.toString(Score);
                    scoreTextView.setText(scoreString);
                }
            }
        };
    }

    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }



}