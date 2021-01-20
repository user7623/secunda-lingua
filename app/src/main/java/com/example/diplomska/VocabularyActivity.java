package com.example.diplomska;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.diplomska.translations.getImagesIdsGlobal;

public class VocabularyActivity extends AppCompatActivity {
    private int questionNumber = 0;
    String[] imagesList;
    int currentQuestionNumber = 1;
    int points = 0;
    String hintString = "";
    ImageView theImageView;
    TextView resultTextView;
    Button quitButton;
    Button submitButton;
    Button questionMarkButton;
    EditText answer;
    SeekBar seekBar;
    int seekBarProgress = 0;
    boolean correctAnswerFlag = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        //TODO: povik do databaza
        //TODO: inicijalni vrednosti za promenlivite
        questionMarkButton = (Button) findViewById(R.id.questionMarkButtonVocabulary);
        submitButton = (Button) findViewById(R.id.vocabularySubmitButton);
        quitButton = (Button) findViewById(R.id.quitButtonOnVocabulary);
        resultTextView = (TextView) findViewById(R.id.correctIncorrectTextViewVocabulary);
        theImageView = (ImageView) findViewById(R.id.vocabularyPicture);
        answer = (EditText) findViewById(R.id.vocabularyEditTextAnswer);
        imagesList = getImagesIdsGlobal();
        seekBar = (SeekBar) findViewById(R.id.seekBarVocabulary);

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
        questionMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hintString = imagesList[questionNumber];
                Toast.makeText(VocabularyActivity.this, hintString, Toast.LENGTH_SHORT).show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("info: ", "entered onClick");
                checkAnswerFunction();
            }
        });
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quitVocabularyIntent = new Intent(VocabularyActivity.this, MainActivity.class);
                startActivity(quitVocabularyIntent);
            }
        });
        giveQuestionFunc();
    }

    public void giveQuestionFunc() {
        Log.d("info:", "setting question, question number is" + questionNumber);
        Log.e("images names ", imagesList[questionNumber] + imagesList[questionNumber + 1] + imagesList[questionNumber + 2]);

        String imageName = imagesList[questionNumber];
        String imageLocation = "drawable/" + imageName;
        int id = getResources().getIdentifier(imageLocation, "id", "com.example.diplomska");
        theImageView.setImageResource(id);
    }

    private void nextQuestionFunc() {
        questionNumber++;
        giveQuestionFunc();
        //questionText.setText(questionsList.get(questionNumber));

    }

    public void checkAnswerFunction() {
        Log.d("info: ", "entered checkAnswerFunction");

        //TODO: kod za proverka na odgovorot

        String answerGiven = answer.getText().toString();
        String answerRequired = imagesList[questionNumber];

        Log.d("info: ", answerGiven + answerRequired);
        if (answerGiven.toLowerCase().equals(answerRequired.toLowerCase())) {
            seekBarProgress = seekBarProgress + 10;
            seekBar.setProgress(seekBarProgress);
            correctAnswerFlag = true;
            showCorrectToast();
        } else {
            if (seekBarProgress >= 10)
            {
                seekBarProgress = seekBarProgress - 10;
            }else{
                seekBarProgress = 0;
            }
            seekBar.setProgress(seekBarProgress);
            correctAnswerFlag = false;
            showWrongToast();
        }
        new CountDownTimer(3500, 1000) {
            public void onFinish() {
                Log.e("next question:" , ".............................................");
                nextQuestionFunc();
            }
            public void onTick(long millisUntilFinished) {

            }
        }.start();
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
    private void endCourseFunc()
    {
        Log.d("info", "ending translate activity --------------------------------------");
        Intent endTranslateActivity = new Intent(VocabularyActivity.this, MainActivity.class);
        startActivity(endTranslateActivity);
    }

}
