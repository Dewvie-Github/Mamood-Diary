package com.example.mamooddiary;

import android.content.Context;
import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnStart;
    MediaPlayer mediaPlayer;

    @Override
    protected void  onCreate(Bundle savedInstanceState) {;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(this, R.raw.click);

        closeActionBar();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnStart) {
            mediaPlayer.start();
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    public void closeActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}
