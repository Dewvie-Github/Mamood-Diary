package com.example.mamooddiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;


public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper dbh;
    private int day, month, year;
    TextView diaryBackButton, diarySaveButton;
    ImageView moodButton5,moodButton1,moodButton2,moodButton3,moodButton4;

    String mood;

    EditText message;

    Intent intent;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        intent = getIntent();

        day = intent.getIntExtra("day", -1);
        month = intent.getIntExtra("month", -1);
        year = intent.getIntExtra("year", -1);

        diaryBackButton = findViewById(R.id.diaryBackButton);
        diarySaveButton = findViewById(R.id.diarySaveButton);
        diaryBackButton.setOnClickListener(this);
        diarySaveButton.setOnClickListener(this);

        message = findViewById(R.id.diaryEditText);

        moodButton1 = findViewById(R.id.happyImageView);
        moodButton2 = findViewById(R.id.smileImageView);
        moodButton3 = findViewById(R.id.normalImageView);
        moodButton4 = findViewById(R.id.notOkImageView);
        moodButton5 = findViewById(R.id.sadImageView);
        moodButton1.setOnClickListener(this);
        moodButton2.setOnClickListener(this);
        moodButton3.setOnClickListener(this);
        moodButton4.setOnClickListener(this);
        moodButton5.setOnClickListener(this);

        //database
        dbh = new DBHelper(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.click);

        closeActionBar();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        checkMood(v.getId());
        switch (v.getId()){
            case R.id.diaryBackButton:
                finish();
                break;
            case R.id.diarySaveButton:
                boolean IsSucceed = dbh.AddData(
                        String.valueOf( day ),
                        String.valueOf( month ),
                        String.valueOf( year ),
                        message.getText().toString(),
                        mood);
                if (IsSucceed){
                    mediaPlayer.start();
                    Toast.makeText(this, "Data Saved in " + day + "-" + month + "-" + year + "mood:" + mood, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Data Can't Save ", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
        }
    }
    public String checkMood(int id){
        switch (id){
            case R.id.happyImageView:
                mood = "happy";
                break;
            case R.id.smileImageView:
                mood = "smile";
                break;
            case R.id.normalImageView:
                mood = "normal";
                break;
            case R.id.sadImageView:
                mood = "sad";
                break;
            case R.id.notOkImageView:
                mood = "notok";
                break;
        }
        return mood;
    }

    public void closeActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}
