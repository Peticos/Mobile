package com.mobile.peticos.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mobile.peticos.Cadastros.CadastrarPet;
import com.mobile.peticos.MainActivity;
import com.mobile.peticos.R;

public class DesejaCadastrarUmPet extends AppCompatActivity {
    Button btnSim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deseja_cadastrar_um_pet);
        btnSim = findViewById(R.id.btnSim);

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleCadastro = getIntent().getExtras();
                int id = bundleCadastro.getInt("id");

                Intent intent = new Intent(DesejaCadastrarUmPet.this, CadastrarPet.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
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

    public void Entrar(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}