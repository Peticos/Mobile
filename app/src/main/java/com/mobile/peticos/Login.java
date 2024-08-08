package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile.peticos.feedFotoPet.feedFotoPet;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void TutorOuPeofissional(View view) {
        Intent intent = new Intent(this, Tutor_ou_Profissional.class);
        startActivity(intent);
        finish();
    }

    public void Entrar(View view) {
        Intent intent = new Intent(this, feedFotoPet.class);
        startActivity(intent);
        finish();
    }
}