package com.mobile.peticos.Perfil.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.mobile.peticos.Cadastros.CadastrarPet;
import com.mobile.peticos.Home.HomeFragment;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PersonalizarPets extends AppCompatActivity {


    ImageView  dog_1, dog_2, dog_3, dog_4, dog_5, dog_6, dog_7, dog_8, dog_9, cat_1, cat_2, cat_3, cat_4, cat_5, cat_6, cat_7, cat_8, cat_9;
    ImageView brinquedo_2, brinquedo_3, brinquedo_4, brinquedo_5, brinquedo_6, brinquedo_7, brinquedo_8, brinquedo_9, remover_brinquedo;
    ImageView oculos_1, oculos_2, oculos_3, oculos_4, oculos_5, oculos_6, oculosao_dog, oculosao_cat, oculos_7, remover_oculos;
    ImageView cabecao,cabeca_1, cabeca_2, cabeca_3, cabeca_4, cabeca_6, cabeca_7, cabeca_8, cabeca_9, remover_cabeca;
    ImageView  btn_cor, btn_acessorio_cabeca, btn_brinquedo, petzao, btn_oculos, brinquedao;
    LinearLayout color_dog_layout, color_cat_layout, acessorio_cabeca_layout, brinquedo_layout, oculos_layout;
    Boolean initial_value_oculoes = false, dog_cat; // true = dog, false = cat

    int corpo, cabeca, brinquedo, oculos;
    ImageView btnVoltar;
    Button btAtualizar;
    String especie;
    Retrofit  retrofit2;
    SharedPreferences pets;


    int id;


    private void atualizar (View v){

        String APIMONGO = "https://api-mongo-i1jq.onrender.com/";
        retrofit2 = new Retrofit.Builder()
                .baseUrl(APIMONGO)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIPets api2 = retrofit2.create(APIPets.class);


        Personalizacao petPersonalizado = new Personalizacao(
                id,
                especie,
                cabeca,
                corpo,
                brinquedo,
                oculos
        );
        pets = getSharedPreferences("pets", MODE_PRIVATE);


        // Chama a API para personalizar o pet
        Call<ModelRetorno> callPersonalizacao = api2.personalizarPet(petPersonalizado);
        callPersonalizacao.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PersonalizarPets.this, "Pet personalizado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), PerfilPet.class);
                    v.getContext().startActivity(intent);
                    finish();

                } else {
                    Log.e("Personalizacao", "Falha ao personalizar pet: " + response.code() + " - " + response.message());
                    Toast.makeText(PersonalizarPets.this, "Falha ao personalizar o pet, tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable t) {
                Log.e("Personalizacao", "Erro: " + t.getMessage());
                Toast.makeText(PersonalizarPets.this, "Erro ao tentar personalizar o pet.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void remover (){
        //remover
        remover_oculos.setOnClickListener( v->{
            oculosao_cat.setVisibility(View.INVISIBLE);
            oculosao_dog.setVisibility(View.INVISIBLE);
            initial_value_oculoes = false;
            oculos = 0;
        });
        remover_brinquedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setVisibility(View.INVISIBLE);
                brinquedo = 0;

            }
        });
        remover_cabeca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setVisibility(View.INVISIBLE);
                cabeca = 0;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizar_pets);

        SharedPreferences pets = getSharedPreferences("pets", MODE_PRIVATE);

        SharedPreferences sharedPreferences = getSharedPreferences("Pet", MODE_PRIVATE);



        id = sharedPreferences.getInt("id", 0);
        avatarPet();


        MetodosPet metodosPet = new MetodosPet();


        especie = sharedPreferences.getString("especie", null);

        configurarCampo();
        LinearLayout();
        fragmentOpcoes();
        remover();
        metodosPet.avatarPet(id,oculosao_dog,petzao,oculosao_cat,brinquedao,cabecao, pets, sharedPreferences);


        btAtualizar.setOnClickListener(
                v->{
                    atualizar(v);
                }
        );
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PerfilPet.class);
            v.getContext().startActivity(intent);
            finish();
        });


        if (especie.equals("Gato")) {

            dog_cat = false;
            oculosao_dog.setVisibility(View.GONE);
            if(initial_value_oculoes){
                oculosao_cat.setVisibility(View.VISIBLE);
            }
        }
        else if (especie.equals("Cachorro")){

            dog_cat = true;
            oculosao_cat.setVisibility(View.GONE);
            if(initial_value_oculoes){
                oculosao_dog.setVisibility(View.VISIBLE);
            }
        }
        oculos();
        corCat();
        corDog();
        cabeca();
        brinquedo();




    }

    private void avatarPet( ) {

        String API = "https://api-mongo-i1jq.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPets apiPets = retrofit.create(APIPets.class);
        Call<Personalizacao> call = apiPets.getPersonalizacao(id);
        call.enqueue(new Callback<Personalizacao>() {
            @Override
            public void onResponse(Call<Personalizacao> call, Response<Personalizacao> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        Personalizacao pet = response.body();
                        corpo = pet.getHairId();
                        cabeca = pet.getHatId();
                        brinquedo = pet.getToyId();
                        oculos = pet.getGlassesId();
                        Toast.makeText(PersonalizarPets.this,"" + cabeca,  Toast.LENGTH_SHORT).show();




                        if(pet.getSpecies().equals("Cachorro")) {

                            if(pet.getGlassesId() == 1) {
                                oculosao_dog.setVisibility(View.VISIBLE);
                                oculosao_dog.setImageResource(R.drawable.oculos_personalizado_1);
                            } else if (pet.getGlassesId()==2) {
                                oculosao_dog.setVisibility(View.VISIBLE);
                                oculosao_dog.setImageResource(R.drawable.oculos_personalizado_2);
                            }  else if (pet.getGlassesId()==3) {
                                oculosao_dog.setVisibility(View.VISIBLE);
                                oculosao_dog.setImageResource(R.drawable.oculos_personalizado_3);
                            } else if (pet.getGlassesId()==4) {
                                oculosao_dog.setVisibility(View.VISIBLE);
                                oculosao_dog.setImageResource(R.drawable.oculos_personalizado_4);
                            } else if (pet.getGlassesId()==5) {
                                oculosao_dog.setVisibility(View.VISIBLE);
                                oculosao_dog.setImageResource(R.drawable.oculos_personalizado_5);
                            } else if (pet.getGlassesId()==6) {
                                oculosao_dog.setVisibility(View.VISIBLE);
                                oculosao_dog.setImageResource(R.drawable.oculos_personalizado_6);
                            } else if (pet.getGlassesId()==7) {
                                oculosao_dog.setVisibility(View.VISIBLE);
                                oculosao_dog.setImageResource(R.drawable.oculos_personalizado_7);
                            }
                            petzao.setVisibility(View.VISIBLE);
                            if(pet.getHairId() == 1 || pet.getHairId() == 0) {
                                petzao.setImageResource(R.drawable.dog_personalizado_1);
                            }else if (pet.getHairId()==2) {
                                petzao.setImageResource(R.drawable.dog_personalizado_2);
                            }else if (pet.getHairId()==3) {
                                petzao.setImageResource(R.drawable.dog_personalizado_3);
                            }else if (pet.getHairId()==4) {
                                petzao.setImageResource(R.drawable.dog_personalizado_4);
                            }else if (pet.getHairId()==5) {
                                petzao.setImageResource(R.drawable.dog_personalizado_5);
                            }else if (pet.getHairId()==6) {
                                petzao.setImageResource(R.drawable.dog_personalizado_6);
                            }else if (pet.getHairId()==7) {
                                petzao.setImageResource(R.drawable.dog_personalizado_7);
                            }else if(pet.getHairId()==8) {
                                petzao.setImageResource(R.drawable.dog_personalizado_8);
                            }else if(pet.getHairId()==9) {
                                petzao.setImageResource(R.drawable.dog_personalizado_9);
                            }

                        } else if(pet.getSpecies().equals("Gato")) {
                            if(pet.getGlassesId() == 1) {
                                oculosao_cat.setVisibility(View.VISIBLE);
                                oculosao_cat.setImageResource(R.drawable.oculos_personalizado_1);
                            } else if (pet.getGlassesId()==2) {
                                oculosao_cat.setImageResource(R.drawable.oculos_personalizado_2);
                            }  else if (pet.getGlassesId()==3) {
                                oculosao_cat.setImageResource(R.drawable.oculos_personalizado_3);
                            } else if (pet.getGlassesId()==4) {
                                oculosao_cat.setImageResource(R.drawable.oculos_personalizado_4);
                            } else if (pet.getGlassesId()==5) {
                                oculosao_cat.setImageResource(R.drawable.oculos_personalizado_5);
                            } else if (pet.getGlassesId()==6) {
                                oculosao_cat.setImageResource(R.drawable.oculos_personalizado_6);
                            } else if (pet.getGlassesId()==7) {
                                oculosao_cat.setImageResource(R.drawable.oculos_personalizado_7);
                            }
                            petzao.setVisibility(View.VISIBLE);
                            if(pet.getHairId() == 1 || pet.getHairId() == 0) {
                                petzao.setImageResource(R.drawable.cat_personalizado_1);
                            }else if (pet.getHairId()==2) {
                                petzao.setImageResource(R.drawable.cat_personalizado_2);
                            }else if (pet.getHairId()==3) {
                                petzao.setImageResource(R.drawable.cat_personalizado_3);
                            }else if (pet.getHairId()==4) {
                                petzao.setImageResource(R.drawable.cat_personalizado_4);
                            }else if (pet.getHairId()==5) {
                                petzao.setImageResource(R.drawable.cat_personalizado_5);
                            }else if (pet.getHairId()==6) {
                                petzao.setImageResource(R.drawable.cat_personalizado_6);
                            }else if (pet.getHairId()==7) {
                                petzao.setImageResource(R.drawable.cat_personalizado_7);
                            }else if(pet.getHairId()==8) {
                                petzao.setImageResource(R.drawable.cat_personalizado_8);
                            }else if(pet.getHairId()==9) {
                                petzao.setImageResource(R.drawable.cat_personalizado_9);
                            }

                        }
                        if(pet.getToyId() == 1) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_1);
                        }  else if(pet.getToyId() == 2) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_2);
                        }  else if(pet.getToyId() == 3) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_3);
                        } else if(pet.getToyId() == 4) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_4);
                        } else if(pet.getToyId() == 5) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_5);
                        } else if(pet.getToyId() == 6) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_6);
                        } else if(pet.getToyId() == 7) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_7);
                        }  else if(pet.getToyId() == 8) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_8);
                        }  else if(pet.getToyId() == 9) {
                            brinquedao.setVisibility(View.VISIBLE);
                            brinquedao.setImageResource(R.drawable.brinquedo_personalizado_9);
                        }
                        if(pet.getHatId() == 0){
                            cabecao.setVisibility(View.INVISIBLE);
                        }
                        else if(pet.getHatId() == 1) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_1);
                        } else if(pet.getHatId() == 2) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_2);
                        } else if(pet.getHatId() == 3) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_3);
                        } else if(pet.getHatId() == 4) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_4);
                        } else if(pet.getHatId() == 5) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_5);
                        } else if(pet.getHatId() == 6) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_6);
                        } else if(pet.getHatId() == 7) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_7);
                        } else if(pet.getHatId() == 8) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_8);
                        } else if(pet.getHatId() == 9) {
                            cabecao.setVisibility(View.VISIBLE);
                            cabecao.setImageResource(R.drawable.cabeca_personalizado_9);
                        }}

                } else {
                    petzao.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Personalizacao> call, Throwable t) {
                petzao.setVisibility(View.VISIBLE);
            }
        });
    }


    private void configurarCampo(){
        btAtualizar = findViewById(R.id.btAtualizar);
        btnVoltar = findViewById(R.id.btnVoltar);
        //gato opcoes
        cat_1 = findViewById(R.id.cat_1);
        cat_2 = findViewById(R.id.cat_2);
        cat_3 = findViewById(R.id.cat_3);
        cat_4 = findViewById(R.id.cat_4);
        cat_5 = findViewById(R.id.cat_5);
        cat_6 = findViewById(R.id.cat_6);
        cat_7 = findViewById(R.id.cat_7);
        cat_8 = findViewById(R.id.cat_8);
        cat_9 = findViewById(R.id.cat_9);
        //cachorro opcoes
        dog_1 = findViewById(R.id.dog_1);
        dog_2 = findViewById(R.id.dog_2);
        dog_3 = findViewById(R.id.dog_3);
        dog_4 = findViewById(R.id.dog_4);
        dog_5 = findViewById(R.id.dog_5);
        dog_6 = findViewById(R.id.dog_6);
        dog_7 = findViewById(R.id.dog_7);
        dog_8 = findViewById(R.id.dog_8);
        dog_9 = findViewById(R.id.dog_9);
        // brinquedo
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
        //cabeca
        cabecao = findViewById(R.id.cabecao);
        remover_cabeca = findViewById(R.id.remover_cabeca);
        cabeca_1 = findViewById(R.id.cabeca_1);
        cabeca_2 = findViewById(R.id.cabeca_2);
        cabeca_3 = findViewById(R.id.cabeca_3);
        cabeca_4 = findViewById(R.id.cabeca_4);
        cabeca_6 = findViewById(R.id.cabeca_6);
        cabeca_7 = findViewById(R.id.cabeca_7);
        cabeca_8 = findViewById(R.id.cabeca_8);
        cabeca_9 = findViewById(R.id.cabeca_9);
        //oculos
        oculosao_dog = findViewById(R.id.oculosao_dog);
        oculosao_cat = findViewById(R.id.oculosao_cat);
        remover_oculos = findViewById(R.id.remover_oculos);
        oculos_1 = findViewById(R.id.oculos_1);
        oculos_2 = findViewById(R.id.oculos_2);
        oculos_3 = findViewById(R.id.oculos_3);
        oculos_4 = findViewById(R.id.oculos_4);
        oculos_5 = findViewById(R.id.oculos_5);
        oculos_6 = findViewById(R.id.oculos_6);
        oculos_7 = findViewById(R.id.oculos_7);
        //botoes
        btn_brinquedo = findViewById(R.id.btn_brinquedo);
        btn_cor = findViewById(R.id.btn_color);

        btn_acessorio_cabeca = findViewById(R.id.btn_acessorio_cabeca);
        btn_oculos = findViewById(R.id.btn_oculos);

    }
    private void LinearLayout(){
        //linearlayout
        brinquedo_layout = findViewById(R.id.brinquedos);

        color_dog_layout = findViewById(R.id.cachorros_cores);
        petzao = findViewById(R.id.petzao);
        acessorio_cabeca_layout = findViewById(R.id.cabeca);
        oculos_layout = findViewById(R.id.oculos);
        color_cat_layout = findViewById(R.id.gatos_cores);

        //icon pre selecionado
        btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);

        //linearlayout escondidos

        brinquedo_layout.setVisibility(View.GONE);
        acessorio_cabeca_layout.setVisibility(View.GONE);
        oculos_layout.setVisibility(View.GONE);
        oculosao_cat.setVisibility(View.INVISIBLE);
        oculosao_dog.setVisibility(View.INVISIBLE);
        brinquedao.setVisibility(View.INVISIBLE);
        cabecao.setVisibility(View.INVISIBLE);
        
        if(especie.equals("Gato")){
            color_cat_layout.setVisibility(View.VISIBLE);
            color_dog_layout.setVisibility(View.GONE);
        }else{
            color_cat_layout.setVisibility(View.GONE);
            color_dog_layout.setVisibility(View.VISIBLE);
        }

    }
    private void fragmentOpcoes(){
        btn_cor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setando cores certas

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

                brinquedo_layout.setVisibility(View.GONE);
                acessorio_cabeca_layout.setVisibility(View.GONE);
                oculos_layout.setVisibility(View.GONE);



            }
        });

        btn_brinquedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setando cores

                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_1);
                btn_brinquedo.setImageResource(R.drawable.ic_personalizar_pet_4_2);
                btn_oculos.setImageResource(R.drawable.ic_personalizar_pet_5_1);
                //escondendo os layouts
                color_dog_layout.setVisibility(View.GONE);

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

                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_2);
                btn_brinquedo.setImageResource(R.drawable.ic_personalizar_pet_4_1);
                btn_oculos.setImageResource(R.drawable.ic_personalizar_pet_5_1);
                //escondendo os layouts
                color_dog_layout.setVisibility(View.GONE);

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

                btn_cor.setImageResource(R.drawable.ic_personalizar_pet_2_1);
                btn_acessorio_cabeca.setImageResource(R.drawable.ic_personalizar_pet_3_1);
                btn_brinquedo.setImageResource(R.drawable.ic_personalizar_pet_4_1);
                btn_oculos.setImageResource(R.drawable.ic_personalizar_pet_5_2);
                //escondendo os layouts
                color_dog_layout.setVisibility(View.GONE);

                brinquedo_layout.setVisibility(View.GONE);
                acessorio_cabeca_layout.setVisibility(View.GONE);
                oculos_layout.setVisibility(View.VISIBLE);
                color_cat_layout.setVisibility(View.GONE);

            }
        });

    }
    private void oculos() {
        //setar oculos
        oculos_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initial_value_oculoes =true;
                oculos = 7;
                if(dog_cat == true){
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_7);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_7);
                    oculosao_dog.setVisibility(View.VISIBLE);

                }else{
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_7);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_7);
                    oculosao_cat.setVisibility(View.VISIBLE);

                }
            }
        });
        oculos_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes = true;
                oculos = 1;
                if (dog_cat == true) {
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_1);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_1);
                    oculosao_dog.setVisibility(View.VISIBLE);

                } else {
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_1);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_1);
                    oculosao_cat.setVisibility(View.VISIBLE);

                }

            }
        });
        oculos_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes = true;
                oculos = 2;
                if (dog_cat == true) {
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_2);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_2);
                    oculosao_dog.setVisibility(View.VISIBLE);
                } else {
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_2);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_2);
                    oculosao_cat.setVisibility(View.VISIBLE);

                }

            }
        });
        oculos_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes = true;
                oculos = 3;
                if (dog_cat == true) {
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_3);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_3);
                    oculosao_dog.setVisibility(View.VISIBLE);
                } else {
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_3);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_3);
                    oculosao_cat.setVisibility(View.VISIBLE);
                }

            }
        });
        oculos_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initial_value_oculoes = true;
                oculos = 4;
                if (dog_cat == true) {
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_4);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_4);
                    oculosao_dog.setVisibility(View.VISIBLE);
                } else {
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_4);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_4);
                    oculosao_cat.setVisibility(View.VISIBLE);
                }

            }
        });
        oculos_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oculos = 5;
                initial_value_oculoes = true;
                if (dog_cat == true) {
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_5);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_5);
                    oculosao_dog.setVisibility(View.VISIBLE);
                } else {
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_5);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_5);
                    oculosao_cat.setVisibility(View.VISIBLE);
                }

            }
        });
        oculos_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oculos = 6;
                initial_value_oculoes = true;
                if (dog_cat == true) {
                    oculosao_cat.setVisibility(View.INVISIBLE);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_6);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_6);
                    oculosao_dog.setVisibility(View.VISIBLE);
                } else {
                    oculosao_dog.setVisibility(View.INVISIBLE);
                    oculosao_dog.setImageResource(R.drawable.oculos_personalizado_6);
                    oculosao_cat.setImageResource(R.drawable.oculos_personalizado_6);
                    oculosao_cat.setVisibility(View.VISIBLE);
                }

            }
        });

    }
    private void corCat(){
        //SETAR COR CAT
        cat_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 1;
                petzao.setImageResource(R.drawable.cat_personalizado_1);
            }
        });
        cat_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 2;
                petzao.setImageResource(R.drawable.cat_personalizado_2);
            }
        });
        cat_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 3;
                petzao.setImageResource(R.drawable.cat_personalizado_3);
            }
        });
        cat_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 4;
                petzao.setImageResource(R.drawable.cat_personalizado_4);
            }
        });
        cat_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 5;
                petzao.setImageResource(R.drawable.cat_personalizado_5);
            }
        });
        cat_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 6;
                petzao.setImageResource(R.drawable.cat_personalizado_6);
            }
        });
        cat_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 7;
                petzao.setImageResource(R.drawable.cat_personalizado_7);
            }
        });
        cat_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 8;
                petzao.setImageResource(R.drawable.cat_personalizado_8);
            }
        });
        cat_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 9;
                petzao.setImageResource(R.drawable.cat_personalizado_9);
            }
        });
    }
    private void corDog(){
        //setar cachorro
        dog_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 1;
                petzao.setImageResource(R.drawable.dog_personalizado_1);
            }
        });
        dog_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 2;
                petzao.setImageResource(R.drawable.dog_personalizado_2);
            }
        });
        dog_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 3;
                petzao.setImageResource(R.drawable.dog_personalizado_3);
            }
        });
        dog_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 4;
                petzao.setImageResource(R.drawable.dog_personalizado_4);
            }
        });
        dog_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 5;
                petzao.setImageResource(R.drawable.dog_personalizado_5);
            }
        });
        dog_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 6;
                petzao.setImageResource(R.drawable.dog_personalizado_6);
            }
        });
        dog_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 7;
                petzao.setImageResource(R.drawable.dog_personalizado_7);
            }
        });
        dog_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 8;
                petzao.setImageResource(R.drawable.dog_personalizado_8);
            }
        });
        dog_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                corpo = 9;
                petzao.setImageResource(R.drawable.dog_personalizado_9);
            }
        });
    }
    private void cabeca(){
        //setar cabeca

        cabeca_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_2);
                cabecao.setVisibility(View.VISIBLE);
                cabeca = 2;
            }
        });
        cabeca_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_3);
                cabecao.setVisibility(View.VISIBLE);
                cabeca = 3;
            }
        });
        cabeca_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_4);
                cabecao.setVisibility(View.VISIBLE);
                cabeca = 4;
            }
        });
        cabeca_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_1);
                cabecao.setVisibility(View.VISIBLE);
                cabeca = 1;
            }
        });
        cabeca_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_6);
                cabecao.setVisibility(View.VISIBLE);
                cabeca = 6;
            }
        });
        cabeca_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_7);
                cabecao.setVisibility(View.VISIBLE);
                cabeca = 7;
            }
        });
        cabeca_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_8);
                cabecao.setVisibility(View.VISIBLE);
                cabeca = 8;
            }
        });
        cabeca_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cabecao.setImageResource(R.drawable.cabeca_personalizado_9);
                cabecao.setVisibility(View.VISIBLE);
                cabeca = 9;
            }
        });
    }
    private void brinquedo(){
        brinquedo_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_2);
                brinquedao.setVisibility(View.VISIBLE);
                brinquedo = 2;

            }
        });
        brinquedo_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_3);
                brinquedao.setVisibility(View.VISIBLE);
                brinquedo = 3;

            }
        });
        brinquedo_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_4);
                brinquedao.setVisibility(View.VISIBLE);
                brinquedo = 4;

            }
        });
        brinquedo_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_5);
                brinquedao.setVisibility(View.VISIBLE);
                brinquedo = 5;

            }
        });
        brinquedo_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_6);
                brinquedao.setVisibility(View.VISIBLE);
                brinquedo = 6;

            }
        });
        brinquedo_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_7);
                brinquedao.setVisibility(View.VISIBLE);
                brinquedo = 7;

            }
        });
        brinquedo_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_8);
                brinquedao.setVisibility(View.VISIBLE);
                brinquedo = 8;

            }
        });
        brinquedo_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                brinquedao.setImageResource(R.drawable.brinquedo_personalizado_9);
                brinquedao.setVisibility(View.VISIBLE);
                brinquedo = 9;

            }
        });
    }


}