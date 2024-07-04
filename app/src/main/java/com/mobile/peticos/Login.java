package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
}