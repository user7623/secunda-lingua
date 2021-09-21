package com.example.diplomska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
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

        makeOnClickListenersForButtons();
        TranslateDBHelper dbHelper = new TranslateDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        readFromDB();
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
                    leftButtonOne.setBackgroundColor(Color.BLUE);
                    numberOfLeftButtonSelected = 1;
                    leftButtonTwo.setClickable(false);
                    leftButtonTwo.setBackgroundColor(Color.GRAY);
                    leftButtonThree.setClickable(false);
                    leftButtonThree.setBackgroundColor(Color.GRAY);
                    leftButtonFour.setClickable(false);
                    leftButtonFour.setBackgroundColor(Color.GRAY);
                    leftButtonFive.setClickable(false);
                    leftButtonFive.setBackgroundColor(Color.GRAY);

                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 1)
                {
                    leftButtonOne.setBackgroundColor(Color.GREEN);
                    numberOfLeftButtonSelected = 0;
                    leftButtonTwo.setClickable(true);
                    leftButtonTwo.setBackgroundColor(Color.GREEN);
                    leftButtonThree.setClickable(true);
                    leftButtonThree.setBackgroundColor(Color.GREEN);
                    leftButtonFour.setClickable(true);
                    leftButtonFour.setBackgroundColor(Color.GREEN);
                    leftButtonFive.setClickable(true);
                    leftButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        leftButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfLeftButtonSelected != 2)
                {
                    leftButtonOne.setBackgroundColor(Color.GRAY);
                    leftButtonOne.setClickable(false);
                    numberOfLeftButtonSelected = 2;
                    leftButtonTwo.setBackgroundColor(Color.BLUE);
                    leftButtonThree.setClickable(false);
                    leftButtonThree.setBackgroundColor(Color.GRAY);
                    leftButtonFour.setClickable(false);
                    leftButtonFour.setBackgroundColor(Color.GRAY);
                    leftButtonFive.setClickable(false);
                    leftButtonFive.setBackgroundColor(Color.GRAY);

                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 2)
                {
                    leftButtonOne.setBackgroundColor(Color.GREEN);
                    leftButtonOne.setClickable(true);
                    numberOfLeftButtonSelected = 0;
                    leftButtonTwo.setBackgroundColor(Color.GREEN);
                    leftButtonThree.setClickable(true);
                    leftButtonThree.setBackgroundColor(Color.GREEN);
                    leftButtonFour.setClickable(true);
                    leftButtonFour.setBackgroundColor(Color.GREEN);
                    leftButtonFive.setClickable(true);
                    leftButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        leftButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfLeftButtonSelected != 3)
                {
                    leftButtonOne.setBackgroundColor(Color.GRAY);
                    leftButtonOne.setClickable(false);
                    numberOfLeftButtonSelected = 3;
                    leftButtonTwo.setClickable(false);
                    leftButtonTwo.setBackgroundColor(Color.GRAY);
                    leftButtonThree.setBackgroundColor(Color.BLUE);
                    leftButtonFour.setClickable(false);
                    leftButtonFour.setBackgroundColor(Color.GRAY);
                    leftButtonFive.setClickable(false);
                    leftButtonFive.setBackgroundColor(Color.GRAY);

                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 3)
                {
                    leftButtonOne.setBackgroundColor(Color.GREEN);
                    leftButtonOne.setClickable(true);
                    numberOfLeftButtonSelected = 0;
                    leftButtonTwo.setClickable(true);
                    leftButtonTwo.setBackgroundColor(Color.GREEN);
                    leftButtonThree.setBackgroundColor(Color.GREEN);
                    leftButtonFour.setClickable(true);
                    leftButtonFour.setBackgroundColor(Color.GREEN);
                    leftButtonFive.setClickable(true);
                    leftButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        leftButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfLeftButtonSelected != 4)
                {
                    leftButtonOne.setBackgroundColor(Color.GRAY);
                    leftButtonOne.setClickable(false);
                    numberOfLeftButtonSelected = 4;
                    leftButtonTwo.setClickable(false);
                    leftButtonTwo.setBackgroundColor(Color.GRAY);
                    leftButtonThree.setClickable(false);
                    leftButtonThree.setBackgroundColor(Color.GRAY);
                    leftButtonFour.setBackgroundColor(Color.BLUE);
                    leftButtonFive.setClickable(false);
                    leftButtonFive.setBackgroundColor(Color.GRAY);

                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 4)
                {
                    leftButtonOne.setBackgroundColor(Color.GREEN);
                    leftButtonOne.setClickable(true);
                    numberOfLeftButtonSelected = 0;
                    leftButtonTwo.setClickable(true);
                    leftButtonTwo.setBackgroundColor(Color.GREEN);
                    leftButtonThree.setClickable(true);
                    leftButtonThree.setBackgroundColor(Color.GREEN);
                    leftButtonFour.setBackgroundColor(Color.GREEN);
                    leftButtonFive.setClickable(true);
                    leftButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        leftButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfLeftButtonSelected != 5)
                {
                    leftButtonOne.setBackgroundColor(Color.GRAY);
                    leftButtonOne.setClickable(false);
                    numberOfLeftButtonSelected = 5;
                    leftButtonTwo.setClickable(false);
                    leftButtonTwo.setBackgroundColor(Color.GRAY);
                    leftButtonThree.setClickable(false);
                    leftButtonThree.setBackgroundColor(Color.GRAY);
                    leftButtonFour.setClickable(false);
                    leftButtonFour.setBackgroundColor(Color.GRAY);
                    leftButtonFive.setBackgroundColor(Color.BLUE);

                    checkAnswer();
                }
                else if (numberOfLeftButtonSelected == 5)
                {
                    leftButtonOne.setBackgroundColor(Color.GREEN);
                    leftButtonOne.setClickable(true);
                    numberOfLeftButtonSelected = 0;
                    leftButtonTwo.setClickable(true);
                    leftButtonTwo.setBackgroundColor(Color.GREEN);
                    leftButtonThree.setClickable(true);
                    leftButtonThree.setBackgroundColor(Color.GREEN);
                    leftButtonFour.setClickable(true);
                    leftButtonFour.setBackgroundColor(Color.GREEN);
                    leftButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        rightButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 1)
                {
                    rightButtonOne.setBackgroundColor(Color.BLUE);
                    numberOfRightButtonSelected = 1;
                    rightButtonTwo.setClickable(false);
                    rightButtonTwo.setBackgroundColor(Color.GRAY);
                    rightButtonThree.setClickable(false);
                    rightButtonThree.setBackgroundColor(Color.GRAY);
                    rightButtonFour.setClickable(false);
                    rightButtonFour.setBackgroundColor(Color.GRAY);
                    rightButtonFive.setClickable(false);
                    rightButtonFive.setBackgroundColor(Color.GRAY);

                    checkAnswer();
                }
                else if (numberOfRightButtonSelected == 1)
                {
                    rightButtonOne.setBackgroundColor(Color.GREEN);
                    numberOfRightButtonSelected = 0;
                    rightButtonTwo.setClickable(true);
                    rightButtonTwo.setBackgroundColor(Color.GREEN);
                    rightButtonThree.setClickable(true);
                    rightButtonThree.setBackgroundColor(Color.GREEN);
                    rightButtonFour.setClickable(true);
                    rightButtonFour.setBackgroundColor(Color.GREEN);
                    rightButtonFive.setClickable(true);
                    rightButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        rightButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 2)
                {
                    rightButtonOne.setBackgroundColor(Color.GRAY);
                    rightButtonOne.setClickable(false);
                    numberOfRightButtonSelected = 2;
                    rightButtonTwo.setBackgroundColor(Color.BLUE);
                    rightButtonThree.setClickable(false);
                    rightButtonThree.setBackgroundColor(Color.GRAY);
                    rightButtonFour.setClickable(false);
                    rightButtonFour.setBackgroundColor(Color.GRAY);
                    rightButtonFive.setClickable(false);
                    rightButtonFive.setBackgroundColor(Color.GRAY);

                    checkAnswer();
                }
                else if (numberOfRightButtonSelected == 2)
                {
                    rightButtonOne.setBackgroundColor(Color.GREEN);
                    rightButtonOne.setClickable(true);
                    numberOfRightButtonSelected = 0;
                    rightButtonTwo.setBackgroundColor(Color.GREEN);
                    rightButtonThree.setClickable(true);
                    rightButtonThree.setBackgroundColor(Color.GREEN);
                    rightButtonFour.setClickable(true);
                    rightButtonFour.setBackgroundColor(Color.GREEN);
                    rightButtonFive.setClickable(true);
                    rightButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        rightButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 3)
                {
                    rightButtonOne.setBackgroundColor(Color.GRAY);
                    rightButtonOne.setClickable(false);
                    numberOfRightButtonSelected = 3;
                    rightButtonTwo.setClickable(false);
                    rightButtonTwo.setBackgroundColor(Color.GRAY);
                    rightButtonThree.setBackgroundColor(Color.BLUE);
                    rightButtonFour.setClickable(false);
                    rightButtonFour.setBackgroundColor(Color.GRAY);
                    rightButtonFive.setClickable(false);
                    rightButtonFive.setBackgroundColor(Color.GRAY);

                    checkAnswer();
                }
                else if (numberOfRightButtonSelected == 3)
                {
                    rightButtonOne.setBackgroundColor(Color.GREEN);
                    rightButtonOne.setClickable(true);
                    numberOfRightButtonSelected = 0;
                    rightButtonTwo.setBackgroundColor(Color.GREEN);
                    rightButtonTwo.setClickable(true);
                    rightButtonThree.setBackgroundColor(Color.GREEN);
                    rightButtonFour.setClickable(true);
                    rightButtonFour.setBackgroundColor(Color.GREEN);
                    rightButtonFive.setClickable(true);
                    rightButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        rightButtonFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 4)
                {
                    rightButtonOne.setBackgroundColor(Color.GRAY);
                    rightButtonOne.setClickable(false);
                    numberOfRightButtonSelected = 4;
                    rightButtonTwo.setClickable(false);
                    rightButtonTwo.setBackgroundColor(Color.GRAY);
                    rightButtonThree.setBackgroundColor(Color.GRAY);
                    rightButtonThree.setClickable(false);
                    rightButtonFour.setBackgroundColor(Color.BLUE);
                    rightButtonFive.setClickable(false);
                    rightButtonFive.setBackgroundColor(Color.GRAY);

                    checkAnswer();
                }
                else if (numberOfRightButtonSelected == 4)
                {
                    rightButtonOne.setBackgroundColor(Color.GREEN);
                    rightButtonOne.setClickable(true);
                    numberOfRightButtonSelected = 0;
                    rightButtonTwo.setBackgroundColor(Color.GREEN);
                    rightButtonTwo.setClickable(true);
                    rightButtonThree.setBackgroundColor(Color.GREEN);
                    rightButtonThree.setClickable(true);
                    rightButtonFour.setBackgroundColor(Color.GREEN);
                    rightButtonFive.setClickable(true);
                    rightButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
        rightButtonFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfRightButtonSelected != 5)
                {
                    rightButtonOne.setBackgroundColor(Color.GRAY);
                    rightButtonOne.setClickable(false);
                    numberOfRightButtonSelected = 5;
                    rightButtonTwo.setClickable(false);
                    rightButtonTwo.setBackgroundColor(Color.GRAY);
                    rightButtonThree.setBackgroundColor(Color.GRAY);
                    rightButtonThree.setClickable(false);
                    rightButtonFour.setBackgroundColor(Color.GRAY);
                    rightButtonFour.setClickable(false);
                    rightButtonFive.setBackgroundColor(Color.BLUE);

                    checkAnswer();
                }
                else if (numberOfRightButtonSelected == 5)
                {
                    rightButtonOne.setBackgroundColor(Color.GREEN);
                    rightButtonOne.setClickable(true);
                    numberOfRightButtonSelected = 0;
                    rightButtonTwo.setBackgroundColor(Color.GREEN);
                    rightButtonTwo.setClickable(true);
                    rightButtonThree.setBackgroundColor(Color.GREEN);
                    rightButtonThree.setClickable(true);
                    rightButtonFour.setBackgroundColor(Color.GREEN);
                    rightButtonFour.setClickable(true);
                    rightButtonFive.setBackgroundColor(Color.GREEN);
                }
            }
        });
    }

    private void checkAnswer() {
        //if answer is correct
        if (numberOfLeftButtonSelected == numberOfRightButtonSelected)
        {
            addButtonsToAnswered();
            resetColours();
        }//if answer is incorrect
        else{
            resetColours();
        }
    }
    public void resetColours()
    {
        rightButtonOne.setBackgroundColor(Color.GREEN);
        rightButtonOne.setClickable(true);
        numberOfRightButtonSelected = 0;
        rightButtonTwo.setBackgroundColor(Color.GREEN);
        rightButtonTwo.setClickable(true);
        rightButtonThree.setBackgroundColor(Color.GREEN);
        rightButtonThree.setClickable(true);
        rightButtonFour.setBackgroundColor(Color.GREEN);
        rightButtonFour.setClickable(true);
        rightButtonFive.setBackgroundColor(Color.GREEN);
        rightButtonFive.setClickable(true);

        leftButtonOne.setBackgroundColor(Color.GREEN);
        leftButtonOne.setClickable(true);
        numberOfLeftButtonSelected = 0;
        leftButtonTwo.setClickable(true);
        leftButtonTwo.setBackgroundColor(Color.GREEN);
        leftButtonThree.setClickable(true);
        leftButtonThree.setBackgroundColor(Color.GREEN);
        leftButtonFour.setClickable(true);
        leftButtonFour.setBackgroundColor(Color.GREEN);
        leftButtonFive.setBackgroundColor(Color.GREEN);
        leftButtonFive.setClickable(true);
    }

    public void addButtonsToAnswered()
    {

    }
}