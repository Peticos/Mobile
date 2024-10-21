package com.mobile.peticos.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile.peticos.Cadastros.CadastroProfissional;
import com.mobile.peticos.Cadastros.CadastroTutor;
import com.mobile.peticos.Login;
import com.mobile.peticos.R;

public class Tutor_ou_Profissional extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_ou_profissional);
    }

    public void EntrarLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    public void CadastroProfissional(View view) {
        Intent intent = new Intent(this, CadastroProfissional.class);
        startActivity(intent);
        finish();

    }

    public void CadastroTutor(View view) {
        Intent intent = new Intent(this, CadastroTutor.class);
        startActivity(intent);
        finish();

    }
}