package com.example.mamooddiary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;


public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {
    private int day, month, year;
    TextView diaryBackButton, diarySaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_activity_diary);

        // Default value if day not parse is today
        LocalDate dateNow = LocalDate.now();
        day = getIntent().getIntExtra("day", LocalDate.now().getDayOfMonth());
        month = getIntent().getIntExtra("month", LocalDate.now().getMonthValue());
        year = getIntent().getIntExtra("year", LocalDate.now().getYear());

        diaryBackButton = findViewById(R.id.diaryBackButton);
        diarySaveButton = findViewById(R.id.diarySaveButton);
        diaryBackButton.setOnClickListener(this);
        diarySaveButton.setOnClickListener(this);
        System.out.println(day + "-" + month + "-" + year);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.diaryBackButton:
                finish();
                break;
            case R.id.diarySaveButton:
                saveDataOnDatabase();
                finish();
                break;
        }
    }

    private void saveDataOnDatabase(){

        Toast.makeText(this, "Data Saved in " + day + "-" + month + "-" + year
                , Toast.LENGTH_SHORT).show();
    }
}
