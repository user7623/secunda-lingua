package com.example.diplomska;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    private void addToDatabase(String newData)
    {

    }

}
