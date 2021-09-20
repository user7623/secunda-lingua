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
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                       Log.e("Response is: ", response.substring(0,500));
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR 500","No response from server");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void addToDatabase(String newData)
    {

    }

}
