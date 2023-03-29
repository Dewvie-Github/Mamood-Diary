package com.example.mamooddiary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_activity_diary);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.diaryBackButton:
                finish();
                break;
            case R.id.diarySaveButton:
                safeDataOnDatabase();
                finish();
                Toast.makeText(this, "Data Saved!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void safeDataOnDatabase(){

    }
}
