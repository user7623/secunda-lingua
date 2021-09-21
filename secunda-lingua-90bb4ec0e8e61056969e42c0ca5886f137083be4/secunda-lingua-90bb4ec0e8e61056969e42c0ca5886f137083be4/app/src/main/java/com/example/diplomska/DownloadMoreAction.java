package com.example.diplomska;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import  com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import  com.android.volley.toolbox.Volley;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.diplomska.translations.getAlternativeTranslationsarrayGlobal;
import static com.example.diplomska.translations.getSentencesArrayGlobal;
import static com.example.diplomska.translations.getTranslationsArrayGlobal;

import static com.example.diplomska.translations.setAlternativeTranslationsarrayGlobal;
import static com.example.diplomska.translations.setSentencesArrayGlobal;
import static com.example.diplomska.translations.setTranslationsArrayGlobal;

public class DownloadMoreAction extends AppCompatActivity {

    Button downloadButton;
    Button backButton;

    ArrayList<String> translationsList = new ArrayList<>();
    ArrayList<String> sentencesList = new ArrayList<>();
    ArrayList<String> altTranslationsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_more);

        downloadButton = (Button) findViewById(R.id.downloadButton);
        backButton = (Button) findViewById(R.id.downloadMoreBackButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadMoreContent();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backFromDownloadIntent = new Intent(DownloadMoreAction.this, MainActivity.class);
                startActivity(backFromDownloadIntent);
            }
        });
    }

    private void downloadMoreContent() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("sentences");
        query.findInBackground((objects, e) -> {

            if (e == null) {
                try{
                    for(ParseObject sentence : objects){
                        sentencesList.add(sentence.getString("sentence"));
                    }
                    for(ParseObject translation : objects){
                        translationsList.add(translation.getString("translation"));
                    }
                    for(ParseObject alt_translation : objects){
                        altTranslationsList.add(alt_translation.getString("alternative_translation"));
                    }
                    addToGlobalVars();
                }catch (Exception exception){
                    Log.e("exception", exception.getLocalizedMessage());
                }
            } else {
                Log.e("Error", e.getMessage());
            }
        });
    }

    private void addToGlobalVars()
    {
        //we were using lists until now because they are easier to work with
        //but setters for global vars work with arrays
        // so we need to convert them accordingly

        String[] newTranslationsArray = translationsList.toArray(new String[0]);
        String[] newSentencesArray = sentencesList.toArray(new String[0]);
        String[] newAltTranslationsArray = altTranslationsList.toArray(new String[0]);

        setTranslationsArrayGlobal(newTranslationsArray);
        setAlternativeTranslationsarrayGlobal(newAltTranslationsArray);
        setSentencesArrayGlobal(newSentencesArray);

        updateDatabase();
    }

    private void updateDatabase() {
        //function that already updates database,
        // no need for rewriting code
        MainActivity.addItem();
    }
}
