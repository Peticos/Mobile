package com.mobile.peticos.Perdidos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdapterPetFeedTriste extends RecyclerView.Adapter<AdapterPetFeedTriste.PetViewHolder> {

    private List<ModelPetBanco> petsList;


    public AdapterPetFeedTriste(List<ModelPetBanco> petsList) {
        this.petsList = petsList;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_perdido, parent, false);
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



        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomepet);
            imagem = itemView.findViewById(R.id.fotopet);
            tudo = itemView.findViewById(R.id.tudo);
            cancelarPet = itemView.findViewById(R.id.cancelarPet);


        }
    }
}

