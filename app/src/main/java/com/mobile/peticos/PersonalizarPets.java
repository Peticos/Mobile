package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PersonalizarPets extends AppCompatActivity {

    ImageView dog_escolha, cat_escolha, dog_1, dog_2, dog_3, dog_4, dog_5, dog_6, dog_7, dog_8, dog_9, cat_1, cat_2, cat_3, cat_4, cat_5, cat_6, cat_7, cat_8, cat_9;
    ImageView btn_escolha, btn_cor, btn_acessorio_cabeca, btn_coleira, petzao;
    LinearLayout escolhercatordog, color_dog, color_cat, acessorio_cabeca, coleira;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizar_pets);
        dog_escolha = findViewById(R.id.dog_escolha);
        cat_escolha = findViewById(R.id.cat_escolha);
        dog_1 = findViewById(R.id.dog_1);
        dog_2 = findViewById(R.id.dog_2);
        dog_3 = findViewById(R.id.dog_3);
        dog_4 = findViewById(R.id.dog_4);
        dog_5 = findViewById(R.id.dog_5);
        dog_6 = findViewById(R.id.dog_6);
        dog_7 = findViewById(R.id.dog_7);
        dog_8 = findViewById(R.id.dog_8);
        dog_9 = findViewById(R.id.dog_9);
//        cat_1 = findViewById(R.id.cat_1);
//        cat_2 = findViewById(R.id.cat_2);
//        cat_3 = findViewById(R.id.cat_3);
//        cat_4 = findViewById(R.id.cat_4);
//        cat_5 = findViewById(R.id.cat_5);
//        cat_6 = findViewById(R.id.cat_6);
//        cat_7 = findViewById(R.id.cat_7);
//        cat_8 = findViewById(R.id.cat_8);
//        cat_9 = findViewById(R.id.cat_9);
        btn_coleira = findViewById(R.id.btn_coleira);
        btn_cor = findViewById(R.id.btn_color);
        btn_escolha = findViewById(R.id.btn_escolher_catordog);
        btn_acessorio_cabeca = findViewById(R.id.btn_acessorio_cabeca);
        escolhercatordog = findViewById(R.id.escolher_catordog);
        color_dog = findViewById(R.id.cachorros_cores);
        petzao = findViewById(R.id.petzao);




        btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_2);
        color_dog.setVisibility(View.GONE);

        btn_cor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_1);
                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_2);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_1);
                btn_coleira.setImageResource(R.drawable.ic_personalizar_pet_4_1);
                color_dog.setVisibility(View.VISIBLE);
                escolhercatordog.setVisibility(View.GONE);
            }
        });
        btn_escolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_2);
                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_1);
                btn_coleira.setImageResource(R.drawable.ic_personalizar_pet_4_1);
                color_dog.setVisibility(View.GONE);
                escolhercatordog.setVisibility(View.VISIBLE);
            }
        });


        dog_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_1);
            }
        });
        dog_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_2);
            }
        });
        dog_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_3);
            }
        });
        dog_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_4);
            }
        });
        dog_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_5);
            }
        });
        dog_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_6);
            }
        });
        dog_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_7);
            }
        });
        dog_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_8);
            }
        });
        dog_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_9);
            }
        });




    }


}