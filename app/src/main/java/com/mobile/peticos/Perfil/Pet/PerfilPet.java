package com.mobile.peticos.Perfil.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.Perfil.Pet.Vacinas.VacinasPets;
import com.mobile.peticos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.mobile.peticos.Perfil.Pet.API.APIPets;

public class PerfilPet extends AppCompatActivity {
    TextView NomePet, sexoPet, idadePet, especiePet, racaPet, corPet, portePet, NomePet2;
    ImageView btnvoltar, btn_editar, petzao, cabeca, oculos_dog, oculos_cat, brinquedo, especie, btn_personalizar;
    int id;
    CardView Vacinas, Peso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_pet);

        NomePet = findViewById(R.id.NomePetDois);
        NomePet2 = findViewById(R.id.NomePet);
        sexoPet = findViewById(R.id.sexoPet);
        idadePet = findViewById(R.id.IdadePet);
        racaPet = findViewById(R.id.RacaPet);
        corPet = findViewById(R.id.CorPet);
        portePet = findViewById(R.id.PortPet);
        idadePet = findViewById(R.id.IdadePet);
        btnvoltar = findViewById(R.id.btnvoltar);
        btn_editar = findViewById(R.id.btn_editar);
        btn_personalizar = findViewById(R.id.btn_personalizar);
        Vacinas = findViewById(R.id.Vacinas);
        Peso = findViewById(R.id.Peso);

        brinquedo = findViewById(R.id.brinquedao);
        oculos_dog = findViewById(R.id.oculosao_dog);
        oculos_cat = findViewById(R.id.oculosao_cat);
        petzao = findViewById(R.id.petzao);
        cabeca = findViewById(R.id.cabecao);

        oculos_cat.setVisibility(View.INVISIBLE);
        oculos_dog.setVisibility(View.INVISIBLE);
        brinquedo.setVisibility(View.INVISIBLE);
        petzao.setVisibility(View.INVISIBLE);
        cabeca.setVisibility(View.INVISIBLE);


        SharedPreferences sharedPreferences = getSharedPreferences("Pet", MODE_PRIVATE);

        NomePet.setText(sharedPreferences.getString("nickname", "nome do pet"));
        NomePet2.setText(sharedPreferences.getString("nickname", "nome do pet"));
        if(sharedPreferences.getString("genero", "genero").equals("M")){
            sexoPet.setText("Macho");
        } else if (sharedPreferences.getString("genero", "genero").equals("F")) {
            sexoPet.setText("Fêmea");
        }
        idadePet.setText(sharedPreferences.getInt("idade", 0) + " anos");
        racaPet.setText(sharedPreferences.getString("raca", "raca"));
        corPet.setText(sharedPreferences.getString("cor", "cor"));
        portePet.setText(sharedPreferences.getString("porte", "porte"));
        id = sharedPreferences.getInt("id", 0);
        avatarPet(sharedPreferences);





        btnvoltar.setOnClickListener(v -> {
            finish();
        });
        btn_editar.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), EditarPerfilPet.class);
            v.getContext().startActivity(intent);


        });
        btn_personalizar.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), PersonalizarPets.class);
            v.getContext().startActivity(intent);


        });
        Vacinas.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), VacinasPets.class);
            v.getContext().startActivity(intent);


        });
        Peso.setOnClickListener(v->{
            Intent intent = new Intent(v.getContext(), PesoPets.class);
            v.getContext().startActivity(intent);

        });


    }
    private void avatarPet(SharedPreferences sharedPreferences) {

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
                        Log.e("teste", pet.getHairId() + " hat" + pet.getHatId() + "glasses " + pet.getGlassesId() + " toy" + pet.getToyId());

                        if(pet.getSpecies().equals("Cachorro")) {

                            if(pet.getGlassesId() == 1) {
                                oculos_dog.setVisibility(View.VISIBLE);
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_1);
                            } else if (pet.getGlassesId()==2) {
                                oculos_dog.setVisibility(View.VISIBLE);
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_2);
                            }  else if (pet.getGlassesId()==3) {
                                oculos_dog.setVisibility(View.VISIBLE);
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_3);
                            } else if (pet.getGlassesId()==4) {
                                oculos_dog.setVisibility(View.VISIBLE);
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_4);
                            } else if (pet.getGlassesId()==5) {
                                oculos_dog.setVisibility(View.VISIBLE);
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_5);
                            } else if (pet.getGlassesId()==6) {
                                oculos_dog.setVisibility(View.VISIBLE);
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_6);
                            } else if (pet.getGlassesId()==7) {
                                oculos_dog.setVisibility(View.VISIBLE);
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_7);
                            }
                            petzao.setVisibility(View.VISIBLE);
                            if(pet.getHairId() == 1) {
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
                                oculos_cat.setVisibility(View.VISIBLE);
                                oculos_cat.setImageResource(R.drawable.oculos_personalizado_1);
                            } else if (pet.getGlassesId()==2) {
                                oculos_cat.setVisibility(View.VISIBLE);
                                oculos_cat.setImageResource(R.drawable.oculos_personalizado_2);
                            }  else if (pet.getGlassesId()==3) {
                                oculos_cat.setVisibility(View.VISIBLE);
                                oculos_cat.setImageResource(R.drawable.oculos_personalizado_3);
                            } else if (pet.getGlassesId()==4) {
                                oculos_cat.setVisibility(View.VISIBLE);
                                oculos_cat.setImageResource(R.drawable.oculos_personalizado_4);
                            } else if (pet.getGlassesId()==5) {
                                oculos_cat.setVisibility(View.VISIBLE);
                                oculos_cat.setImageResource(R.drawable.oculos_personalizado_5);
                            } else if (pet.getGlassesId()==6) {
                                oculos_cat.setVisibility(View.VISIBLE);
                                oculos_cat.setImageResource(R.drawable.oculos_personalizado_6);
                            } else if (pet.getGlassesId()==7) {
                                oculos_cat.setVisibility(View.VISIBLE);
                                oculos_cat.setImageResource(R.drawable.oculos_personalizado_7);
                            }

                            if(pet.getHairId() == 1 || pet.getHatId() == 0) {
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
                            petzao.setVisibility(View.VISIBLE);

                        }
                        if(pet.getToyId() == 1) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_1);
                        }  else if(pet.getToyId() == 2) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_2);
                        }  else if(pet.getToyId() == 3) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_3);
                        } else if(pet.getToyId() == 4) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_4);
                        } else if(pet.getToyId() == 5) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_5);
                        } else if(pet.getToyId() == 6) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_6);
                        } else if(pet.getToyId() == 7) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_7);
                        }  else if(pet.getToyId() == 8) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_8);
                        }  else if(pet.getToyId() == 9) {
                            brinquedo.setVisibility(View.VISIBLE);
                            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_9);
                        }
                        if(pet.getHatId() == 1) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_1);
                        } else if(pet.getHatId() == 2) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_2);
                        } else if(pet.getHatId() == 3) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_3);
                        } else if(pet.getHatId() == 4) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_4);
                        } else if(pet.getHatId() == 5) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_5);
                        } else if(pet.getHatId() == 6) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_6);
                        } else if(pet.getHatId() == 7) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_7);
                        } else if(pet.getHatId() == 8) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_8);
                        } else if(pet.getHatId() == 9) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_9);
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
}