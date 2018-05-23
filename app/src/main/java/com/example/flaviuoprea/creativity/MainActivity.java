package com.example.flaviuoprea.creativity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton mDeseneazaButton;
    private AppCompatButton mConstruiesteCastelButton;
    private AppCompatButton mPicteazaButton;
    private AppCompatButton mConstruiesteLegoButton;

    private final String DESENEAZA_URL = "yFoBh3clZJ4";
    private final String CONSTRUIESTE_CASTEL_URL = "cJd4OtMAEOg";
    private final String PICTEAZA_URL = "pXGJCz8tP6s";
    private final String CONSTRUIESTE_LEGO_URL = "RYdykyCf3_w";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDeseneazaButton = findViewById(R.id.deseneaz);
        mDeseneazaButton.setOnClickListener(v ->
                startNewActivty(DESENEAZA_URL, R.raw.desen_sound));

        mConstruiesteCastelButton = findViewById(R.id.construieste_castel);
        mConstruiesteCastelButton.setOnClickListener(v ->
                startNewActivty(CONSTRUIESTE_CASTEL_URL, R.raw.castel_sound));

        mPicteazaButton = findViewById(R.id.picteaza);
        mPicteazaButton.setOnClickListener(v ->
                startNewActivty(PICTEAZA_URL, R.raw.pictat_sound));

        mConstruiesteLegoButton = findViewById(R.id.construieste_lego);
        mConstruiesteLegoButton.setOnClickListener(v ->
                startNewActivty(CONSTRUIESTE_LEGO_URL, R.raw.lego_sound));
    }

    private void startNewActivty(String video_url, int soundId){
        Intent intent = new Intent(getApplicationContext(), CreativityActivity.class);
        intent.putExtra("video_url", video_url);
        intent.putExtra("sound_id", soundId);
        startActivity(intent);
    }

    public static Intent createIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

}
