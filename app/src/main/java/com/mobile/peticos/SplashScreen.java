package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class SplashScreen extends AppCompatActivity {
    ImageView imagemSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imagemSplash = findViewById(R.id.imagemSplash);
        Glide.with(this)
                .asGif()
                .load(R.raw.animacao)
                .into(imagemSplash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {abritela();}
        }, 12000);
    }

    private void abritela(){
        Intent intent = new Intent(this, Login.class);

        startActivity(intent);
        finish();
    }
}
