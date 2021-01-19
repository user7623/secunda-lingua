package com.example.diplomska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TranslateActivity extends AppCompatActivity {

    private SQLiteDatabase mDatabase;
    String hintString = "";
    private static Timer timer;
    private static TimerTask timerTask;
    long oldTime = 0;
    private int counter = 0;
    boolean correctAnswerFlag = true;
    boolean hideResultTextView = false;
    private int questionNumber = 0;
    private int numberOfQuestions = 0;
    int seekBarProgress = 0;
    ArrayList<String> questionsList = new ArrayList<>();
    ArrayList<String> translationsList = new ArrayList<>();
    ArrayList<String> altTranslationsList = new ArrayList<>();
    Button questionMarkButton;
    Button submitButton;
    Button quitButton;
    EditText answerText;
    TextView questionText;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        TranslateDBHelper dbHelper = new TranslateDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        questionMarkButton = (Button) findViewById(R.id.questionMarkButtonTranslate);
        submitButton = (Button) findViewById(R.id.translateSubmitButton);
        quitButton = (Button) findViewById(R.id.quitButtonOnTranslate);
        answerText = (EditText) findViewById(R.id.translateEditText);
        questionText = (TextView) findViewById(R.id.translateTextView);
        seekBar = (SeekBar) findViewById(R.id.seekBarTranslate);

        //seekBar.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(seekBarProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        readFromDb();

        giveQuestionFunc();

        questionMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintString = translationsList.get(questionNumber).toString();
                hintString = hintString + " Or ";
                hintString = hintString + altTranslationsList.get(questionNumber).toString();
                Toast.makeText(TranslateActivity.this, hintString, Toast.LENGTH_LONG).show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("info: " , "entered onClick");
                checkAnswerFunction();
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quitVocabularyIntent = new Intent(TranslateActivity.this, MainActivity.class);
                startActivity(quitVocabularyIntent);
            }
        });
    }

    public void checkAnswerFunction()
    {
        Log.d("info: " , "entered checkAnswerFunction");

        //TODO: kod za proverka na odgovorot

        String answerGiven = answerText.getText().toString().toLowerCase();
        String answerRequired = translationsList.get(questionNumber); //answersArray[questionNumber];
        String alAnswerRequired = altTranslationsList.get(questionNumber);
        Log.d("info: " , answerGiven + answerRequired + alAnswerRequired);
        if (answerText.getText().toString().toLowerCase().equals(translationsList.get(questionNumber).toLowerCase()))
        {
            seekBarProgress = seekBarProgress + 10;
            seekBar.setProgress(seekBarProgress);
            correctAnswerFlag = true;
        }
        else if (answerText.getText().toString().toLowerCase().equals(altTranslationsList.get(questionNumber).toLowerCase()))
        {
            seekBarProgress = seekBarProgress + 10;
            seekBar.setProgress(seekBarProgress);
            correctAnswerFlag = true;
        }else{

            seekBarProgress = seekBarProgress - 10;
            seekBar.setProgress(seekBarProgress);
            correctAnswerFlag = false;
        }

        //soodvetno izvestuvanje
        if (correctAnswerFlag)
        {
            Log.d("info: " , "Correct answer");
            showCorrectToast();
        }else{
            Log.d("info: " , "Incorrect answer");
            showWrongToast();
        }
        //edit text poleto isprazni go
        answerText.setText("");

        if (questionNumber == (numberOfQuestions - 1))
        {
            endCourseFunc();
        } else {
            nextQuestionFunc();
        }
    }

    private void showCorrectToast()
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.correct_answer_toast_layout, (ViewGroup) findViewById(R.id.correct_toast_root));
        ImageView toastImage = layout.findViewById(R.id.correct_toast_image);
        toastImage.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
    private void showWrongToast()
    {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.wrong_answer_toast_layout, (ViewGroup) findViewById(R.id.wrong_toast_root));
        ImageView toastImage = layout.findViewById(R.id.wrong_toast_image);
        toastImage.setImageResource(R.drawable.incorect_smiley);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
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

                if(counter == 1)
                {

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

    public  void giveQuestionFunc()
    {
        Log.d("info:" , "setting question, question number is" + questionNumber);
        questionText.setText(questionsList.get(questionNumber));
        //questionNumber++;
    }

    public void readFromDb()
    {
        //TODO: napravi da cita od db !!!
        Cursor mCursor = mDatabase.query(translateExercise.TranslateEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        mCursor.moveToFirst();

        int count = mCursor.getCount();
        Log.d("info " , "found " + String.valueOf(count) + "number of rows in database **********");
        while (!mCursor.isAfterLast())
        {
            String sent;
            String translation;
            String altTranslation;
            sent = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_SENTENCE));
            translation = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_TRANSLATION));
            altTranslation = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_ALT_TRANSLATION));
            Log.e("READING FROM DATABASE:" , sent + translation);

            questionsList.add(sent);
            translationsList.add(translation);
            altTranslationsList.add(altTranslation);
            mCursor.moveToNext();
        }

        mCursor.close();

    }

    private void nextQuestionFunc()
    {
        questionNumber++;
        questionText.setText(questionsList.get(questionNumber));

    }

    private void endCourseFunc()
    {
        Log.d("info", "ending translate activity --------------------------------------");
        Intent endTranslateActivity = new Intent(TranslateActivity.this, MainActivity.class);
        startActivity(endTranslateActivity);
    }

}