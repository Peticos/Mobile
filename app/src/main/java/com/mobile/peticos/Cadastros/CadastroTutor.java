package com.mobile.peticos.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile.peticos.DesejaCadastrarUmPet;
import com.mobile.peticos.Login;
import com.mobile.peticos.R;

public class CadastroTutor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tutor);
    }

    public void EntrarLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void CadastraPet(View view) {
        Intent intent = new Intent(this, DesejaCadastrarUmPet.class);
        startActivity(intent);
        finish();
    }
}