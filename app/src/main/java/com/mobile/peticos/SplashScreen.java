package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;


public class SplashScreen extends AppCompatActivity {
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        videoView = findViewById(R.id.videoView);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.animacao);
        videoView.setVideoURI(video);

        videoView.setOnCompletionListener(mp -> {
            abritela();
            finish();
        });

        videoView.start();
    }

    private void abritela(){
        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
        finish();
    }
}
