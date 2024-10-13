package com.mobile.peticos.Perfil.Tutor.AdapterPets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.peticos.Perfil.Pet.Apis.ModelPetBanco;

import com.mobile.peticos.PerfilPet;
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

            Intent intent = new Intent(v.getContext(), PerfilPet.class);
            Bundle bundle = new Bundle();
            bundle.putString("nickname", Pet.getNickname());
            bundle.putInt("idade", Pet.getAge());
            bundle.putString("especie", Pet.getSpecie());
            bundle.putString("raca", Pet.getRace());
            bundle.putString("cor", Pet.getColorpet());
            bundle.putString("porte", Pet.getSize());
            bundle.putString("genero", Pet.getSex());
            intent.putExtras(bundle);
            v.getContext().startActivity(intent);

        });


//        holder.textViewDescricao.setText(local.getDescricao());
//        holder.imageView.setImageResource(local.getImagem());
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

//            textViewDescricao = itemView.findViewById(R.id.textView32);
//            imageView = itemView.findViewById(R.id.fotoPerfil);
        }
    }
}

