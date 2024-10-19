package com.mobile.peticos.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mobile.peticos.Cadastros.CadastrarPet;
import com.mobile.peticos.MainActivity;
import com.mobile.peticos.R;

public class DesejaCadastrarUmPet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deseja_cadastrar_um_pet);
    }

    public void CadastrarPet(View view) {
        Bundle bundleCadastro = getIntent().getExtras();
        int id = bundleCadastro.getInt("id");

        Intent intent = new Intent(this, CadastrarPet.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
    public void Main(View view) {

        Bundle bundleCadastro = getIntent().getExtras();
        int id = bundleCadastro.getInt("id");

        Intent intent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }


}