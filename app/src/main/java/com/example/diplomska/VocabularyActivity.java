package com.example.diplomska;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class VocabularyActivity extends AppCompatActivity {

    int currentQuestionNumber = 1;
    int points = 0;
    String hintString = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        //TODO: povik do databaza
        //TODO: inicijalni vrednosti za promenlivite
        Button questionMarkButton = (Button) findViewById(R.id.questionMarkButtonVocabulary);
        Button submitButton = (Button) findViewById(R.id.vocabularySubmitButton);
        Button quitButton = (Button) findViewById(R.id.quitButtonOnVocabulary);
        TextView resultTextView = (TextView) findViewById(R.id.correctIncorrectTextViewVocabulary);
        ImageView theImageView = (ImageView) findViewById(R.id.vocabularyPicture);

        questionMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(VocabularyActivity.this, hintString, Toast.LENGTH_SHORT).show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: kod za proverka na vnesewniot odgovor
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quitVocabularyIntent = new Intent(VocabularyActivity.this, MainActivity.class);
                startActivity(quitVocabularyIntent);
            }
        });

    }
}
