package com.example.diplomska;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class MatchActivity extends AppCompatActivity {

    Button quitButton;

    Button leftButtonOne;
    Button leftButtonTwo;
    Button leftButtonThree;
    Button leftButtonFour;
    Button leftButtonFive;

    Button rightButtonOne;
    Button rightButtonTwo;
    Button rightButtonThree;
    Button rightButtonFour;
    Button rightButtonFive;

    ArrayList<String> questionsList = new ArrayList<>();
    ArrayList<String> translationsList = new ArrayList<>();

    int numberOfLeftButtonSelected = 0;
    int numberOfRightButtonSelected = 0;

    private SQLiteDatabase mDatabase;

    ArrayList<Integer> answeredLeft = new ArrayList<>();
    ArrayList<Integer> answeredRight= new ArrayList<>();

    SeekBar seekBar;
    int seekBarProgress = 0;

    QuestionAnswerClass QACone = new QuestionAnswerClass();
    QuestionAnswerClass QACtwo = new QuestionAnswerClass();
    QuestionAnswerClass QACthree = new QuestionAnswerClass();
    QuestionAnswerClass QACfour = new QuestionAnswerClass();
    QuestionAnswerClass QACfive = new QuestionAnswerClass();

    HashMap<Integer, Integer> improvisedPointers = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        quitButton = (Button) findViewById(R.id.quitButtonOnMatch);

        leftButtonOne = (Button) findViewById(R.id.matchButtonOneLeft);
        leftButtonTwo = (Button) findViewById(R.id.matchButtonTwoLeft);
        leftButtonThree = (Button) findViewById(R.id.matchButtonThreeLeft);
        leftButtonFour = (Button) findViewById(R.id.matchButtonFourLeft);
        leftButtonFive = (Button) findViewById(R.id.matchButtonFiveLeft);

        rightButtonOne = (Button) findViewById(R.id.matchButtonOneRight);
        rightButtonTwo = (Button) findViewById(R.id.matchButtonTwoRight);
        rightButtonThree = (Button) findViewById(R.id.matchButtonThreeRight);
        rightButtonFour = (Button) findViewById(R.id.matchButtonFourRight);
        rightButtonFive = (Button) findViewById(R.id.matchButtonFiveRight);

        seekBar = (SeekBar) findViewById(R.id.seekBarMatch);

        setUpSeekBar();

        makeOnClickListenersForButtons();

        TranslateDBHelper dbHelper = new TranslateDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        readFromDB();
        setUpQAClasses();
        shuffleAnswers();
        setButtonsText();
    }

    public void setButtonsText() {
        leftButtonOne.setText(questionsList.get(0));
        leftButtonTwo.setText(questionsList.get(1));
        leftButtonThree.setText(questionsList.get(2));
        leftButtonFour.setText(questionsList.get(3));
        leftButtonFive.setText(questionsList.get(4));

        rightButtonOne.setText(translationsList.get(0));
        rightButtonTwo.setText(translationsList.get(1));
        rightButtonThree.setText(translationsList.get(2));
        rightButtonFour.setText(translationsList.get(3));
        rightButtonFive.setText(translationsList.get(4));
    }

    public void readFromDB()
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

        int counter = 5;
        while (!mCursor.isAfterLast() && counter > 0)
        {
            String sentence;
            String translation;
            sentence = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_SENTENCE));
            translation = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_TRANSLATION));
            questionsList.add(sentence);
            translationsList.add(translation);
            mCursor.moveToNext();
            counter = counter - 1;
            Log.e("question", sentence + translation);
        }
        mCursor.close();
    }

    private void makeOnClickListenersForButtons(){
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quitIntent = new Intent(MatchActivity.this, MainActivity.class);
                startActivity(quitIntent);
            }
        });
        leftButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (numberOfLeftButtonSelected != 1)
                {
                    numberOfLeftButtonSelected = 1;
                    highlightSelection(numberOfLeftButtonSelected, true);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 1)
                {
                    resetColoursLeft();
                }
            }
        });
        leftButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfLeftButtonSelected != 2)
                {
                    numberOfLeftButtonSelected = 2;
                    highlightSelection(numberOfLeftButtonSelected, true);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 2)
                {
                    resetColoursLeft();
                }
            }
        });
        leftButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfLeftButtonSelected != 3)
                {
                    numberOfLeftButtonSelected = 3;
                    highlightSelection(numberOfLeftButtonSelected, true);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 3)
                {
                    resetColoursLeft();
                }
            }
        });
        leftButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfLeftButtonSelected != 4)
                {
                    numberOfLeftButtonSelected = 4;
                    highlightSelection(numberOfLeftButtonSelected, true);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 4)
                {
                    resetColoursLeft();
                }
            }
        });
        leftButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfLeftButtonSelected != 5)
                {
                    numberOfLeftButtonSelected = 5;
                    highlightSelection(numberOfLeftButtonSelected, true);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 5)
                {
                    resetColoursLeft();
                }
            }
        });
        rightButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 1)
                {
                    numberOfRightButtonSelected = 1;
                    highlightSelection(numberOfRightButtonSelected, false);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 1)
                {
                    resetColoursRight();
                }
            }
        });
        rightButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 2)
                {
                    numberOfRightButtonSelected = 2;
                    highlightSelection(numberOfRightButtonSelected, false);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 2)
                {
                    resetColoursRight();
                }
            }
        });
        rightButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 3)
                {
                    numberOfRightButtonSelected = 3;
                    highlightSelection(numberOfRightButtonSelected, false);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 3)
                {
                    resetColoursRight();
                }
            }
        });
        rightButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 4)
                {
                    numberOfRightButtonSelected = 4;
                    highlightSelection(numberOfRightButtonSelected, false);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 4)
                {
                    resetColoursRight();
                }
            }
        });
        rightButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 5)
                {
                    numberOfRightButtonSelected = 5;
                    highlightSelection(numberOfRightButtonSelected, false);
                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 5)
                {
                    resetColoursRight();
                }
            }
        });
    }

    public void highlightSelection(int numberOfSelectedButton, boolean isOnLeft) {
        if (!isOnLeft) {
            rightButtonOne.setBackgroundColor(Color.GRAY);
            rightButtonOne.setClickable(false);
            rightButtonTwo.setClickable(false);
            rightButtonTwo.setBackgroundColor(Color.GRAY);
            rightButtonThree.setBackgroundColor(Color.GRAY);
            rightButtonThree.setClickable(false);
            rightButtonFour.setBackgroundColor(Color.GRAY);
            rightButtonFour.setClickable(false);
            rightButtonFive.setClickable(false);
            rightButtonFive.setBackgroundColor(Color.GRAY);

            switch (numberOfSelectedButton) {
                case 1:
                    rightButtonOne.setClickable(true);
                    rightButtonOne.setBackgroundColor(Color.BLUE);
                    break;
                case 2:
                    rightButtonTwo.setClickable(true);
                    rightButtonTwo.setBackgroundColor(Color.BLUE);
                    break;
                case 3:
                    rightButtonThree.setClickable(true);
                    rightButtonThree.setBackgroundColor(Color.BLUE);
                    break;
                case 4:
                    rightButtonFour.setClickable(true);
                    rightButtonFour.setBackgroundColor(Color.BLUE);
                    break;
                case 5:
                    rightButtonFive.setClickable(true);
                    rightButtonFive.setBackgroundColor(Color.BLUE);
                default:
                    break;
            }
        } else {
            leftButtonOne.setBackgroundColor(Color.GRAY);
            leftButtonOne.setClickable(false);
            leftButtonTwo.setClickable(false);
            leftButtonTwo.setBackgroundColor(Color.GRAY);
            leftButtonThree.setBackgroundColor(Color.GRAY);
            leftButtonThree.setClickable(false);
            leftButtonFour.setBackgroundColor(Color.GRAY);
            leftButtonFour.setClickable(false);
            leftButtonFive.setClickable(false);
            leftButtonFive.setBackgroundColor(Color.GRAY);

            switch (numberOfSelectedButton) {
                case 1:
                    leftButtonOne.setClickable(true);
                    leftButtonOne.setBackgroundColor(Color.BLUE);
                    break;
                case 2:
                    leftButtonTwo.setClickable(true);
                    leftButtonTwo.setBackgroundColor(Color.BLUE);
                    break;
                case 3:
                    leftButtonThree.setClickable(true);
                    leftButtonThree.setBackgroundColor(Color.BLUE);
                    break;
                case 4:
                    leftButtonFour.setClickable(true);
                    leftButtonFour.setBackgroundColor(Color.BLUE);
                    break;
                case 5:
                    leftButtonFive.setClickable(true);
                    leftButtonFive.setBackgroundColor(Color.BLUE);
                    break;
                default:
                    break;
            }

        }

    }

    private void checkAnswer() {

        if (numberOfLeftButtonSelected != 0 && numberOfRightButtonSelected != 0)
        {
            String rQuestion = "";
            String rAnswer = "";
            //get selected word(question) and its translation(answer)
            switch (numberOfLeftButtonSelected){
                case 1: rQuestion = leftButtonOne.getText().toString(); break;
                case 2: rQuestion = leftButtonTwo.getText().toString(); break;
                case 3: rQuestion = leftButtonThree.getText().toString(); break;
                case 4: rQuestion = leftButtonFour.getText().toString(); break;
                case 5: rQuestion = leftButtonFive.getText().toString(); break;
                default: break;
            }
            switch (numberOfRightButtonSelected){
                case 1: rAnswer = rightButtonOne.getText().toString(); break;
                case 2: rAnswer = rightButtonTwo.getText().toString(); break;
                case 3: rAnswer = rightButtonThree.getText().toString(); break;
                case 4: rAnswer = rightButtonFour.getText().toString(); break;
                case 5: rAnswer = rightButtonFive.getText().toString(); break;
                default: break;
            }
            //check against all pairs&if true save as answered correct
            if(QACone.checkIfValid(rQuestion, rAnswer)){
                addButtonsToAnswered(numberOfLeftButtonSelected, numberOfRightButtonSelected);
            }
            if(QACtwo.checkIfValid(rQuestion, rAnswer)){
                addButtonsToAnswered(numberOfLeftButtonSelected, numberOfRightButtonSelected);
            }
            if(QACthree.checkIfValid(rQuestion, rAnswer)){
                addButtonsToAnswered(numberOfLeftButtonSelected, numberOfRightButtonSelected);
            }
            if(QACfour.checkIfValid(rQuestion, rAnswer)){
                addButtonsToAnswered(numberOfLeftButtonSelected, numberOfRightButtonSelected);
            }
            if(QACfive.checkIfValid(rQuestion, rAnswer)){
                addButtonsToAnswered(numberOfLeftButtonSelected, numberOfRightButtonSelected);
            }
            resetColoursLeft();
            resetColoursRight();
        }
        /*
        //if answer is correct
        if (numberOfLeftButtonSelected == numberOfRightButtonSelected)
        {
            addButtonsToAnswered(numberOfRightButtonSelected, numberOfLeftButtonSelected);
        }//if answer is incorrect
        else if (numberOfLeftButtonSelected != 0 && numberOfRightButtonSelected != 0){
            resetColoursLeft();
            resetColoursRight();
        }*/
    }
    public void resetColoursLeft()
    {
        Log.e("answered left", answeredLeft + "!");
        if (!answeredLeft.contains(1))
        {
            leftButtonOne.setBackgroundColor(Color.GREEN);
            leftButtonOne.setClickable(true);
        }
        if (!answeredLeft.contains(2))
        {
            leftButtonTwo.setClickable(true);
            leftButtonTwo.setBackgroundColor(Color.GREEN);
        }
        if (!answeredLeft.contains(3))
        {
            leftButtonThree.setClickable(true);
            leftButtonThree.setBackgroundColor(Color.GREEN);
        }
        if (!answeredLeft.contains(4))
        {
            leftButtonFour.setClickable(true);
            leftButtonFour.setBackgroundColor(Color.GREEN);
        }
        if (!answeredLeft.contains(5))
        {
            leftButtonFive.setClickable(true);
            leftButtonFive.setBackgroundColor(Color.GREEN);
        }
        numberOfLeftButtonSelected = 0;
    }
    public void resetColoursRight()
    {
        Log.e("answered left", answeredLeft + "!");
        if (!answeredRight.contains(1))
        {
            rightButtonOne.setBackgroundColor(Color.GREEN);
            rightButtonOne.setClickable(true);
        }
        if (!answeredRight.contains(2))
        {
            rightButtonTwo.setClickable(true);
            rightButtonTwo.setBackgroundColor(Color.GREEN);
        }
        if (!answeredRight.contains(3))
        {
            rightButtonThree.setClickable(true);
            rightButtonThree.setBackgroundColor(Color.GREEN);
        }
        if (!answeredRight.contains(4))
        {
            rightButtonFour.setClickable(true);
            rightButtonFour.setBackgroundColor(Color.GREEN);
        }
        if (!answeredRight.contains(5))
        {
            rightButtonFive.setClickable(true);
            rightButtonFive.setBackgroundColor(Color.GREEN);
        }
        numberOfRightButtonSelected = 0;
    }

    public void addButtonsToAnswered(int left, int right)
    {
        Log.e("Buttons", "have been added to answered-----------");
        answeredLeft.add(left);
        answeredRight.add(right);
        resetColoursLeft();
        resetColoursRight();

        seekBarProgress = seekBarProgress + 20;
        setSeekBarColor();

        if (seekBarProgress >= 100)
        {
            endCourseFunc();
        }
    }

    public void setUpSeekBar()
    {
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
    }
    private void setSeekBarColor()
    {
        Log.e("SEEKBAR", "setingh up colour" + seekBarProgress);
        seekBar.setProgress(seekBarProgress);
        if (seekBarProgress > 40 && seekBarProgress < 80)
        {
            seekBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }
        if (seekBarProgress >= 80)
        {
            seekBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        if (seekBarProgress <= 40)
        {
            seekBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
    }

    private void endCourseFunc()
    {
        if (seekBarProgress >= 100) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            int Score = PreferenceManager.getDefaultSharedPreferences(this).getInt("Score", 0);

            Score = Score + seekBarProgress;
            PreferenceManager.getDefaultSharedPreferences(MatchActivity.this).edit().putInt("Score", Score).apply();
        }

        Intent endTranslateActivity = new Intent(MatchActivity.this, MainActivity.class);
        startActivity(endTranslateActivity);
    }

    public class QuestionAnswerClass{

        String question;
        String answer;

        public boolean checkIfValid(String rQuestion, String rAnswer) {
            return question.trim().toLowerCase().equals(rQuestion.trim().toLowerCase())
                    && answer.trim().toLowerCase().equals(rAnswer.trim().toLowerCase());
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }
    }

    public void setUpQAClasses() {
        QACone.setQuestion(questionsList.get(0));
        QACtwo.setQuestion(questionsList.get(1));
        QACthree.setQuestion(questionsList.get(2));
        QACfour.setQuestion(questionsList.get(3));
        QACfive.setQuestion(questionsList.get(4));

        QACone.setAnswer(translationsList.get(0));
        QACtwo.setAnswer(translationsList.get(1));
        QACthree.setAnswer(translationsList.get(2));
        QACfour.setAnswer(translationsList.get(3));
        QACfive.setAnswer(translationsList.get(4));
    }

    public void shuffleAnswers() {
        Collections.shuffle(translationsList);
    }
}