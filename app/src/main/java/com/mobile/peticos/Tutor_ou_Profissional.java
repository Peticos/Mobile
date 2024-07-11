package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile.peticos.Login;
import com.mobile.peticos.R;

public class Tutor_ou_Profissional extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_ou_profissional);
    }

    public void EntarLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}