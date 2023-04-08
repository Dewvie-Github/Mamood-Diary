package com.example.mamooddiary;

import android.annotation.SuppressLint;
import android.content.Intent;
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
    ImageButton moodButton,moodButton1,moodButton2,moodButton3,moodButton4;

    String mood;

    EditText message;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_activity_diary);

        intent = getIntent();

        day = intent.getIntExtra("day", -1);
        month = intent.getIntExtra("month", -1);
        year = intent.getIntExtra("year", -1);

        diaryBackButton = findViewById(R.id.diaryBackButton);
        diarySaveButton = findViewById(R.id.diarySaveButton);
        diaryBackButton.setOnClickListener(this);
        diarySaveButton.setOnClickListener(this);

        message = findViewById(R.id.message);

        moodButton = findViewById(R.id.imageButton);
        moodButton1 = findViewById(R.id.imageButton2);
        moodButton2 = findViewById(R.id.imageButton3);
        moodButton3 = findViewById(R.id.imageButton4);
        moodButton4 = findViewById(R.id.imageButton5);

        //database
        dbh =new DBHelper(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.diaryBackButton:
                finish();
                break;
            case R.id.diarySaveButton:
                boolean IsSucceed = dbh.AddData(
                        message.getText().toString(),
                        String.valueOf( day ),
                        String.valueOf( month ),
                        String.valueOf( year ),
                        mood);
                if (IsSucceed){
                    Toast.makeText(this, "Data Saved in " + day + "-" + month + "-" + year + "mood:" + mood, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Data Can't Save ", Toast.LENGTH_SHORT).show();
                }
                finish();
                break;
        }
    }
    public String checkMood(View v){

        switch (v.getId()){
            case R.id.imageButton:
                mood = "happy";
                break;
            case R.id.imageButton2:
                mood = "smile";
                break;
            case R.id.imageButton3:
                mood = "normal";
                break;
            case R.id.imageButton4:
                mood = "sad";
                break;
            case R.id.imageButton5:
                mood = "notok";
                break;
        }
        return mood;
    }

}
