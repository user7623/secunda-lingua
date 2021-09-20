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

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

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
    private int counter = 0;

    TextView scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        ParseObject firstObject = new  ParseObject("FirstClass");
        firstObject.put("message","Hey ! First message from android. Parse is now connected");
        firstObject.saveInBackground(e -> {
            if (e != null){
                Log.e("MainActivity", e.getLocalizedMessage());
            }else{
                Log.d("MainActivity","Object saved.");
            }
        });

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
                //reverse languages uk-mk
                testTwoIntent.putExtra("reverse", "NO");
                startActivity(testTwoIntent);
            }
        });
        testThreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testThreeIntent = new Intent(MainActivity.this, TestActivity.class);
                //reverse languages uk-mk
                testThreeIntent.putExtra("reverse", "YES");
                startActivity(testThreeIntent);
            }
        });
        if (!doesDatabaseExist(this, "mDatabase"))
        {
            Log.e("Database " , "Exists");
            Cursor mCursor = mDatabase.query(translateExercise.TranslateEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            mCursor.moveToFirst();

            int count = mCursor.getCount();
            if (count <= 1) {
                Log.d("info ", "database is empty, initializing default database ");
                addItem();
            }
        }
        else
        {
            Log.e("ERROR", "No database!");
        }
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
                Intent achievementsIntent = new Intent(MainActivity.this, AchievementsActivity.class);
                startActivity(achievementsIntent);
                Toast.makeText(this, "achievements selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.download_more_exercises:
                Intent downloadIntent = new Intent(MainActivity.this, DownloadMoreAction.class);
                startActivity(downloadIntent);
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
        sentences = getSentencesArrayGlobal();
        translations = getTranslationsArrayGlobal();
        altTranslations = getAlternativeTranslationsarrayGlobal();
        ContentValues cv = new ContentValues();
        int count = sentences.length;
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
        stopTimerTask();
        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 1000, 1000);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));
                // on every three seconds update results-scoretextView
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