package com.example.diplomska;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
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

    private static SQLiteDatabase mDatabase;
    static String[] sentences;
    static String[] translations;
    static String[] altTranslations;

    Button vocabularyButton;
    Button translateButton;
    Button understandButton;
    Button matchButton;
    Button testOneButton;
    Button testTwoButton;
    Button testThreeButton;

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

        TranslateDBHelper dbHelper = new TranslateDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        vocabularyButton = (Button) findViewById(R.id.basicVocabularyButton);
        translateButton = (Button) findViewById(R.id.translateButton);
        understandButton = (Button) findViewById(R.id.understandButton);
        matchButton = (Button) findViewById(R.id.matchButton);
        testOneButton = (Button) findViewById(R.id.testOneButton);
        testTwoButton = (Button) findViewById(R.id.testTwoButton);
        testThreeButton = (Button) findViewById(R.id.testThreeButton);

        scoreTextView = (TextView) findViewById(R.id.scoreTextView);

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        setScoreNumberForUI();
        setOnClickListenersForButtons();
        setUpDB();
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
               return true;
            case R.id.achievements_to_open:
                Intent achievementsIntent = new Intent(MainActivity.this, AchievementsActivity.class);
                startActivity(achievementsIntent);
                return true;
            case R.id.download_more_exercises:
                Intent downloadIntent = new Intent(MainActivity.this, DownloadMoreAction.class);
                startActivity(downloadIntent);
               return true;
            
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("Recycle")
    public static void addItem() {
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
        mDatabase.execSQL("DELETE FROM groceryList WHERE _id NOT IN (SELECT MIN(_id) FROM groceryList GROUP BY name);");
    }

    private static boolean doesDatabaseExist(Context context, String dbName) {

        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    private void setScoreNumberForUI()
    {
        int Score = PreferenceManager.getDefaultSharedPreferences(this).getInt("Score", 0);
        String scoreString = scoreTextView.getText().toString();
        scoreString = scoreString + Integer.toString(Score);
        scoreTextView.setText(scoreString);
    }
    private void setOnClickListenersForButtons()
    {
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
        matchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matchIntent = new Intent(MainActivity.this, MatchActivity.class);
                startActivity(matchIntent);
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
    }
    private void setUpDB()
    {
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
}