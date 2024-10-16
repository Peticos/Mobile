package com.mobile.peticos.Perfil.Pet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobile.peticos.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.Apis.APIPets;
import com.mobile.peticos.Perfil.Pet.Apis.Personalizacao;
import com.mobile.peticos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalizarPets extends AppCompatActivity {

    ImageView dog_escolha, cat_escolha, dog_1, dog_2, dog_3, dog_4, dog_5, dog_6, dog_7, dog_8, dog_9, cat_1, cat_2, cat_3, cat_4, cat_5, cat_6, cat_7, cat_8, cat_9;
    ImageView brinquedo_2, brinquedo_3, brinquedo_4, brinquedo_5, brinquedo_6, brinquedo_7, brinquedo_8, brinquedo_9, remover_brinquedo;
    ImageView oculos_1, oculos_2, oculos_3, oculos_4, oculos_5, oculos_6, oculosao_dog, oculosao_cat, oculos_7, remover_oculos;
    ImageView cabecao,cabeca_1, cabeca_2, cabeca_3, cabeca_4, cabeca_6, cabeca_7, cabeca_8, cabeca_9, remover_cabeca;
    ImageView btn_escolha, btn_cor, btn_acessorio_cabeca, btn_brinquedo, petzao, btn_oculos, brinquedao;
    LinearLayout escolhercatordog_layout, color_dog_layout, color_cat_layout, acessorio_cabeca_layout, brinquedo_layout, oculos_layout;
    Boolean initial_value_oculoes = false, dog_cat = true; // true = dog, false = cat

    int hatId=0,  hairId=0, toyId=0, glassesId=0;
    Button btnSalvar, btnSair;
    String species;
    ImageView btnVoltar;
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
        remover_brinquedo = findViewById(R.id.remover_brinquedo);
        brinquedo_2 = findViewById(R.id.brinquedo_2);
        brinquedo_3 = findViewById(R.id.brinquedo_3);
        brinquedo_4 = findViewById(R.id.brinquedo_4);
        brinquedo_5 = findViewById(R.id.brinquedo_5);
        brinquedo_6 = findViewById(R.id.brinquedo_6);
        brinquedo_7 = findViewById(R.id.brinquedo_7);
        brinquedo_8 = findViewById(R.id.brinquedo_8);
        brinquedo_9 = findViewById(R.id.brinquedo_9);
        brinquedao = findViewById(R.id.brinquedao);
        cabecao = findViewById(R.id.cabecao);
        remover_cabeca = findViewById(R.id.remover_cabeca);
        remover_oculos = findViewById(R.id.remover_oculos);
        cabeca_1 = findViewById(R.id.cabeca_1);
        cabeca_2 = findViewById(R.id.cabeca_2);
        cabeca_3 = findViewById(R.id.cabeca_3);
        cabeca_4 = findViewById(R.id.cabeca_4);
        remover_cabeca = findViewById(R.id.remover_cabeca);
        cabeca_6 = findViewById(R.id.cabeca_6);
        cabeca_7 = findViewById(R.id.cabeca_7);
        cabeca_8 = findViewById(R.id.cabeca_8);
        cabeca_9 = findViewById(R.id.cabeca_9);
        oculosao_dog = findViewById(R.id.oculosao_dog);
        oculosao_cat = findViewById(R.id.oculosao_cat);
        oculos_1 = findViewById(R.id.oculos_1);
        oculos_2 = findViewById(R.id.oculos_2);
        oculos_3 = findViewById(R.id.oculos_3);
        oculos_4 = findViewById(R.id.oculos_4);
        oculos_5 = findViewById(R.id.oculos_5);
        oculos_6 = findViewById(R.id.oculos_6);
        cat_1 = findViewById(R.id.cat_1);
        cat_2 = findViewById(R.id.cat_2);
        cat_3 = findViewById(R.id.cat_3);
        cat_4 = findViewById(R.id.cat_4);
        cat_5 = findViewById(R.id.cat_5);
        cat_6 = findViewById(R.id.cat_6);
        cat_7 = findViewById(R.id.cat_7);
        cat_8 = findViewById(R.id.cat_8);
        cat_9 = findViewById(R.id.cat_9);
        //botoes
        btn_brinquedo = findViewById(R.id.btn_brinquedo);
        btn_cor = findViewById(R.id.btn_color);
        btn_escolha = findViewById(R.id.btn_escolher_catordog);
        btn_acessorio_cabeca = findViewById(R.id.btn_acessorio_cabeca);
        btn_oculos = findViewById(R.id.btn_oculos);
        //escolha
        dog_escolha = findViewById(R.id.dog_escolha);
        cat_escolha = findViewById(R.id.cat_escolha);

        //linearlayout
        brinquedo_layout = findViewById(R.id.brinquedos);
        escolhercatordog_layout = findViewById(R.id.escolher_catordog);
        color_dog_layout = findViewById(R.id.cachorros_cores);
        petzao = findViewById(R.id.petzao);
        acessorio_cabeca_layout = findViewById(R.id.cabeca);
        oculos_layout = findViewById(R.id.oculos);
        color_cat_layout = findViewById(R.id.gatos_cores);





        //icon pre selecionado
        btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_2);

        //linearlayout escondidos
        color_dog_layout.setVisibility(View.GONE);
        brinquedo_layout.setVisibility(View.GONE);
        acessorio_cabeca_layout.setVisibility(View.GONE);
        oculos_layout.setVisibility(View.GONE);
        oculosao_cat.setVisibility(View.INVISIBLE);
        oculosao_dog.setVisibility(View.INVISIBLE);
        brinquedao.setVisibility(View.INVISIBLE);
        cabecao.setVisibility(View.INVISIBLE);
        color_cat_layout.setVisibility(View.GONE);

        //btn voltar
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v ->{
            Intent intent = new Intent(this, EditarPerfilPet.class);
            startActivity(intent);
        });
        //btn sair e salvar
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSair = findViewById(R.id.btnSair);
        btnSair.setOnClickListener(v ->{
            Intent intent = new Intent(this, EditarPerfilPet.class);
            startActivity(intent);
        });
        btnSalvar.setOnClickListener(v->{
            salvar(v);
        });


        btn_cor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setando cores certas
                btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_1);
                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_2);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_1);
                btn_brinquedo.setImageResource(R.drawable.ic_personalizar_pet_4_1);
                btn_oculos.setImageResource(R.drawable.ic_personalizar_pet_5_1);
                //escondendo os layouts
                if(dog_cat == true){
                    color_dog_layout.setVisibility(View.VISIBLE);
                    color_cat_layout.setVisibility(View.GONE);
                }else{
                    color_cat_layout.setVisibility(View.VISIBLE);
                    color_dog_layout.setVisibility(View.GONE);
                }
                escolhercatordog_layout.setVisibility(View.GONE);
                brinquedo_layout.setVisibility(View.GONE);
                acessorio_cabeca_layout.setVisibility(View.GONE);
                oculos_layout.setVisibility(View.GONE);



            }
        });
        btn_escolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setando cores
                btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_2);
                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_1);
                btn_brinquedo.setImageResource(R.drawable.ic_personalizar_pet_4_1);
                btn_oculos.setImageResource(R.drawable.ic_personalizar_pet_5_1);
                //escondendo os layouts
                color_dog_layout.setVisibility(View.GONE);
                escolhercatordog_layout.setVisibility(View.VISIBLE);
                brinquedo_layout.setVisibility(View.GONE);
                acessorio_cabeca_layout.setVisibility(View.GONE);
                oculos_layout.setVisibility(View.GONE);
                color_cat_layout.setVisibility(View.GONE);

            }
        });
        btn_brinquedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setando cores
                btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_1);
                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_1);
                btn_brinquedo.setImageResource(R.drawable.ic_personalizar_pet_4_2);
                btn_oculos.setImageResource(R.drawable.ic_personalizar_pet_5_1);
                //escondendo os layouts
                color_dog_layout.setVisibility(View.GONE);
                escolhercatordog_layout.setVisibility(View.GONE);
                brinquedo_layout.setVisibility(View.VISIBLE);
                acessorio_cabeca_layout.setVisibility(View.GONE);
                oculos_layout.setVisibility(View.GONE);
                color_cat_layout.setVisibility(View.GONE);

            }
        });
        btn_acessorio_cabeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setando cores
                btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_1);
                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_2);
                btn_brinquedo.setImageResource(R.drawable.ic_personalizar_pet_4_1);
                btn_oculos.setImageResource(R.drawable.ic_personalizar_pet_5_1);
                //escondendo os layouts
                color_dog_layout.setVisibility(View.GONE);
                escolhercatordog_layout.setVisibility(View.GONE);
                brinquedo_layout.setVisibility(View.GONE);
                acessorio_cabeca_layout.setVisibility(View.VISIBLE);
                oculos_layout.setVisibility(View.GONE);
                color_cat_layout.setVisibility(View.GONE);

            }
        });
        btn_oculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setando cores
                btn_escolha.setImageResource(R.drawable.ic_personalizar_pet_1_1);
                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_1);
                btn_brinquedo.setImageResource(R.drawable.ic_personalizar_pet_4_1);
                btn_oculos.setImageResource(R.drawable.ic_personalizar_pet_5_2);
                //escondendo os layouts
                color_dog_layout.setVisibility(View.GONE);
                escolhercatordog_layout.setVisibility(View.GONE);
                brinquedo_layout.setVisibility(View.GONE);
                acessorio_cabeca_layout.setVisibility(View.GONE);
                oculos_layout.setVisibility(View.VISIBLE);
                color_cat_layout.setVisibility(View.GONE);

            }
        });

        //setar cat ou dog
        cat_escolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_1);
                dog_cat = false;
                oculosao_dog.setVisibility(View.GONE);
                if(initial_value_oculoes == true){
                    oculosao_cat.setVisibility(View.VISIBLE);
                }
                species = "GATO";


            }
        });
        dog_escolha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_1);
                dog_cat = true;
                oculosao_cat.setVisibility(View.GONE);
                if(initial_value_oculoes == true){
                    oculosao_dog.setVisibility(View.VISIBLE);
                }
                species = "CACHORRO";

            }
        });

        //remover
        remover_oculos.setOnClickListener( v->{
            oculosao_cat.setVisibility(View.INVISIBLE);
            oculosao_dog.setVisibility(View.INVISIBLE);
            initial_value_oculoes = false;
            glassesId = 0;
        });
        remover_brinquedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setVisibility(View.INVISIBLE);
                toyId = 0;

            }
        });
        remover_cabeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setVisibility(View.INVISIBLE);
                hairId =0;
            }
        });

        //setar oculos
