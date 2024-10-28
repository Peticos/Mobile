package com.mobile.peticos.Vakinhas;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterAdicionarVakinha extends RecyclerView.Adapter<AdapterAdicionarVakinha.PetViewHolder> {

    private List<ModelPetBanco> petsList;


    public AdapterAdicionarVakinha(List<ModelPetBanco> petsList) {
        this.petsList = petsList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_vakinha, parent, false);
        return new PetViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        ModelPetBanco Pet = petsList.get(position);
        holder.nome.setText(Pet.getNickname());

        // Recupera o SharedPreferences
        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("PetTriste", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Certifique-se de usar a mesma chave 'selectedPet'
        String id = sharedPreferences.getString("selectedPet", null);
        if (id != null && id.equals(String.valueOf(Pet.getIdPet()))) {
            holder.cancelarPet.setVisibility(View.VISIBLE);
        } else {
            holder.cancelarPet.setVisibility(View.INVISIBLE);
        }


        // Evento de clique para selecionar o pet
        holder.tudo.setOnClickListener(v -> {
            String idPet = String.valueOf(Pet.getIdPet());
            editor.putString("selectedPet", idPet); // Corrigido: usar 'selectedPet' como chave
            editor.putString("nome",Pet.getNickname() );
            editor.apply();

            // Atualizar a exibição após selecionar o pet
            notifyDataSetChanged();
        });

        // Evento de clique para cancelar a seleção do pet
        holder.cancelarPet.setOnClickListener(v -> {
            editor.putString("selectedPet", "0"); // Corrigido: limpar 'selectedPet'
            editor.apply();

            // Atualizar a exibição após desmarcar o pet
            holder.cancelarPet.setVisibility(View.INVISIBLE);
            notifyDataSetChanged();
        });
        String API = "https://api-mongo-i1jq.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPets apiPets = retrofit.create(APIPets.class);
        Call<Personalizacao> call = apiPets.getPersonalizacao(Pet.getIdPet());
        call.enqueue(new Callback<Personalizacao>() {
            @Override
            public void onResponse(Call<Personalizacao> call, Response<Personalizacao> response) {
                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        Personalizacao pet = response.body();

                        if(pet.getSpecies().equals("Cachorro")) {

                            if(pet.getGlassesId() == 1) {
                                holder.oculosao_dog.setVisibility(View.VISIBLE);
                                holder.oculosao_dog.setImageResource(R.drawable.oculos_personalizado_1);
                            } else if (pet.getGlassesId()==2) {
                                holder.oculosao_dog.setVisibility(View.VISIBLE);
                                holder.oculosao_dog.setImageResource(R.drawable.oculos_personalizado_2);
                            }  else if (pet.getGlassesId()==3) {
                                holder.oculosao_dog.setVisibility(View.VISIBLE);
                                holder.oculosao_dog.setImageResource(R.drawable.oculos_personalizado_3);
                            } else if (pet.getGlassesId()==4) {
                                holder.oculosao_dog.setVisibility(View.VISIBLE);
                                holder.oculosao_dog.setImageResource(R.drawable.oculos_personalizado_4);
                            } else if (pet.getGlassesId()==5) {
                                holder.oculosao_dog.setVisibility(View.VISIBLE);
                                holder.oculosao_dog.setImageResource(R.drawable.oculos_personalizado_5);
                            } else if (pet.getGlassesId()==6) {
                                holder.oculosao_dog.setVisibility(View.VISIBLE);
                                holder.oculosao_dog.setImageResource(R.drawable.oculos_personalizado_6);
                            } else if (pet.getGlassesId()==7) {
                                holder.oculosao_dog.setVisibility(View.VISIBLE);
                                holder.oculosao_dog.setImageResource(R.drawable.oculos_personalizado_7);
                            }
                            holder.petzao.setVisibility(View.VISIBLE);
                            if(pet.getHairId() == 1) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_1);
                            }else if (pet.getHairId()==2) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_2);
                            }else if (pet.getHairId()==3) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_3);
                            }else if (pet.getHairId()==4) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_4);
                            }else if (pet.getHairId()==5) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_5);
                            }else if (pet.getHairId()==6) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_6);
                            }else if (pet.getHairId()==7) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_7);
                            }else if(pet.getHairId()==8) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_8);
                            }else if(pet.getHairId()==9) {
                                holder.petzao.setImageResource(R.drawable.dog_personalizado_9);
                            }

                        } else if(pet.getSpecies().equals("Gato")) {
                            if(pet.getGlassesId() == 1) {
                                holder.oculosao_cat.setVisibility(View.VISIBLE);
                                holder.oculosao_cat.setImageResource(R.drawable.oculos_personalizado_1);
                            } else if (pet.getGlassesId()==2) {
                                holder.oculosao_cat.setImageResource(R.drawable.oculos_personalizado_2);
                            }  else if (pet.getGlassesId()==3) {
                                holder.oculosao_cat.setImageResource(R.drawable.oculos_personalizado_3);
                            } else if (pet.getGlassesId()==4) {
                                holder.oculosao_cat.setImageResource(R.drawable.oculos_personalizado_4);
                            } else if (pet.getGlassesId()==5) {
                                holder.oculosao_cat.setImageResource(R.drawable.oculos_personalizado_5);
                            } else if (pet.getGlassesId()==6) {
                                holder.oculosao_cat.setImageResource(R.drawable.oculos_personalizado_6);
                            } else if (pet.getGlassesId()==7) {
                                holder.oculosao_cat.setImageResource(R.drawable.oculos_personalizado_7);
                            }
                            holder.petzao.setVisibility(View.VISIBLE);
                            if(pet.getHairId() == 1 || pet.getHairId() == 0) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_1);
                            }else if (pet.getHairId()==2) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_2);
                            }else if (pet.getHairId()==3) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_3);
                            }else if (pet.getHairId()==4) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_4);
                            }else if (pet.getHairId()==5) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_5);
                            }else if (pet.getHairId()==6) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_6);
                            }else if (pet.getHairId()==7) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_7);
                            }else if(pet.getHairId()==8) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_8);
                            }else if(pet.getHairId()==9) {
                                holder.petzao.setImageResource(R.drawable.cat_personalizado_9);
                            }

                        }
                        if(pet.getToyId() == 1) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_1);
                        }  else if(pet.getToyId() == 2) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_2);
                        }  else if(pet.getToyId() == 3) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_3);
                        } else if(pet.getToyId() == 4) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_4);
                        } else if(pet.getToyId() == 5) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_5);
                        } else if(pet.getToyId() == 6) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_6);
                        } else if(pet.getToyId() == 7) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_7);
                        }  else if(pet.getToyId() == 8) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_8);
                        }  else if(pet.getToyId() == 9) {
                            holder.brinquedao.setVisibility(View.VISIBLE);
                            holder.brinquedao.setImageResource(R.drawable.brinquedo_personalizado_9);
                        }
                        if(pet.getHatId() == 1) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_1);
                        } else if(pet.getHatId() == 2) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_2);
                        } else if(pet.getHatId() == 3) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_3);
                        } else if(pet.getHatId() == 4) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_4);
                        } else if(pet.getHatId() == 5) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_5);
                        } else if(pet.getHatId() == 6) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_6);
                        } else if(pet.getHatId() == 7) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_7);
                        } else if(pet.getHatId() == 8) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_8);
                        } else if(pet.getHatId() == 9) {
                            holder.cabecao.setVisibility(View.VISIBLE);
                            holder.cabecao.setImageResource(R.drawable.cabeca_personalizado_9);
                        }}

                } else {
                    holder.petzao.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Personalizacao> call, Throwable t) {
                holder.petzao.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public int getItemCount() {
        return petsList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView nome;
        ImageView imagem, cancelarPet;
        LinearLayout tudo;
        String perdido;
        ImageView petzao, cabecao, brinquedao, oculosao_dog, oculosao_cat;



        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomepet);

            tudo = itemView.findViewById(R.id.tudo);
            cancelarPet = itemView.findViewById(R.id.cancelarPet);
            petzao = itemView.findViewById(R.id.petzao);
            cabecao =  itemView.findViewById(R.id.cabecao);
            brinquedao =  itemView.findViewById(R.id.brinquedao);
            oculosao_dog =  itemView.findViewById(R.id.oculosao_dog);
            oculosao_cat =  itemView.findViewById(R.id.oculosao_cat);


        }
    }
}
