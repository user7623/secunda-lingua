package com.example.diplomska;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class UnderstandActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int sound1;
    private TextToSpeech tts;
    private SQLiteDatabase mDatabase;
    ImageView imageView;
    Button quitButton;
    SeekBar seekBar;
    EditText answer;
    Button submitAnswer;
    int seekBarProgress = 0;
    int questionNumber = 0;
    String stringForPronouncing;
    int[] chosenQuestions = new int[9];
    int counter = 0;
    ArrayList<String> questionsList = new ArrayList<>();
    boolean savedInstanceStateHelper = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_understand);

        TranslateDBHelper dbHelper = new TranslateDBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();

        imageView = (ImageView) findViewById(R.id.understandImageView);
        quitButton = (Button) findViewById(R.id.quitButtonOnUnderstand);
        seekBar = (SeekBar) findViewById(R.id.seekBarUnderstand);
        answer = (EditText) findViewById(R.id.understandAnswer);
        submitAnswer = (Button) findViewById(R.id.understandSubmitButton);
        quitButton = (Button) findViewById(R.id.quitButtonOnUnderstand);

        if (savedInstanceState != null)
        {
            savedInstanceStateHelper = true;
        }

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

            int id = getResources().getIdentifier("drawable/play_sound_icon", "id", "com.example.diplomska");
            imageView.setImageResource(id);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = getResources().getIdentifier("drawable/sound_playing_icon", "id", "com.example.diplomska");
                    imageView.setImageResource(id);

                    ConvertTextToSpeech();

                    new CountDownTimer(2000, 1000) {
                        public void onFinish() {
                            Log.e("next question:", ".............................................");
                            int id = getResources().getIdentifier("drawable/play_sound_icon", "id", "com.example.diplomska");
                            imageView.setImageResource(id);
                        }

                        public void onTick(long millisUntilFinished) {

                        }
                    }.start();

                }
            });

            quitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent quitUnderstandIntent = new Intent(UnderstandActivity.this, MainActivity.class);
                    startActivity(quitUnderstandIntent);
                }
            });

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

            submitAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswerFunction();
                }
            });

            quitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    endCourseFunc();
                }
            });

        readFromDb();

        tts = new TextToSpeech(UnderstandActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("error", "This Language is not supported");
                    } else {
                        if (!savedInstanceStateHelper)
                        {
                            ConvertTextToSpeech();
                        }
                    }
                } else
                    Log.e("error", "Initialization of TextToSpeech Failed!");
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

        for (int i = 0; i <= 8; i++){
            chosenQuestions[i] = i;
        }
        Random rand = new Random();
        for (int i = 0; i < chosenQuestions.length; i++) {
            int randomIndexToSwap = rand.nextInt(chosenQuestions.length);
            int temp = chosenQuestions[randomIndexToSwap];
            chosenQuestions[randomIndexToSwap] = chosenQuestions[i];
            chosenQuestions[i] = temp;
        }
        Log.e("chosen questions" , "are:::" + chosenQuestions);
        questionNumber = rand.nextInt(chosenQuestions.length);

        if (savedInstanceState != null)
        {
            restoreUIState(savedInstanceState);
        } else {
            giveQuestionFunc();
        }

        }

        public void restoreUIState (Bundle savedInstanceState) {
            answer.setText(savedInstanceState.getString("answerFieldText"));
            stringForPronouncing = savedInstanceState.getString("stringForPronouncing");
            seekBarProgress = savedInstanceState.getInt("seekBarProgress");
        }

        public void checkAnswerFunction () {

            String answered = answer.getText().toString();
            String needed = questionsList.get(questionNumber);

            if (answered.toLowerCase().equals(needed.toLowerCase()))
            {
                showCorrectToast();
                seekBarProgress = seekBarProgress + 20;
                boolean motivation = SettingsActivity.wantsMotivationalMsg;
                if ((seekBarProgress == 40 || seekBarProgress == 80) && motivation)
                {
                    showMotivationalMessage();
                }
            }else{
                showWrongToast();
                if (seekBarProgress >= 20) {
                    seekBarProgress = seekBarProgress - 20;
                }
            }
            seekBar.setProgress(seekBarProgress);
            boolean motivate = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("motivation", false);
            if (seekBarProgress >= 100)
            {
                endCourseFunc();
            }

            setSeekBarColor();
            nextQuestionFunc();
        }
        private void showCorrectToast ()
        {
            soundPool.play(sound1, 1, 1, 0, 0, 1);
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.correct_answer_toast_layout, (ViewGroup) findViewById(R.id.correct_toast_root));
            ImageView toastImage = layout.findViewById(R.id.correct_toast_image);
            toastImage.setImageResource(R.drawable.ic_baseline_emoji_emotions_24);
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        }

        private void showWrongToast ()
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

        private void endCourseFunc ()
        {
            if (seekBarProgress >= 100) {
                SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                int Score = PreferenceManager.getDefaultSharedPreferences(this).getInt("Score", 0);

                Score = Score + seekBarProgress;
                PreferenceManager.getDefaultSharedPreferences(UnderstandActivity.this).edit().putInt("Score", Score).apply();
            }
            Intent endTranslateActivity = new Intent(UnderstandActivity.this, MainActivity.class);
            startActivity(endTranslateActivity);
        }

        private void nextQuestionFunc () {

            questionNumber = chosenQuestions[counter];
            counter++;
            if (seekBarProgress < 100) {
                giveQuestionFunc();
            }
        }

        public void giveQuestionFunc () {
            if (seekBarProgress >= 100)
            {
                endCourseFunc();
                return;
            }
            answer.setText("");
            stringForPronouncing = questionsList.get(questionNumber);
            ConvertTextToSpeech();
        }

        public void readFromDb ()
        {
            Cursor mCursor = mDatabase.query(translateExercise.TranslateEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null);
            mCursor.moveToFirst();

            int count = mCursor.getCount();
            while (!mCursor.isAfterLast()) {
                String sent;
                String translation;
                sent = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_SENTENCE));
                translation = mCursor.getString(mCursor.getColumnIndex(translateExercise.TranslateEntry.COLUMN_TRANSLATION));

                questionsList.add(sent);

                mCursor.moveToNext();
            }
            mCursor.close();
        }

        private void ConvertTextToSpeech () {
            String text = stringForPronouncing;

            if (text == null || "".equals(text)) {
                giveQuestionFunc();
            }
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    private void showMotivationalMessage()
    {
        soundPool.play(sound1, 1, 1, 0, 0, 1);
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.motivation_toast, (ViewGroup) findViewById(R.id.motivation_toast_root));
        ImageView toastImage = layout.findViewById(R.id.correct_toast_image);
        toastImage.setImageResource(R.drawable.ic_motivation);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("stringForPronouncing", stringForPronouncing);
        outState.putString("answerFieldText", answer.getText().toString());
        outState.putInt("seekBarProgress", seekBarProgress);
    }
}


