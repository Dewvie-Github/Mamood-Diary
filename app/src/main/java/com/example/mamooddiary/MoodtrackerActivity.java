package com.example.mamooddiary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MoodtrackerActivity extends AppCompatActivity {
    protected void  onCreate(Bundle savedInstanceState) {
        ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodtracker);

        closeActionBar();
    }

    public void closeActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}