//        oculos_7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                initial_value_oculoes =true;
//                if(dog_cat == true){
//                    oculosao_cat.setVisibility(View.INVISIBLE);
//                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_7);
//                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_7);
//                    oculosao_dog.setVisibility(View.VISIBLE);
//
//                }else{
//                    oculosao_dog.setVisibility(View.INVISIBLE);
//                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_7);
//                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_7);
//                    oculosao_cat.setVisibility(View.VISIBLE);
//
//                }
//            }
//        });
        oculos_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes =true;
                if(dog_cat == true){
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_1);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_1);
                    oculosao_dog.setVisibility(View.VISIBLE);

                }else{
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_1);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_1);
                    oculosao_cat.setVisibility(View.VISIBLE);

                }
                glassesId = 1;

            }
        });
        oculos_2.setOnClickListener(new View.OnClickListener() {
            @Override            public void onClick(View view) {
                initial_value_oculoes =true;
                if(dog_cat == true){
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_2);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_2);
                    oculosao_dog.setVisibility(View.VISIBLE);
                }else{
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_2);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_2);
                    oculosao_cat.setVisibility(View.VISIBLE);

                }
                glassesId = 2;

            }
        });
        oculos_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes =true;
                if(dog_cat == true){
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_3);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_3);
                    oculosao_dog.setVisibility(View.VISIBLE);
                }else{
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_3);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_3);
                    oculosao_cat.setVisibility(View.VISIBLE);
                }
                glassesId = 3;

            }
        });
        oculos_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes =true;
                if(dog_cat == true){
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_4);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_4);
                    oculosao_dog.setVisibility(View.VISIBLE);
                }else{
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_4);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_4);
                    oculosao_cat.setVisibility(View.VISIBLE);
                }
                glassesId = 4;

            }
        });
        oculos_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes =true;
                if(dog_cat == true){
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_5);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_5);
                    oculosao_dog.setVisibility(View.VISIBLE);
                }else{
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_5);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_5);
                    oculosao_cat.setVisibility(View.VISIBLE);
                }
                glassesId = 5;
            }
        });
        oculos_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes =true;
                if(dog_cat == true){
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_6);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_6);
                    oculosao_dog.setVisibility(View.VISIBLE);
                }else{
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_6);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_6);
                    oculosao_cat.setVisibility(View.VISIBLE);
                }
                glassesId = 6;
            }
        });
        //FALTANDO 7
        //SETAR COR CAT
        cat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_1);
                hatId = 1;
            }
        });
        cat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_2);
                hatId = 2;
            }
        });
        cat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_3);
                hatId = 3;
            }
        });
        cat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_4);
                hatId = 4;
            }
        });
        cat_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_5);
                hatId = 5;
            }
        });
        cat_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_6);
                hatId = 6;
            }
        });
        cat_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_7);
                hatId = 7;
            }
        });
        cat_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_8);
                hatId = 8;
            }
        });
        cat_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.cat_personalizado_9);
                hatId = 9;
            }
        });
        //setar brinquedo
        //FALTANDO 1
        brinquedo_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_2);
                brinquedao.setVisibility(View.VISIBLE);
                toyId = 2;

            }
        });
        brinquedo_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_3);
                brinquedao.setVisibility(View.VISIBLE);
                toyId = 3;

            }
        });
        brinquedo_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_4);
                brinquedao.setVisibility(View.VISIBLE);
                toyId = 4;

            }
        });
        brinquedo_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_5);
                brinquedao.setVisibility(View.VISIBLE);
                toyId = 5;

            }
        });
        brinquedo_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_6);
                brinquedao.setVisibility(View.VISIBLE);
                toyId = 6;
            }
        });
        brinquedo_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_7);
                brinquedao.setVisibility(View.VISIBLE);
                toyId = 7;
            }
        });
        brinquedo_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_8);
                brinquedao.setVisibility(View.VISIBLE);
                toyId = 8;
            }
        });
        brinquedo_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_9);
                brinquedao.setVisibility(View.VISIBLE);
                toyId = 9;
            }
        });

        //setar cabeca
        //FALTANDO 5
        cabeca_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_2);
                cabecao.setVisibility(View.VISIBLE);
                hairId = 2;
            }
        });
        cabeca_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_3);
                cabecao.setVisibility(View.VISIBLE);
                hairId = 3;
            }
        });
        cabeca_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_4);
                cabecao.setVisibility(View.VISIBLE);
                hairId = 4;
            }
        });
        cabeca_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_5);
                cabecao.setVisibility(View.VISIBLE);
                hairId = 1;
            }
        });
        cabeca_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_6);
                cabecao.setVisibility(View.VISIBLE);
                hairId = 6;
            }
        });
        cabeca_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_7);
                cabecao.setVisibility(View.VISIBLE);
                hairId = 7;
            }
        });
        cabeca_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_8);
                cabecao.setVisibility(View.VISIBLE);
                hairId = 8;
            }
        });
        cabeca_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_9);
                cabecao.setVisibility(View.VISIBLE);
                hairId = 9;
            }
        });

        //setar cachorro
        dog_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_1);
                hatId = 1;
            }
        });
        dog_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_2);
                hatId = 1;
            }
        });
        dog_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_3);
                hatId = 3;
            }
        });
        dog_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_4);
                hatId = 4;
            }
        });
        dog_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_5);
                hatId = 5;
            }
        });
        dog_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_6);
                hatId = 6;
            }
        });
        dog_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_7);
                hatId = 7;
            }
        });
        dog_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_8);
                hatId = 9;
            }
        });
        dog_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                petzao.setImageResource(R.drawable.dog_personalizado_9);
                hatId = 9;
            }
        });




    }

    private void salvar(View v) {
        Bundle bundle = getIntent().getExtras();

        String API = "https://apimongo-ghjh.onrender.com/swagger-ui/index.html#/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Personalizacao pet = new Personalizacao(bundle.getInt("id"),species,hatId,hairId,toyId,glassesId);

        APIPets apiPets = retrofit.create(APIPets.class);
        Call<ModelRetorno> call = apiPets.personalizarPet(pet);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(PersonalizarPets.this, "Pet personalizado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PersonalizarPets.this, EditarPerfilPet.class);
                    startActivity(intent);
                }
            }
            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable t) {
                Toast.makeText(PersonalizarPets.this, "Erro ao Cadastrar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}