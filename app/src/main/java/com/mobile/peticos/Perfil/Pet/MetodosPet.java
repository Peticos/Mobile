package com.mobile.peticos.Perfil.Pet;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.ImageView;

import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.R;

public class MetodosPet {
    public void avatarPet(int id, ImageView oculos_dog, ImageView petzao, ImageView oculos_cat, ImageView brinquedo, ImageView cabeca, SharedPreferences pets, SharedPreferences pet) {

        String especie = pet.getString("especie", "Cachorro");

        int cor = pets.getInt("cor", 1);
        int oculosshar = pets.getInt("oculos", 0);
        int brinquedoshar = pets.getInt("brinquedo", 0);
        int cabecashar = pets.getInt("cabeca", 0);





        if(especie.equals("Cachorro")) {

            if(oculosshar == 1) {
                oculos_dog.setVisibility(View.VISIBLE);
                oculos_dog.setImageResource(R.drawable.oculos_personalizado_1);
            } else if (oculosshar==2) {
                oculos_dog.setVisibility(View.VISIBLE);
                oculos_dog.setImageResource(R.drawable.oculos_personalizado_2);
            }  else if (oculosshar==3) {
                oculos_dog.setVisibility(View.VISIBLE);
                oculos_dog.setImageResource(R.drawable.oculos_personalizado_3);
            } else if (oculosshar==4) {
                oculos_dog.setVisibility(View.VISIBLE);
                oculos_dog.setImageResource(R.drawable.oculos_personalizado_4);
            } else if (oculosshar==5) {
                oculos_dog.setVisibility(View.VISIBLE);
                oculos_dog.setImageResource(R.drawable.oculos_personalizado_5);
            } else if (oculosshar==6) {
                oculos_dog.setVisibility(View.VISIBLE);
                oculos_dog.setImageResource(R.drawable.oculos_personalizado_6);
            } else if (oculosshar==7) {
                oculos_dog.setVisibility(View.VISIBLE);
                oculos_dog.setImageResource(R.drawable.oculos_personalizado_7);
            }

            if(cor == 1) {
                petzao.setImageResource(R.drawable.dog_personalizado_1);
            }else if (cor==2) {
                petzao.setImageResource(R.drawable.dog_personalizado_2);
            }else if (cor==3) {
                petzao.setImageResource(R.drawable.dog_personalizado_3);
            }else if (cor==4) {
                petzao.setImageResource(R.drawable.dog_personalizado_4);
            }else if (cor==5) {
                petzao.setImageResource(R.drawable.dog_personalizado_5);
            }else if (cor==6) {
                petzao.setImageResource(R.drawable.dog_personalizado_6);
            }else if (cor==7) {
                petzao.setImageResource(R.drawable.dog_personalizado_7);
            }else if(cor==8) {
                petzao.setImageResource(R.drawable.dog_personalizado_8);
            }else if(cor==9) {
                petzao.setImageResource(R.drawable.dog_personalizado_9);
            }

        } else if(especie.equals("Gato")) {
            if(oculosshar == 1) {
                oculos_cat.setVisibility(View.VISIBLE);
                oculos_cat.setImageResource(R.drawable.oculos_personalizado_1);
            } else if (oculosshar ==2) {
                oculos_cat.setVisibility(View.VISIBLE);
                oculos_cat.setImageResource(R.drawable.oculos_personalizado_2);
            }  else if (oculosshar==3) {
                oculos_cat.setVisibility(View.VISIBLE);
                oculos_cat.setImageResource(R.drawable.oculos_personalizado_3);
            } else if (oculosshar==4) {
                oculos_cat.setVisibility(View.VISIBLE);
                oculos_cat.setImageResource(R.drawable.oculos_personalizado_4);
            } else if (oculosshar==5) {
                oculos_cat.setVisibility(View.VISIBLE);
                oculos_cat.setImageResource(R.drawable.oculos_personalizado_5);
            } else if (oculosshar==6) {
                oculos_cat.setVisibility(View.VISIBLE);
                oculos_cat.setImageResource(R.drawable.oculos_personalizado_6);
            } else if (oculosshar==7) {
                oculos_cat.setVisibility(View.VISIBLE);
                oculos_cat.setImageResource(R.drawable.oculos_personalizado_7);
            }

            if(cor == 1 || cor == 0) {
                petzao.setImageResource(R.drawable.cat_personalizado_1);
            }else if (cor==2) {
                petzao.setImageResource(R.drawable.cat_personalizado_2);
            }else if (cor==3) {
                petzao.setImageResource(R.drawable.cat_personalizado_3);
            }else if (cor==4) {
                petzao.setImageResource(R.drawable.cat_personalizado_4);
            }else if (cor==5) {
                petzao.setImageResource(R.drawable.cat_personalizado_5);
            }else if (cor==6) {
                petzao.setImageResource(R.drawable.cat_personalizado_6);
            }else if (cor==7) {
                petzao.setImageResource(R.drawable.cat_personalizado_7);
            }else if(cor==8) {
                petzao.setImageResource(R.drawable.cat_personalizado_8);
            }else if(cor==9) {
                petzao.setImageResource(R.drawable.cat_personalizado_9);
            }

        }
        if(brinquedoshar == 1) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_1);
        }  else if(brinquedoshar == 2) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_2);
        }  else if(brinquedoshar == 3) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_3);
        } else if(brinquedoshar == 4) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_4);
        } else if(brinquedoshar == 5) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_5);
        } else if(brinquedoshar == 6) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_6);
        } else if(brinquedoshar == 7) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_7);
        }  else if(brinquedoshar == 8) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_8);
        }  else if(brinquedoshar == 9) {
            brinquedo.setVisibility(View.VISIBLE);
            brinquedo.setImageResource(R.drawable.brinquedo_personalizado_9);
        }
        if(cabecashar == 1) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_1);
        } else if(cabecashar == 2) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_2);
        } else if(cabecashar == 3) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_3);
        } else if(cabecashar == 4) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_4);
        } else if(cabecashar == 5) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_5);
        } else if(cabecashar == 6) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_6);
        } else if(cabecashar == 7) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_7);
        } else if(cabecashar == 8) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_8);
        } else if(cabecashar == 9) {
            cabeca.setVisibility(View.VISIBLE);
            cabeca.setImageResource(R.drawable.cabeca_personalizado_9);
        }
    }


}
