package com.example.diplomska;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
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
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static com.example.diplomska.translations.getImagesIdsGlobal;

public class VocabularyActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int sound1;
    private int questionNumber = 0;
    String[] imagesList;
    String hintString = "";
    ImageView theImageView;
    //TextView resultTextView;
    Button quitButton;
    Button submitButton;
    Button questionMarkButton;
    EditText answer;
    SeekBar seekBar;
    int seekBarProgress = 0;
    boolean correctAnswerFlag = true;
    private TextToSpeech tts;
    String stringForPronouncing;
    ImageView listenAgainButton;
    ArrayList<Integer> chosenPictures;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        //TODO: povik do databaza
        //TODO: inicijalni vrednosti za promenlivite
        questionMarkButton = (Button) findViewById(R.id.questionMarkButtonVocabulary);
        submitButton = (Button) findViewById(R.id.vocabularySubmitButton);
        quitButton = (Button) findViewById(R.id.quitButtonOnVocabulary);
       // resultTextView = (TextView) findViewById(R.id.correctIncorrectTextViewVocabulary);
        theImageView = (ImageView) findViewById(R.id.vocabularyPicture);
        answer = (EditText) findViewById(R.id.vocabularyEditTextAnswer);
        imagesList = getImagesIdsGlobal();
        seekBar = (SeekBar) findViewById(R.id.seekBarVocabulary);
        listenAgainButton = (ImageView) findViewById(R.id.listenAgainButtonVocabulary);
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
                hintString = imagesList[chosenPictures.get(questionNumber)];
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
                endCourseFunc();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }

        sound1 = soundPool.load(this, R.raw.sound1, 1);
        tts = new TextToSpeech(VocabularyActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                // TODO Auto-generated method stub
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        ConvertTextToSpeech();
                    }
                } else
                    Log.e("error", "Initialization of TextToSpeech Failed!");
            }
        });
        listenAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConvertTextToSpeech();
            }
        });
        chosenPictures = new ArrayList<>(9);
        for (int i = 0; i <= 8; i++){
            chosenPictures.add(i);
        }
        Collections.shuffle(chosenPictures);
        giveQuestionFunc();
    }

    public void giveQuestionFunc() {

        Log.d("info:", "setting question, question number is" + questionNumber);
        //Log.e("images names ", imagesList[questionNumber] + imagesList[questionNumber + 1] + imagesList[questionNumber + 2]);
        //se korsiti listata za da bide slucajno izbrano prasanjeto
        String imageName = imagesList[chosenPictures.get(questionNumber)];
        String imageLocation = "drawable/" + imageName;
        int id = getResources().getIdentifier(imageLocation, "id", "com.example.diplomska");
        theImageView.setImageResource(id);
        stringForPronouncing = imageName;
        ConvertTextToSpeech();
    }

    private void nextQuestionFunc() {
        questionNumber++;
        giveQuestionFunc();
        answer.setText("");
        //questionText.setText(questionsList.get(questionNumber));
    }

    public void checkAnswerFunction() {
        Log.d("info: ", "entered checkAnswerFunction");

        //TODO: kod za proverka na odgovorot

        String answerGiven = answer.getText().toString();
        String answerRequired = imagesList[chosenPictures.get(questionNumber)];

        Log.d("info: ", answerGiven + answerRequired);
        if (answerGiven.toLowerCase().equals(answerRequired.toLowerCase())) {
            seekBarProgress = seekBarProgress + 20;
            seekBar.setProgress(seekBarProgress);
            correctAnswerFlag = true;
            if (seekBarProgress >= 100)
            {
                endCourseFunc();
            }
            showCorrectToast();
            setSeekBarColor();
        } else {
            if (seekBarProgress >= 20)
            {
                seekBarProgress = seekBarProgress - 20;
            }else{
                seekBarProgress = 0;
            }
            seekBar.setProgress(seekBarProgress);
            correctAnswerFlag = false;
            showWrongToast();
            setSeekBarColor();
        }
        new CountDownTimer(3500, 1000) {
            public void onFinish() {
                Log.e("next question:" , ".............................................");
                if (seekBarProgress < 100) {
                    nextQuestionFunc();
                }
            }
            public void onTick(long millisUntilFinished) {

            }
        }.start();
    }
    private void showCorrectToast()
    {
        soundPool.play(sound1, 1, 1, 0, 0, 1);
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

        if (seekBarProgress >= 100) {
            Log.e("info", "saving points --------------------------------------------------");
            SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            int Score = PreferenceManager.getDefaultSharedPreferences(this).getInt("Score", 0);

            Score = Score + seekBarProgress;
            PreferenceManager.getDefaultSharedPreferences(VocabularyActivity.this).edit().putInt("Score", Score).apply();
        }
        Log.e("info", "ending translate activity --------------------------------------");



        Intent endTranslateActivity = new Intent(VocabularyActivity.this, MainActivity.class);
        stringForPronouncing = "";
        startActivity(endTranslateActivity);
    }

    private void ConvertTextToSpeech () {
        // TODO Auto-generated method stub
        String text = stringForPronouncing;

        if (text == null || "".equals(text)) {
            text = "Content not available";
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        } else
            tts.speak(text , TextToSpeech.QUEUE_FLUSH, null);
    }
    private void setSeekBarColor()
    {
        if (seekBarProgress > 40 && seekBarProgress < 80)
        {
            seekBar.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }
        if (seekBarProgress >= 80)
        {
            seekBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
    }


}
