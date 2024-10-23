package com.mobile.peticos.Perdidos;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.R;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdapterPetFeedVakinha extends RecyclerView.Adapter<AdapterPetFeedVakinha.PetViewHolder> {

    private List<ModelPetBanco> petsList;

    public AdapterPetFeedVakinha(List<ModelPetBanco> petsList) {
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
        holder.tudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Supondo que você tenha um método getId() em seu modelo
                String idPet = String.valueOf(Pet.getIdPet());

                // Recupera o SharedPreferences
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("PetCache", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                // Recupera a lista atual de IDs, ou cria uma nova se não existir
                Set<String> selectedPets = sharedPreferences.getStringSet("selectedPets", new HashSet<>());

                // Verifica se o ID já existe na lista
                if (!selectedPets.contains(idPet)) {
                    // Adiciona o ID do pet à lista
                    selectedPets.add(idPet);

                    // Armazena a lista de volta
                    editor.putStringSet("selectedPets", selectedPets);
                    editor.apply();

                    holder.cancelarPet.setVisibility(View.VISIBLE);
                    Toast.makeText(v.getContext(), "Pet " + idPet + " selecionado!", Toast.LENGTH_SHORT).show();
                } else {
                    // Se o ID já existe, você pode mostrar uma mensagem
                    Toast.makeText(v.getContext(), "Pet " + idPet + " já está selecionado!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.cancelarPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPet = String.valueOf(Pet.getIdPet());
                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("PetCache", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> selectedPets = sharedPreferences.getStringSet("selectedPets", new HashSet<>());
                selectedPets.remove(idPet); // Remove se já estiver na lista
                Toast.makeText(v.getContext(), "Pet " + idPet + " removido!", Toast.LENGTH_SHORT).show();
                editor.putStringSet("selectedPets", selectedPets);
                editor.apply();
                holder.cancelarPet.setVisibility(View.INVISIBLE);

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



        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nomepet);
            imagem = itemView.findViewById(R.id.fotopet);
            tudo = itemView.findViewById(R.id.tudo);
            cancelarPet = itemView.findViewById(R.id.cancelarPet);


        }
    }
}

