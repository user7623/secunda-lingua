package com.example.diplomska;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class TranslateActivity extends AppCompatActivity {

    String hintString = "";
    private static Timer timer;
    private static TimerTask timerTask;
    long oldTime = 0;
    private int counter = 0;
    boolean correctAnswerFlag = true;
    boolean hideResultTextView = false;
    private int questionNumber = 0;
    private int numberOfQuestions = 0;
    private String[] questionsArray;
    private String[] answersArray;
    private String[] alternativeAnswersArray;

    Button questionMarkButton;
    Button submitButton;
    Button quitButton;
    EditText answerText;
    TextView questionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);

        questionMarkButton = (Button) findViewById(R.id.questionMarkButtonTranslate);
        submitButton = (Button) findViewById(R.id.translateSubmitButton);
        quitButton = (Button) findViewById(R.id.quitButtonOnTranslate);
        answerText = (EditText) findViewById(R.id.translateEditText);
        questionText = (TextView) findViewById(R.id.translateTextView);

        //popolni gi nizite za prasanja i prevod
        readFromDb();

        giveQuestionFunc();

        questionMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TranslateActivity.this, hintString, Toast.LENGTH_LONG).show();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("info: " , "entered onClick");
                checkAnswerFunction();

               //TODO: kod za proverka na vnesewniot odgovor
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
        String answerRequired = answersArray[questionNumber];
        String alAnswerRequired = alternativeAnswersArray[questionNumber];
        Log.d("info: " , answerGiven + answerRequired + alAnswerRequired);
        if (answerText.getText().toString().toLowerCase().equals(answersArray[questionNumber].toLowerCase()))
        {
            correctAnswerFlag = true;
        }
        else if (answerText.getText().toString().toLowerCase().equals(alternativeAnswersArray[questionNumber].toLowerCase()))
        {
            correctAnswerFlag = true;
        }else{
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
        questionText.setText(questionsArray[questionNumber]);
        //questionNumber++;
    }

    public void readFromDb()
    {
        //TODO: napravi da cita od db !!!
        numberOfQuestions = 2;
        questionsArray = new String[numberOfQuestions];
        answersArray = new String[numberOfQuestions];
        alternativeAnswersArray = new String[numberOfQuestions];

        questionsArray[0] = "Hello world";
        questionsArray[1] = "My name is";
        answersArray[0] = "Zdravo svetu";
        answersArray[1] = "Moeto ime e";
        alternativeAnswersArray[0] = "zdravo svetu";
        alternativeAnswersArray[1] = "Jas se vikam";

    }

    private void nextQuestionFunc()
    {
        questionNumber++;
        questionText.setText(questionsArray[questionNumber]);

    }

    private void endCourseFunc()
    {
        Log.d("info", "ending translate activity --------------------------------------");
        Intent endTranslateActivity = new Intent(TranslateActivity.this, MainActivity.class);
        startActivity(endTranslateActivity);
    }

}