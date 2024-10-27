package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PesoPets extends AppCompatActivity {
    FloatingActionButton btnAdd;
    CardView cardCadastrarPeso;
    Button btnSair, btnSalvar;
    ImageView btn_voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso_pets);

        btnAdd = findViewById(R.id.btnadd);
        btnSair = findViewById(R.id.btn_sair);
        btnSalvar = findViewById(R.id.btn_salvar);
        cardCadastrarPeso = findViewById(R.id.cardCadastrarPeso);
        btn_voltar = findViewById(R.id.btn_voltar);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardCadastrarPeso.setVisibility(View.VISIBLE);
            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardCadastrarPeso.setVisibility(View.GONE);
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarPeso();
            }
        });
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void criarPeso(){}
}