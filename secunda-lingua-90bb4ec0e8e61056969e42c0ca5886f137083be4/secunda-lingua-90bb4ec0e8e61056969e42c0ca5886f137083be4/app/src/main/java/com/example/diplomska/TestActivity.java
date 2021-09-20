package com.example.diplomska;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class TestActivity extends AppCompatActivity {

    TextView qOne, qTwo, qThree, qFour, qFive;
    EditText aOne, aTwo, aThree, aFour, aFive;
    Button quitButton, finishButton;
    int[] chosenQuestions = {1,2,3,4,5,6};
    String[] questions = {"Whats your name" , "How old are you", "My name is", "I want a glass of water", "I want candy", "How are you", "Im fine", "My head hurts", "Your shirt is nice", "The food is tasty "};
    String[] correctAnswers = {"Kako se vikas" ,"Kolku godini imas", "moeto ime e", "Sakam casa voda", "Sakam blago","Kako si", "Dobro sum", "Me boli glavata", "Tvojata maicka e ubava", "Hranata e vkusna"};
    String[] altCorrectAnswers = {"Kako e tvoeto ime" ,"Kolku si star", "Jas se vikam", "Jas sakam casa voda", "Jas sakam blago","Kako si","Jas sum dobro", "Mene me boli glavata", "Ubava ti e maickata", "Jadenjeto e fkusno"};
    TextView score;
    String isReversed;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Intent intent = getIntent();
        isReversed = intent.getStringExtra("reverse");

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
        if (isReversed.equals("NO")) {
            chooseQuestionsFunc();
        }
        else {
            chooseReversedQuestionsFunc();
        }
    }

    private void checkTheAnswers()
    {
        int points = 0;

        if ((aOne.getText().toString().toLowerCase().trim().equals(correctAnswers[chosenQuestions[0]].toLowerCase().trim()))
                || (aOne.getText().toString().toLowerCase().trim().equals(altCorrectAnswers[chosenQuestions[0]].toLowerCase().trim())))
        {
            points = points + 1;
            aOne.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aOne.getText().toString();
            pom = pom + " (" + correctAnswers[chosenQuestions[0]] + "/" + altCorrectAnswers[chosenQuestions[0]] + ")";
            aOne.setText(pom);
            aOne.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aTwo.getText().toString().toLowerCase().trim().equals(correctAnswers[chosenQuestions[1]].toLowerCase().trim()))
                || (aOne.getText().toString().toLowerCase().trim().equals(altCorrectAnswers[chosenQuestions[1]].toLowerCase().trim())))
        {
            points = points + 1;
            aTwo.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aTwo.getText().toString();
            pom = pom + " (" +correctAnswers[chosenQuestions[1]] +  "/" + altCorrectAnswers[chosenQuestions[1]] +")";
            aTwo.setText(pom);
            aTwo.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aThree.getText().toString().toLowerCase().trim().equals(correctAnswers[chosenQuestions[2]].toLowerCase().trim()))
                || (aOne.getText().toString().toLowerCase().trim().equals(altCorrectAnswers[chosenQuestions[2]].toLowerCase().trim())))
        {
            points = points + 1;
            aThree.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aThree.getText().toString();
            pom = pom + "( " +correctAnswers[chosenQuestions[2]] +  "/" + altCorrectAnswers[chosenQuestions[2]] +")";
            aThree.setText(pom);
            aThree.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aFour.getText().toString().toLowerCase().trim().equals(correctAnswers[chosenQuestions[3]].toLowerCase().trim()))
                || (aOne.getText().toString().toLowerCase().trim().equals(altCorrectAnswers[chosenQuestions[3]].toLowerCase().trim())))
        {
            points = points + 1;
            aFour.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aFour.getText().toString();
            pom = pom + " (" +correctAnswers[chosenQuestions[3]] +  "/" + altCorrectAnswers[chosenQuestions[3]] +")";
            aFour.setText(pom);
            aFour.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aFive.getText().toString().toLowerCase().trim().equals(correctAnswers[chosenQuestions[4]].toLowerCase().trim()))
                || (aOne.getText().toString().toLowerCase().trim().equals(altCorrectAnswers[chosenQuestions[4]].toLowerCase().trim())))
        {
            points = points + 1;
            aFive.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aFive.getText().toString();
            pom = pom + " (" +correctAnswers[chosenQuestions[4]] +  "/" + altCorrectAnswers[chosenQuestions[4]] +")";
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
            Log.e("Number " , Integer.toString(chosenQuestions[y]));
        }
        qOne.setText(questions[chosenQuestions[0]]);
        qTwo.setText(questions[chosenQuestions[1]]);
        qThree.setText(questions[chosenQuestions[2]]);
        qFour.setText(questions[chosenQuestions[3]]);
        qFive.setText(questions[chosenQuestions[4]]);

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
        qOne.setText(correctAnswers[chosenQuestions[0]]);
        qTwo.setText(correctAnswers[chosenQuestions[1]]);
        qThree.setText(correctAnswers[chosenQuestions[2]]);
        qFour.setText(correctAnswers[chosenQuestions[3]]);
        qFive.setText(correctAnswers[chosenQuestions[4]]);
    }

    private void checkReversedAnswers()
    {
        int points = 0;

        if ((aOne.getText().toString().toLowerCase().equals(questions[chosenQuestions[0]].toLowerCase())))
        {
            points = points + 1;
            aOne.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aOne.getText().toString();
            pom = pom + " (" + questions[chosenQuestions[0]] + ")";
            aOne.setText(pom);
            aOne.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aTwo.getText().toString().toLowerCase().equals(questions[chosenQuestions[1]].toLowerCase())))
        {
            points = points + 1;
            aTwo.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aTwo.getText().toString();
            pom = pom + " (" + questions[chosenQuestions[1]] + ")";
            aTwo.setText(pom);
            aTwo.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aThree.getText().toString().toLowerCase().equals(correctAnswers[chosenQuestions[2]].toLowerCase())))
        {
            points = points + 1;
            aThree.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aThree.getText().toString();
            pom = pom + "( " + questions[chosenQuestions[2]] + ")";
            aThree.setText(pom);
            aThree.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aFour.getText().toString().toLowerCase().equals(questions[chosenQuestions[3]].toLowerCase())))
        {
            points = points + 1;
            aFour.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aFour.getText().toString();
            pom = pom + " (" + questions[chosenQuestions[3]] + ")";
            aFour.setText(pom);
            aFour.setTextColor(Color.parseColor("#ff0000"));
        }
        if ((aFive.getText().toString().toLowerCase().equals(questions[chosenQuestions[4]].toLowerCase())))
        {
            points = points + 1;
            aFive.setTextColor(Color.parseColor("#19FA19"));
        }else {
            String pom = aFive.getText().toString();
            pom = pom + " (" + questions[chosenQuestions[4]] + ")";
            aFive.setText(pom);
            aFive.setTextColor(Color.parseColor("#ff0000"));
        }
        String pomString = "Score: " + Integer.toString(points) + "/5";
        score.setText(pomString);
    }

}
