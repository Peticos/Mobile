package com.mobile.peticos.Perfil.Tutor.AdapterPets;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Login;
import com.mobile.peticos.MainActivity;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;

import com.mobile.peticos.Perfil.Pet.PerfilPet;
import com.mobile.peticos.R;
import java.util.List;

public class AdapterPet extends RecyclerView.Adapter<AdapterPet.PetViewHolder> {

    private List<ModelPetBanco> petsList;

    public AdapterPet(List<ModelPetBanco> petsList) {
        this.petsList = petsList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        ModelPetBanco Pet = petsList.get(position);
        holder.textViewNome.setText(Pet.getNickname());
        // Configurar o clique
        holder.itemView.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("Pet", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            // Armazenar todas as informações no SharedPreferences
            editor.putString("nickname", Pet.getNickname());
            editor.putInt("idade", Pet.getAge());
            editor.putString("especie", Pet.getSpecie());
            editor.putString("raca", Pet.getRace());
            editor.putString("cor", Pet.getColorpet());
            editor.putString("porte", Pet.getSize());
            editor.putString("genero", Pet.getSex());
            editor.putInt("id", Pet.getIdPet());
            editor.apply();
            Toast.makeText(v.getContext(), "Pet", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent( v.getContext(), PerfilPet.class);
            v.getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return petsList.size();
    }

    public static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome;
        ImageView imageView;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.nomepet);
            imageView = itemView.findViewById(R.id.fotopet);


        }
    }
}

