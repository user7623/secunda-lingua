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

import java.util.List;

public class DownloadMoreAction extends AppCompatActivity {

    Button downloadButton;
    Button backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_more);

    /*
    * Intent downloadIntent = new Intent(MainActivity.this, DownloadMoreAction.class);
                startActivity(downloadIntent);
    * */

        downloadButton = (Button) findViewById(R.id.downloadButton);
        backButton = (Button) findViewById(R.id.downloadMoreBackButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Download button pressed", "---------------------------------");
                //TODO: dodadi kod za komunikacija so REST server i za zapisuvanje na podatocite dobieni

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
        query.orderByDescending("createdAt");
        query.findInBackground((objects, e) -> {

            if (e == null) {
                objects.get(1).getJSONArray(1);
                for(ParseObject sentence : objects){

                      Log.e("sentence", sentence.getString("sentence"));
                }
                for(ParseObject translation : objects){
                    Log.e("sentence", translation.getString("translation"));
                }
            } else {
               Log.e("Error", e.getMessage());
            }
        });
    }

    private void addToDatabase(String newData)
    {

    }

}
