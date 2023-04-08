package com.example.mamooddiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;


public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {
    DBHelper dbh;
    private int day, month, year;
    TextView diaryBackButton, diarySaveButton;
    ImageButton moodButton5,moodButton1,moodButton2,moodButton3,moodButton4;

    String mood;

    EditText message;

    Intent intent;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        init();
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
            case R.id.happyImageButton:
                mood = "happy";
                unselectedButton();
                moodButton1.setSelected(true);
                break;
            case R.id.smileImageButton:
                mood = "smile";
                unselectedButton();
                moodButton2.setSelected(true);
                break;
            case R.id.normalImageButton:
                mood = "normal";
                unselectedButton();
                moodButton3.setSelected(true);
                break;
            case R.id.notOkImageButton:
                mood = "notok";
                unselectedButton();
                moodButton4.setSelected(true);
                break;
            case R.id.sadImageButton:
                mood = "sad";
                unselectedButton();
                moodButton5.setSelected(true);
                break;
        }
        return mood;
    }

    public void unselectedButton(){
        moodButton1.setSelected(false);
        moodButton2.setSelected(false);
        moodButton3.setSelected(false);
        moodButton4.setSelected(false);
        moodButton5.setSelected(false);
    }

    public void closeActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void init() {
        intent = getIntent();
        day = intent.getIntExtra("day", -1);
        month = intent.getIntExtra("month", -1);
        year = intent.getIntExtra("year", -1);

        initUI();
        initDatabase();
        initMediaPlayer();
    }

    private void initUI() {
        diaryBackButton = findViewById(R.id.diaryBackButton);
        diarySaveButton = findViewById(R.id.diarySaveButton);
        diaryBackButton.setOnClickListener(this);
        diarySaveButton.setOnClickListener(this);

        message = findViewById(R.id.diaryEditText);

        moodButton1 = findViewById(R.id.happyImageButton);
        moodButton2 = findViewById(R.id.smileImageButton);
        moodButton3 = findViewById(R.id.normalImageButton);
        moodButton4 = findViewById(R.id.notOkImageButton);
        moodButton5 = findViewById(R.id.sadImageButton);
        moodButton1.setOnClickListener(this);
        moodButton2.setOnClickListener(this);
        moodButton3.setOnClickListener(this);
        moodButton4.setOnClickListener(this);
        moodButton5.setOnClickListener(this);
    }

    private void initDatabase() {
        dbh = new DBHelper(this);
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mediaPlayer != null)
                    mediaPlayer.release();
            }
        });
    }


}
