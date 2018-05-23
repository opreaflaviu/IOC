package com.example.flaviuoprea.creativity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;

public class CreativityActivity extends AppCompatActivity {

    private static final Integer REQUEST_CAMERA = 1;
    private AppCompatButton mPlayVideoButton;
    private AppCompatButton mStartButton;
    private AppCompatTextView mTimer;

    private long timeLeftInMilliseconds = 6000;
    private boolean mTimerRunning;
    CountDownTimer countDownTimer;
    private String videoUrl = "";
    private int soundId;
    private MediaPlayer sound_player;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creativity);
        videoUrl = getIntent().getStringExtra("video_url");
        soundId = getIntent().getIntExtra("sound_id", 0);


        mTimer = findViewById(R.id.timer);
        mPlayVideoButton = findViewById(R.id.play_video);
        mPlayVideoButton.setOnClickListener(v -> startNewActivty(videoUrl));

        mStartButton = findViewById(R.id.start);
        mStartButton.setOnClickListener(v -> startStop());
        updateTimer();

        playSound(R.raw.bine_ai_venit_sound);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sound_player.stop();
    }

    private void playSound(int soundId) {
        sound_player = MediaPlayer.create(this, soundId);
        sound_player.start();
    }

    private void startStop(){
        sound_player.stop();
        if (mTimerRunning){
            stopTimer();
            mStartButton.setText("Start");
        } else {
            startTimer();
            mStartButton.setText("Pauza");
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMilliseconds = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                checkPermissionCamera();
            }
        }.start();
        mTimerRunning = true;
    }

    private void updateTimer() {
        int minutes = (int) timeLeftInMilliseconds / 60000;
        int seconds = (int) timeLeftInMilliseconds % 60000 / 1000;

        String timeLeftText = "";
        timeLeftText += minutes + ":";
        if (seconds < 10){
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        String text = "Timp ramas: " + timeLeftText;
        mTimer.setText(text);
    }

    private void stopTimer() {
        countDownTimer.cancel();
        mTimerRunning = false;
    }

    private void startNewActivty(String video_url){
        Intent intent = new Intent(getApplicationContext(), YoutubeActivity.class);
        intent.putExtra("video_url", video_url);
        startActivity(intent);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void checkPermissionCamera(){
        int permissionCheck = ContextCompat.checkSelfPermission(CreativityActivity.this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    CreativityActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            openCamera();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == REQUEST_CAMERA){
                playSound(soundId);
            }

        }
    }
}
