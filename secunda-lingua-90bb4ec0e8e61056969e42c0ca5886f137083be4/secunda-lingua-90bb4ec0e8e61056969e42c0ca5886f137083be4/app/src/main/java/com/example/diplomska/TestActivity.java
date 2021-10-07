package com.example.diplomska;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TestActivity extends AppCompatActivity {

    TextView qOne, qTwo, qThree, qFour, qFive;
    EditText aOne, aTwo, aThree, aFour, aFive;
    Button quitButton, finishButton;
    int[] chosenQuestions = {1,2,3,4,5,6};
    ArrayList<String> questions = new ArrayList<>();
    ArrayList<String> correctAnswers = new ArrayList<>();
    ArrayList<String> altCorrectAnswers = new ArrayList<>();
    TextView score;
    String isReversed;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        isReversed = intent.getStringExtra("reverse");

        TranslateDBHelper dbHelper = new TranslateDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        qOne = (TextView) findViewById(R.id.testQuestionOne);
        qTwo = (TextView) findViewById(R.id.testQuestionTwo);
        qThree = (TextView) findViewById(R.id.testQuestionThree);
        qFour = (TextView) findViewById(R.id.testQuestionFour);
        qFive = (TextView) findViewById(R.id.testQuestionFive);

        aOne = (EditText) findViewById(R.id.testAnswerOne);
        aTwo = (EditText) findViewById(R.id.testAnswerTwo);
        aThree = (EditText) findViewById(R.id.testAnswerThree);
        aFour = (EditText) findViewById(R.id.testAnswerFour);
        aFive = (EditText) findViewById(R.id.testAnswerFive);

        quitButton = (Button) findViewById(R.id.testQuitButton);
        finishButton = (Button) findViewById(R.id.testFinishButton);

        score = (TextView) findViewById(R.id.testScoreTextView);

        readFromDb();

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quitIntent = new Intent(TestActivity.this, MainActivity.class);
                startActivity(quitIntent);
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReversed.equals("NO"))
                {
                    checkTheAnswers();
                }else{
                    checkReversedAnswers();
                }
                finishButton.setVisibility(View.INVISIBLE);
                quitButton.setText("Back");
            }
        });
        if (savedInstanceState != null)
        {
            //TODO: restore UI
            if (isReversed.equals("NO"))
            {
                altCorrectAnswers = savedInstanceState.getStringArrayList("altCorrectAnswers");
            }
            questions = savedInstanceState.getStringArrayList("questions");
            correctAnswers = savedInstanceState.getStringArrayList("correctAnswers");
            chosenQuestions = savedInstanceState.getIntArray("chosenQuestions");
            aOne.setText(savedInstanceState.getString("answerForOne"));
            aTwo.setText(savedInstanceState.getString("answerForTwo"));
            aThree.setText(savedInstanceState.getString("answerForThree"));
            aFour.setText(savedInstanceState.getString("answerForFour"));
            aFive.setText(savedInstanceState.getString("answerForFive"));
            qOne.setText(questions.get(chosenQuestions[0]));
            qTwo.setText(questions.get(chosenQuestions[1]));
            qThree.setText(questions.get(chosenQuestions[2]));
            qFour.setText(questions.get(chosenQuestions[3]));
            qFive.setText(questions.get(chosenQuestions[4]));
            Log.e("questions", questions + "----");
            Log.e("chose questions", chosenQuestions + "----");

        } else {
            if (isReversed.equals("NO")) {
                chooseQuestionsFunc();
            }
            else {
                chooseReversedQuestionsFunc();
            }
        }

    }

    public void readFromDb()
    {
        Cursor mCursor = mDatabase.query(translateExercise.TranslateEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        Random random = new Random();
        Log.e("----", mCursor.getCount() + "------");
        if (mCursor.getCount() == 10)
        {
            mCursor.moveToFirst();
        } else {
            mCursor.moveToPosition(random.nextInt((mCursor.getCount()-10) - 1) + 1);
        }

        while (!mCursor.isAfterLast())
        {
            String sentence;
            String translation;
            String altTranslation;
            sentence = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_SENTENCE));
            translation = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_TRANSLATION));
            altTranslation = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_ALT_TRANSLATION));
            questions.add(sentence);
            correctAnswers.add(translation);
            altCorrectAnswers.add(altTranslation);
            mCursor.moveToNext();

            Log.e("question", sentence + translation);
        }
        mCursor.close();
    }

    private void checkTheAnswers()
    {
        int points = 0;

        if ((aOne.getText().toString().toLowerCase().trim().equals(correctAnswers.get(chosenQuestions[0]).toLowerCase().trim()))
                || (aOne.getText().toString().toLowerCase().trim().equals(altCorrectAnswers.get(chosenQuestions[0]).toLowerCase().trim())))
        {
            points = points + 1;
            aOne.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aOne.getText().toString();
            pom = pom + " (" + correctAnswers.get(chosenQuestions[0]) + "/" + altCorrectAnswers.get(chosenQuestions[0]) + ")";
            aOne.setText(pom);
            aOne.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aTwo.getText().toString().toLowerCase().trim().equals(correctAnswers.get(chosenQuestions[1]).toLowerCase().trim()))
                || (aTwo.getText().toString().toLowerCase().trim().equals(altCorrectAnswers.get(chosenQuestions[1]).toLowerCase().trim())))
        {
            points = points + 1;
            aTwo.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aTwo.getText().toString();
            pom = pom + " (" +correctAnswers.get(chosenQuestions[1]) +  "/" + altCorrectAnswers.get(chosenQuestions[1]) +")";
            aTwo.setText(pom);
            aTwo.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aThree.getText().toString().toLowerCase().trim().equals(correctAnswers.get(chosenQuestions[2]).toLowerCase().trim()))
                || (aThree.getText().toString().toLowerCase().trim().equals(altCorrectAnswers.get(chosenQuestions[2]).toLowerCase().trim())))
        {
            points = points + 1;
            aThree.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aThree.getText().toString();
            pom = pom + "( " +correctAnswers.get(chosenQuestions[2]) +  "/" + altCorrectAnswers.get(chosenQuestions[2]) +")";
            aThree.setText(pom);
            aThree.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aFour.getText().toString().toLowerCase().trim().equals(correctAnswers.get(chosenQuestions[3]).toLowerCase().trim()))
                || (aFour.getText().toString().toLowerCase().trim().equals(altCorrectAnswers.get(chosenQuestions[3]).toLowerCase().trim())))
        {
            points = points + 1;
            aFour.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aFour.getText().toString();
            pom = pom + " (" +correctAnswers.get(chosenQuestions[3]) +  "/" + altCorrectAnswers.get(chosenQuestions[3]) +")";
            aFour.setText(pom);
            aFour.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aFive.getText().toString().toLowerCase().trim().equals(correctAnswers.get(chosenQuestions[4]).toLowerCase().trim()))
                || (aFive.getText().toString().toLowerCase().trim().equals(altCorrectAnswers.get(chosenQuestions[4]).toLowerCase().trim())))
        {
            points = points + 1;
            aFive.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aFive.getText().toString();
            pom = pom + " (" +correctAnswers.get(chosenQuestions[4]) +  "/" + altCorrectAnswers.get(chosenQuestions[4]) +")";
            aFive.setText(pom);
            aFive.setTextColor(Color.parseColor("#ff0000"));
        }
        String pomString = "Score: " + Integer.toString(points) + "/5";
        score.setText(pomString);
    }

    private void chooseQuestionsFunc()
    {
        ArrayList<Integer> a = new ArrayList<>(10);
        for (int i = 0; i <= 9; i++){
            a.add(i);
        }
        Collections.shuffle(a);
        for(int y = 0 ; y < 5 ; y++)
        {
            chosenQuestions[y] = a.get(y);
        }
        qOne.setText(questions.get(chosenQuestions[0]));
        qTwo.setText(questions.get(chosenQuestions[1]));
        qThree.setText(questions.get(chosenQuestions[2]));
        qFour.setText(questions.get(chosenQuestions[3]));
        qFive.setText(questions.get(chosenQuestions[4]));
    }

    private void chooseReversedQuestionsFunc()
    {
        ArrayList<Integer> a = new ArrayList<>(10);
        for (int i = 0; i <= 9; i++){
            a.add(i);
        }
        Collections.shuffle(a);
        for(int y = 0 ; y < 5 ; y++)
        {
            chosenQuestions[y] = a.get(y);
            Log.e("Number " , Integer.toString(chosenQuestions[y]));
        }
        qOne.setText(correctAnswers.get(chosenQuestions[0]));
        qTwo.setText(correctAnswers.get(chosenQuestions[1]));
        qThree.setText(correctAnswers.get(chosenQuestions[2]));
        qFour.setText(correctAnswers.get(chosenQuestions[3]));
        qFive.setText(correctAnswers.get(chosenQuestions[4]));
    }

    private void checkReversedAnswers()
    {
        Log.e("REVERSED", "`````````````````````````````````````````````````````");
        Log.e("correa", correctAnswers + "");
        Log.e("quest", questions + "");
        int points = 0;

        if ((aOne.getText().toString().toLowerCase().trim().equals(questions.get(chosenQuestions[0]).toLowerCase())))
        {
            points = points + 1;
            aOne.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aOne.getText().toString();
            pom = pom + " (" + questions.get(chosenQuestions[0]) + ")";
            aOne.setText(pom);
            aOne.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aTwo.getText().toString().toLowerCase().trim().equals(questions.get(chosenQuestions[0]).toLowerCase())))
        {
            points = points + 1;
            aTwo.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aTwo.getText().toString();
            pom = pom + " (" + questions.get(chosenQuestions[1]) + ")";
            aTwo.setText(pom);
            aTwo.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aThree.getText().toString().toLowerCase().trim().equals(correctAnswers.get(chosenQuestions[0]).toLowerCase())))
        {
            points = points + 1;
            aThree.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aThree.getText().toString();
            pom = pom + "( " + questions.get(chosenQuestions[2]) + ")";
            aThree.setText(pom);
            aThree.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aFour.getText().toString().toLowerCase().trim().equals(questions.get(chosenQuestions[0]).toLowerCase())))
        {
            points = points + 1;
            aFour.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aFour.getText().toString();
            pom = pom + " (" + questions.get(chosenQuestions[3]) + ")";
            aFour.setText(pom);
            aFour.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aFive.getText().toString().toLowerCase().trim().equals(questions.get(chosenQuestions[0]).toLowerCase())))
        {
            points = points + 1;
            aFive.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aFive.getText().toString();
            pom = pom + " (" + questions.get(chosenQuestions[4]) + ")";
            aFive.setText(pom);
            aFive.setTextColor(Color.parseColor("#ff0000"));
        }
        String pomString = "Score: " + Integer.toString(points) + "/5";
        score.setText(pomString);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.e("sending to state chosen questions", chosenQuestions + "-''''''''''''''''---");
            outState.putIntArray("chosenQuestions", chosenQuestions);
            outState.putStringArrayList("questions", questions);
            outState.putStringArrayList("correctAnswers", correctAnswers);
            outState.putStringArrayList("altCorrectAnswers", altCorrectAnswers);
            outState.putString("answerForOne", aOne.getText().toString());
            outState.putString("answerForTwo", aTwo.getText().toString());
            outState.putString("answerForThree", aThree.getText().toString());
            outState.putString("answerForFour", aFour.getText().toString());
            outState.putString("answerForFive", aFive.getText().toString());

    }
}
