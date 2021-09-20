package com.example.diplomska;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import static com.example.diplomska.Achievements.getAchievement;
import static com.example.diplomska.Achievements.getAchievementDesc;
import static com.example.diplomska.Achievements.getAchievementImage;

public class AchievementsActivity extends AppCompatActivity {

    ListView listView;
    String mTitle[];
    String mDescription[];
    int images[];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        mTitle = getAchievement();
        mDescription = getAchievementDesc();
        images = getAchievementImage();


        listView = findViewById(R.id.achievementsListView);
        Button backButton = findViewById(R.id.achievementsBackButton);
        MyAdapter adapter = new MyAdapter(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(AchievementsActivity.this, MainActivity.class);
                startActivity(backIntent);
            }
        });

    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.achievementRowTitle, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImgs = imgs;

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            int Score = PreferenceManager.getDefaultSharedPreferences(AchievementsActivity.this).getInt("Score", 0);
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.achievementRowImage);
            TextView myTitle = row.findViewById(R.id.achievementRowTitle);
            TextView myDescription = row.findViewById(R.id.achievementRowSubtitle);

            if ((position + 1) * 300 <= Score)
            {
                images.setImageResource(rImgs[position + 1]);
                myTitle.setText(rTitle[position]);
                myDescription.setText(rDescription[position]);
            }
            else
            {
                images.setImageResource(rImgs[0]);
                myTitle.setText("Locked");
                myDescription.setText("");
            }
            return row;
        }
    }
}
