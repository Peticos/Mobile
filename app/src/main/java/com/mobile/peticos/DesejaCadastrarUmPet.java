package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DesejaCadastrarUmPet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deseja_cadastrar_um_pet);
    }

    public void CadastrarPet(View view) {
        Intent intent = new Intent(this, CadastrarPet.class);
        startActivity(intent);
        finish();
    }
}