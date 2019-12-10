package com.matejnevlud.clovecenezlobse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        player = MediaPlayer.create(this, R.raw.background);
        player.setLooping(true);
        player.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
    }

    public void onStartGameButtonClicked(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        player.pause();
        startActivity(intent);
    }
}
