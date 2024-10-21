package com.mobile.peticos.Perfil.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.video.VideoRecordEvent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.mobile.peticos.Perfil.Pet.API.APIPets;

public class EditarPerfilPet extends AppCompatActivity {
    ImageView btnVoltarEditar, btnEdit, petzao, cabeca, oculos_dog, oculos_cat, brinquedo, especie;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_pet);
        Bundle bundle = getIntent().getExtras();
        brinquedo = findViewById(R.id.brinquedao);
        oculos_dog = findViewById(R.id.oculosao_dog);
        oculos_cat = findViewById(R.id.oculosao_cat);
        petzao = findViewById(R.id.petzao);
        cabeca = findViewById(R.id.cabecao);

        oculos_cat.setVisibility(View.INVISIBLE);
        oculos_dog.setVisibility(View.INVISIBLE);
        brinquedo.setVisibility(View.INVISIBLE);

        cabeca.setVisibility(View.INVISIBLE);
        id = bundle.getInt("id");

        avatarPet();


        btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), PersonalizarPets.class);
            Bundle bundleeditar = new Bundle();
            bundleeditar.putInt("id", id);
            intent.putExtras(bundleeditar);
            v.getContext().startActivity(intent);
        });

        btnVoltarEditar = findViewById(R.id.btn_voltar_perfil);
        btnVoltarEditar.setOnClickListener(v -> {

            finish();
        });
    }

    private void avatarPet() {

        String API = "https://apimongo-ghjh.onrender.com";
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
                        if(pet.getSpecies().equals("CACHORRO")) {
                            oculos_dog.setVisibility(View.VISIBLE);
                            if(pet.getGlassesId() == 1) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_1);
                            } else if (pet.getGlassesId()==2) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_2);
                            }  else if (pet.getGlassesId()==3) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_3);
                            } else if (pet.getGlassesId()==4) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_4);
                            } else if (pet.getGlassesId()==5) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_5);
                            } else if (pet.getGlassesId()==6) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_6);
                            } else if (pet.getGlassesId()==7) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_7);
                            }
                            petzao.setVisibility(View.VISIBLE);
                            if(pet.getHatId() == 1) {
                                petzao.setImageResource(R.drawable.dog_personalizado_1);
                            }else if (pet.getHatId()==2) {
                                petzao.setImageResource(R.drawable.dog_personalizado_2);
                            }else if (pet.getHatId()==3) {
                                petzao.setImageResource(R.drawable.dog_personalizado_3);
                            }else if (pet.getHatId()==4) {
                                petzao.setImageResource(R.drawable.dog_personalizado_4);
                            }else if (pet.getHatId()==5) {
                                petzao.setImageResource(R.drawable.dog_personalizado_5);
                            }else if (pet.getHatId()==6) {
                                petzao.setImageResource(R.drawable.dog_personalizado_6);
                            }else if (pet.getHatId()==7) {
                                petzao.setImageResource(R.drawable.dog_personalizado_7);
                            }else if(pet.getHatId()==8) {
                                petzao.setImageResource(R.drawable.dog_personalizado_8);
                            }else if(pet.getHatId()==9) {
                                petzao.setImageResource(R.drawable.dog_personalizado_9);
                            }

                        } else if(pet.getSpecies().equals("GATO")) {
                            oculos_cat.setVisibility(View.VISIBLE);
                            if(pet.getGlassesId() == 1) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_1);
                            } else if (pet.getGlassesId()==2) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_2);
                            }  else if (pet.getGlassesId()==3) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_3);
                            } else if (pet.getGlassesId()==4) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_4);
                            } else if (pet.getGlassesId()==5) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_5);
                            } else if (pet.getGlassesId()==6) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_6);
                            } else if (pet.getGlassesId()==7) {
                                oculos_dog.setImageResource(R.drawable.oculos_personalizado_7);
                            }
                            petzao.setVisibility(View.VISIBLE);
                            if(pet.getHatId() == 1) {
                                petzao.setImageResource(R.drawable.cat_personalizado_1);
                            }else if (pet.getHatId()==2) {
                                petzao.setImageResource(R.drawable.cat_personalizado_2);
                            }else if (pet.getHatId()==3) {
                                petzao.setImageResource(R.drawable.cat_personalizado_3);
                            }else if (pet.getHatId()==4) {
                                petzao.setImageResource(R.drawable.cat_personalizado_4);
                            }else if (pet.getHatId()==5) {
                                petzao.setImageResource(R.drawable.cat_personalizado_5);
                            }else if (pet.getHatId()==6) {
                                petzao.setImageResource(R.drawable.cat_personalizado_6);
                            }else if (pet.getHatId()==7) {
                                petzao.setImageResource(R.drawable.cat_personalizado_7);
                            }else if(pet.getHatId()==8) {
                                petzao.setImageResource(R.drawable.cat_personalizado_8);
                            }else if(pet.getHatId()==9) {
                                petzao.setImageResource(R.drawable.cat_personalizado_9);
                            }

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
                        if(pet.getHairId() == 1) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_1);
                        } else if(pet.getHairId() == 2) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_2);
                        } else if(pet.getHairId() == 3) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_3);
                        } else if(pet.getHairId() == 4) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_4);
                        } else if(pet.getHairId() == 5) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_5);
                        } else if(pet.getHairId() == 6) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_6);
                        } else if(pet.getHairId() == 7) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_7);
                        } else if(pet.getHairId() == 8) {
                            cabeca.setVisibility(View.VISIBLE);
                            cabeca.setImageResource(R.drawable.cabeca_personalizado_8);
                        } else if(pet.getHairId() == 9) {
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
