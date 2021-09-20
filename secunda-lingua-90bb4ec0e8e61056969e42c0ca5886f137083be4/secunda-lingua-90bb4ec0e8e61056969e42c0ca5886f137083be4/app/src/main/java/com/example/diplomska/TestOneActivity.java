package com.example.diplomska;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class TestOneActivity extends AppCompatActivity {

    EditText answerOne, answerTwo, answerThree, answerFour, answerFive;
    Button quitButton, finishButton;
    TextView qOne, qTwo, qThree, qFour, qFive;
    TextView score;
    int[] chosenQuestions = {1,2,3,4,5,6};
    String[] questions = {"Hello" , "Bye", "My name is", "Im thirsty", "Im hungry", "Im happy", "Im tired", "My", "Your", "Food"};
    String[] correctAnswers = {"zdravo" ,"Cao", "moeto ime e", "Zeden sum", "Gladen sum","Srekjen sum", "Umoren sum", "Moe", "Tvoe", "Hrana"};
    String[] altCorrectAnswers = {"zdravo" ,"Prijatno", "Jas se vikam", "Jas sum zeden", "Jas sum gladen","Jas sum srekjen", "Jas sum umoren", "Moe", "Tvoe", "Jadenje"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_one);

        answerOne = (EditText) findViewById(R.id.testOneAnswerOne);
        answerTwo = (EditText) findViewById(R.id.testOneAnswerTwo);
        answerThree = (EditText) findViewById(R.id.testOneAnswerThree);
        answerFour = (EditText) findViewById(R.id.testOneAnswerFour);
        answerFive = (EditText) findViewById(R.id.testOneAnswerFive);
        score = (TextView) findViewById(R.id.testOneScoreTextView);

        qOne = (TextView) findViewById(R.id.testOneQuestionOne);
        qTwo = (TextView) findViewById(R.id.testOneQuestionTwo);
        qThree = (TextView) findViewById(R.id.testOneQuestionThree);
        qFour = (TextView) findViewById(R.id.testOneQuestionFour);
        qFive = (TextView) findViewById(R.id.testOneQuestionFive);

        quitButton = (Button) findViewById(R.id.testOneQuitButton);
        finishButton = (Button) findViewById(R.id.testOneFinishButton);

       // chooseQuestionsFunc();

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testTwoIntent = new Intent(TestOneActivity.this, MainActivity.class);
                startActivity(testTwoIntent);
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTheAnswers();
                finishButton.setVisibility(View.INVISIBLE);
                quitButton.setText("Back");
            }
        });

        chooseQuestionsFunc();
    }
    private void checkTheAnswers()
    {
        int points = 0;

        if ((answerOne.getText().toString().toLowerCase().equals(correctAnswers[chosenQuestions[0]].toLowerCase()))
        || (answerOne.getText().toString().toLowerCase().equals(altCorrectAnswers[chosenQuestions[0]].toLowerCase())))
        {
            points = points + 1;
            answerOne.setTextColor(Color.parseColor("#19FA19"));
        }else {
            answerOne.setTextColor(Color.parseColor("#ff0000"));
            if (answerFive.getText().toString().equals(""))
            {
                String pom = answerOne.getText().toString();
                pom = pom + " (" + questions[chosenQuestions[0]] + ")";
                answerOne.setText(pom);
                answerOne.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        if ((answerTwo.getText().toString().toLowerCase().equals(correctAnswers[chosenQuestions[1]].toLowerCase()))
                || (answerOne.getText().toString().toLowerCase().equals(altCorrectAnswers[chosenQuestions[1]].toLowerCase())))
        {
            points = points + 1;
            answerTwo.setTextColor(Color.parseColor("#19FA19"));
        }else {
            answerTwo.setTextColor(Color.parseColor("#ff0000"));
            if (answerFive.getText().toString().equals(""))
            {
                String pom = answerTwo.getText().toString();
                pom = pom + " (" + questions[chosenQuestions[1]] + ")";
                answerTwo.setText(pom);
                answerTwo.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        if ((answerThree.getText().toString().toLowerCase().equals(correctAnswers[chosenQuestions[2]].toLowerCase()))
                || (answerOne.getText().toString().toLowerCase().equals(altCorrectAnswers[chosenQuestions[2]].toLowerCase())))
        {
            points = points + 1;
            answerThree.setTextColor(Color.parseColor("#19FA19"));
        }else {
            answerThree.setTextColor(Color.parseColor("#ff0000"));
            if (answerFive.getText().toString().equals(""))
            {
                String pom = answerThree.getText().toString();
                pom = pom + " (" + questions[chosenQuestions[2]] + ")";
                answerThree.setText(pom);
                answerThree.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        if ((answerFour.getText().toString().toLowerCase().equals(correctAnswers[chosenQuestions[3]].toLowerCase()))
                || (answerOne.getText().toString().toLowerCase().equals(altCorrectAnswers[chosenQuestions[3]].toLowerCase())))
        {
            points = points + 1;
            answerFour.setTextColor(Color.parseColor("#19FA19"));
        }else {
            answerFour.setTextColor(Color.parseColor("#ff0000"));
            if (answerFive.getText().toString().equals(""))
            {
                String pom = answerFour.getText().toString();
                pom = pom + " (" + questions[chosenQuestions[3]] + ")";
                answerFour.setText(pom);
                answerFour.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        if ((answerFive.getText().toString().toLowerCase().equals(correctAnswers[chosenQuestions[4]].toLowerCase()))
                || (answerOne.getText().toString().toLowerCase().equals(altCorrectAnswers[chosenQuestions[4]].toLowerCase())))
        {
            points = points + 1;
            answerFive.setTextColor(Color.parseColor("#19FA19"));
        }else {
            answerFive.setTextColor(Color.parseColor("#ff0000"));
            if (answerFive.getText().toString().equals(""))
            {
                String pom = answerFive.getText().toString();
                pom = pom + " (" + questions[chosenQuestions[4]] + ")";
                answerFive.setText(pom);
                answerFive.setTextColor(Color.parseColor("#ff0000"));
            }
        }
        if (points == 5) {
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            int Score = PreferenceManager.getDefaultSharedPreferences(this).getInt("Score", 0);
            Score = Score + 100;
            PreferenceManager.getDefaultSharedPreferences(TestOneActivity.this).edit().putInt("Score", Score).apply();
        }
        String scoreString = score.getText().toString();
        scoreString = scoreString + "5/";
        scoreString = scoreString + Integer.toString(points);
        score.setText(scoreString);
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
            Log.e("Number " , Integer.toString(chosenQuestions[y]));
        }
        qOne.setText(questions[chosenQuestions[0]]);
        qTwo.setText(questions[chosenQuestions[1]]);
        qThree.setText(questions[chosenQuestions[2]]);
        qFour.setText(questions[chosenQuestions[3]]);
        qFive.setText(questions[chosenQuestions[4]]);

    }

}
