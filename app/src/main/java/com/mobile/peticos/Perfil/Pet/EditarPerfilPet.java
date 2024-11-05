package com.mobile.peticos.Perfil.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.video.VideoRecordEvent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.mobile.peticos.Cadastros.CadastrarPet;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.Cor;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.Perfil.Pet.API.Raca;
import com.mobile.peticos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.mobile.peticos.Perfil.Pet.API.APIPets;

import java.util.ArrayList;
import java.util.List;

public class EditarPerfilPet extends AppCompatActivity {
    ImageView btnVoltarEditar, petzao, cabeca, oculos_dog, oculos_cat, brinquedo;
    int id;
    int idTutor;
    Button btnCadastrar;
    AutoCompleteTextView especie, raca, cor, porte, genero;
    TextView nome, idade;
    Retrofit retrofit1, retrofit2;
    int cor_pet, cabeca_pet, brinquedo_pet, oculos_pet;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_pet);

        brinquedo = findViewById(R.id.brinquedao);
        oculos_dog = findViewById(R.id.oculosao_dog);
        oculos_cat = findViewById(R.id.oculosao_cat);
        petzao = findViewById(R.id.petzao);
        cabeca = findViewById(R.id.cabecao);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        especie = findViewById(R.id.especie);
        raca = findViewById(R.id.raca);
        cor = findViewById(R.id.cor);
        porte = findViewById(R.id.porte);
        genero = findViewById(R.id.genero);
        nome = findViewById(R.id.nome);
        idade = findViewById(R.id.idade);
        // Chamar API para setar os drops downs
        String APISQL = "https://apipeticos-ltwk.onrender.com";
        retrofit1 = new Retrofit.Builder()
                .baseUrl(APISQL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        setarDropDowns();
        String APIMONGO = "https://api-mongo-i1jq.onrender.com";
        retrofit2 = new Retrofit.Builder()
                .baseUrl(APIMONGO)
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        SharedPreferences sharedPreferences = getSharedPreferences("Pet", MODE_PRIVATE);
        SharedPreferences pets = getSharedPreferences("pets", MODE_PRIVATE);
        SharedPreferences sharedPreferencesPerfil = getSharedPreferences("Perfil", MODE_PRIVATE);
        idTutor = sharedPreferencesPerfil.getInt("id", 0);

        nome.setText(sharedPreferences.getString("nickname", "nome do pet"));
        especie.setText(sharedPreferences.getString("especie", "nome do pet"));
        if(sharedPreferences.getString("genero", "genero").equals("F")){
            genero.setText("Fêmea");
        }else{
            genero.setText("Macho");
        }
        idade.setText(String.valueOf(sharedPreferences.getInt("idade", 0)));
        raca.setText(sharedPreferences.getString("raca", "raca"));
        cor.setText(sharedPreferences.getString("cor", "cor"));
        porte.setText(sharedPreferences.getString("porte", "porte"));

        cabeca.setVisibility(View.INVISIBLE);
        id = sharedPreferences.getInt("id", 0);
        avatarPet();
        btnCadastrar.setOnClickListener(v->{
            update();
        });








        btnVoltarEditar = findViewById(R.id.btn_voltar_perfil);
        btnVoltarEditar.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PerfilPet.class);
            v.getContext().startActivity(intent);
            finish();
        });
    }




        private void avatarPet () {

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
                        if (response.body() != null) {
                            Personalizacao pet = response.body();
                            SharedPreferences pets = getSharedPreferences("pets", MODE_PRIVATE);
                            SharedPreferences.Editor editor = pets.edit();
                            editor.putInt("cor", pet.getHatId());
                            editor.putInt("oculos", pet.getGlassesId());
                            editor.putInt("brinquedo", pet.getToyId());
                            editor.putInt("cabeca", pet.getHairId());
                            editor.apply(); // ou editor.commit();

                            cabeca_pet = pet.getHatId();
                            brinquedo_pet = pet.getToyId();
                            oculos_pet = pet.getGlassesId();




                            if (pet.getSpecies().equals("Cachorro")) {

                                if (pet.getGlassesId() == 1) {
                                    oculos_dog.setVisibility(View.VISIBLE);
                                    oculos_dog.setImageResource(R.drawable.oculos_personalizado_1);
                                } else if (pet.getGlassesId() == 2) {
                                    oculos_dog.setVisibility(View.VISIBLE);
                                    oculos_dog.setImageResource(R.drawable.oculos_personalizado_2);
                                } else if (pet.getGlassesId() == 3) {
                                    oculos_dog.setVisibility(View.VISIBLE);
                                    oculos_dog.setImageResource(R.drawable.oculos_personalizado_3);
                                } else if (pet.getGlassesId() == 4) {
                                    oculos_dog.setVisibility(View.VISIBLE);
                                    oculos_dog.setImageResource(R.drawable.oculos_personalizado_4);
                                } else if (pet.getGlassesId() == 5) {
                                    oculos_dog.setVisibility(View.VISIBLE);
                                    oculos_dog.setImageResource(R.drawable.oculos_personalizado_5);
                                } else if (pet.getGlassesId() == 6) {
                                    oculos_dog.setVisibility(View.VISIBLE);
                                    oculos_dog.setImageResource(R.drawable.oculos_personalizado_6);
                                } else if (pet.getGlassesId() == 7) {
                                    oculos_dog.setVisibility(View.VISIBLE);
                                    oculos_dog.setImageResource(R.drawable.oculos_personalizado_7);
                                }
                                petzao.setVisibility(View.VISIBLE);
                                if (pet.getHairId() == 1 || pet.getHairId() == 0) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_1);
                                } else if (pet.getHairId() == 2) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_2);
                                } else if (pet.getHairId() == 3) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_3);
                                } else if (pet.getHairId() == 4) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_4);
                                } else if (pet.getHairId() == 5) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_5);
                                } else if (pet.getHairId() == 6) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_6);
                                } else if (pet.getHairId() == 7) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_7);
                                } else if (pet.getHairId() == 8) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_8);
                                } else if (pet.getHairId() == 9) {
                                    petzao.setImageResource(R.drawable.dog_personalizado_9);
                                }

                            } else if (pet.getSpecies().equals("Gato")) {
                                if (pet.getGlassesId() == 1) {
                                    oculos_cat.setVisibility(View.VISIBLE);
                                    oculos_cat.setImageResource(R.drawable.oculos_personalizado_1);
                                } else if (pet.getGlassesId() == 2) {
                                    oculos_cat.setVisibility(View.VISIBLE);
                                    oculos_cat.setImageResource(R.drawable.oculos_personalizado_2);
                                } else if (pet.getGlassesId() == 3) {
                                    oculos_cat.setVisibility(View.VISIBLE);
                                    oculos_cat.setImageResource(R.drawable.oculos_personalizado_3);
                                } else if (pet.getGlassesId() == 4) {
                                    oculos_cat.setVisibility(View.VISIBLE);
                                    oculos_cat.setImageResource(R.drawable.oculos_personalizado_4);
                                } else if (pet.getGlassesId() == 5) {
                                    oculos_cat.setVisibility(View.VISIBLE);
                                    oculos_cat.setImageResource(R.drawable.oculos_personalizado_5);
                                } else if (pet.getGlassesId() == 6) {
                                    oculos_cat.setVisibility(View.VISIBLE);
                                    oculos_cat.setImageResource(R.drawable.oculos_personalizado_6);
                                } else if (pet.getGlassesId() == 7) {
                                    oculos_cat.setVisibility(View.VISIBLE);
                                    oculos_cat.setImageResource(R.drawable.oculos_personalizado_7);
                                }
                                petzao.setVisibility(View.VISIBLE);
                                if (pet.getHairId() == 1 || pet.getHatId() == 0) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_1);
                                } else if (pet.getHairId() == 2) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_2);
                                } else if (pet.getHairId() == 3) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_3);
                                } else if (pet.getHairId() == 4) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_4);
                                } else if (pet.getHairId() == 5) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_5);
                                } else if (pet.getHairId() == 6) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_6);
                                } else if (pet.getHairId() == 7) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_7);
                                } else if (pet.getHairId() == 8) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_8);
                                } else if (pet.getHairId() == 9) {
                                    petzao.setImageResource(R.drawable.cat_personalizado_9);
                                }

                            }
                            if (pet.getToyId() == 1) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_1);
                            } else if (pet.getToyId() == 2) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_2);
                            } else if (pet.getToyId() == 3) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_3);
                            } else if (pet.getToyId() == 4) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_4);
                            } else if (pet.getToyId() == 5) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_5);
                            } else if (pet.getToyId() == 6) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_6);
                            } else if (pet.getToyId() == 7) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_7);
                            } else if (pet.getToyId() == 8) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_8);
                            } else if (pet.getToyId() == 9) {
                                brinquedo.setVisibility(View.VISIBLE);
                                brinquedo.setImageResource(R.drawable.brinquedo_personalizado_9);
                            }
                            if (pet.getHatId() == 1) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_1);
                            } else if (pet.getHatId() == 2) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_2);
                            } else if (pet.getHatId() == 3) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_3);
                            } else if (pet.getHatId() == 4) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_4);
                            } else if (pet.getHatId() == 5) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_5);
                            } else if (pet.getHatId() == 6) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_6);
                            } else if (pet.getHatId() == 7) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_7);
                            } else if (pet.getHatId() == 8) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_8);
                            } else if (pet.getHatId() == 9) {
                                cabeca.setVisibility(View.VISIBLE);
                                cabeca.setImageResource(R.drawable.cabeca_personalizado_9);
                            }
                        }

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
        public void setarDropDowns () {
            // Espécie
            List<String> especiesList = new ArrayList<>();
            especiesList.add("Cachorro");
            especiesList.add("Gato");
            ArrayAdapter<String> adapterEspecie = new ArrayAdapter<>(
                    EditarPerfilPet.this,
                    android.R.layout.simple_dropdown_item_1line,
                    especiesList
            );
            especie.setAdapter(adapterEspecie);
            especie.setThreshold(1);

            // Porte
            List<String> porteList = new ArrayList<>();
            porteList.add("Grande");
            porteList.add("Médio");
            porteList.add("Pequeno");

            ArrayAdapter<String> adapterPorte = new ArrayAdapter<>(
                    EditarPerfilPet.this,
                    android.R.layout.simple_dropdown_item_1line,
                    porteList
            );
            porte.setAdapter(adapterPorte);
            porte.setThreshold(1);

            // Cor
            APIPets apiPets = retrofit1.create(APIPets.class);
            Call<List<Cor>> call = apiPets.getAllColors();
            call.enqueue(new Callback<List<Cor>>() {
                @Override
                public void onResponse(Call<List<Cor>> call, Response<List<Cor>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Cor> corList = response.body();
                        List<String> corNomes = new ArrayList<>();

                        for (Cor cor : corList) {
                            corNomes.add(cor.color);
                        }

                        ArrayAdapter<String> adapterCor = new ArrayAdapter<>(
                                EditarPerfilPet.this,
                                android.R.layout.simple_dropdown_item_1line,
                                corNomes
                        );
                        cor.setAdapter(adapterCor);
                        cor.setThreshold(1);
                    }
                }

                @Override
                public void onFailure(Call<List<Cor>> call, Throwable throwable) {
                    Toast.makeText(EditarPerfilPet.this, "Erro ao carregar Cores", Toast.LENGTH_SHORT).show();
                    Log.e("CadastrarPet", "Erro ao carregar Cores", throwable);
                }
            });

            // Raça
            Call<List<Raca>> callRaca = apiPets.getAllRaces();
            callRaca.enqueue(new Callback<List<Raca>>() {
                @Override
                public void onResponse(Call<List<Raca>> call, Response<List<Raca>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Raca> racaList = response.body();
                        List<String> racaNomes = new ArrayList<>();

                        for (Raca raca : racaList) {
                            racaNomes.add(raca.race);
                        }

                        ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(
                                EditarPerfilPet.this,
                                android.R.layout.simple_dropdown_item_1line,
                                racaNomes
                        );
                        raca.setAdapter(adapterRaca);
                        raca.setThreshold(1);
                    }
                }

                @Override
                public void onFailure(Call<List<Raca>> call, Throwable throwable) {
                    Toast.makeText(EditarPerfilPet.this, "Erro ao carregar Raças", Toast.LENGTH_SHORT).show();
                    Log.e("CadastrarPet", "Erro ao carregar Raças", throwable);
                }
            });
            //genero
            List<String> generoList = new ArrayList<>();
            generoList.add("Macho");
            generoList.add("Fêmea");
            ArrayAdapter<String> adapterGenero = new ArrayAdapter<>(
                    EditarPerfilPet.this,
                    android.R.layout.simple_dropdown_item_1line,
                    generoList
            );
            genero.setAdapter(adapterGenero);
            genero.setThreshold(1);
        }

        private void update () {
            APIPets api1 = retrofit1.create(APIPets.class);
            APIPets api2 = retrofit2.create(APIPets.class);


            String g = "F";
            if (genero.getText().toString().equals("Masculino")) {
                g = "M";
            } else if (genero.getText().toString().equals("Feminino")) {
                g = "F";
            }


//            {
//                "idPet":146,
//                    "nickname": "TESTE2",
//                    "age": 3,
//                    "sex": "M",
//                    "specie": "Cachorro",
//                    "race": "Shih Tzu",
//                    "size": "Pequeno",
//                    "color": "Branco e Marrom"
//            }
            // Cria o objeto do pet com os dados fornecidos
            ModelPetBanco pet = new ModelPetBanco(
                    nome.getText().toString(),
                    Integer.parseInt(idade.getText().toString()),
                    g,
                    especie.getText().toString(),
                    raca.getText().toString(),
                    porte.getText().toString(),
                    cor.getText().toString(),
                    id
            );

            // Faz a chamada à API para inserir o pet
            // Chamar API para setar os drops downs

            setarDropDowns();

            Call<ModelRetorno> call = api1.updatePet(pet);
            call.enqueue(new Callback<ModelRetorno>() {
                @Override
                public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                    // Verifica se a resposta da API é bem-sucedida
                    if (response.isSuccessful() && response.body() != null) {

                        Personalizacao petPersonalizado = new Personalizacao(
                                id,
                                especie.getText().toString(),  // Pode ser modificado conforme a lógica do seu app
                                cabeca_pet,
                                0,
                                brinquedo_pet,
                                oculos_pet
                        );

                        // Chama a API para personalizar o pet
                        Call<ModelRetorno> callPersonalizacao = api2.personalizarPet(petPersonalizado);
                        callPersonalizacao.enqueue(new Callback<ModelRetorno>() {
                            @Override
                            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(EditarPerfilPet.this, "Pet cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Log.e("Personalizacao", "Falha ao personalizar pet: " + response.code() + " - " + response.message());
                                    Toast.makeText(EditarPerfilPet.this, "Falha ao personalizar o pet, tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ModelRetorno> call, Throwable t) {
                                Log.e("Personalizacao", "Erro: " + t.getMessage());
                                Toast.makeText(EditarPerfilPet.this, "Erro ao tentar personalizar o pet.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Toast.makeText(EditarPerfilPet.this, "Pet editado!", Toast.LENGTH_SHORT).show();


                    } else {
                        Log.e("Cadastro", "Falha no cadastro: " + response.code() + " - " + response.message());
                        Toast.makeText(EditarPerfilPet.this, "Falha no update, tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelRetorno> call, Throwable t) {
                    Log.e("Cadastro", "Erro: " + t.getMessage());
                    Toast.makeText(EditarPerfilPet.this, "Erro ao tentar cadastrar o pet.", Toast.LENGTH_SHORT).show();
                }
            });
        }


}
