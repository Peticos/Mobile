package com.mobile.peticos.Perdidos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.peticos.R;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class AdapterPerdidos extends RecyclerView.Adapter<AdapterPerdidos.ViewHolder> {

    private final List<PetPerdido> petList;

    public AdapterPerdidos(List<PetPerdido> petList) {
        this.petList = petList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_perdidos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetPerdido pet = petList.get(position);


            // Imagem principal do pet
            Glide.with(holder.fotoPrincipal.getContext())
                    .load(pet.getPicture())
                    .into(holder.fotoPrincipal);

            // Data de perda do pet
            // data
            String lostDateString = pet.getLostDate() + "T00:00:00+00:00";
            OffsetDateTime dateTime = OffsetDateTime.parse(lostDateString);
            LocalDate dataAnterior = dateTime.toLocalDate();
            LocalDate dataAtual = LocalDate.now();
            long dias = ChronoUnit.DAYS.between(dataAnterior, dataAtual);
            if (dias == 0) {
                holder.days.setText("Hoje");
            } else if (dias == 1) {
                holder.days.setText("Ontem");
            } else {
                holder.days.setText("Há " + dias + " dias atrás");
            }


            // Descrição do pet
            holder.descricao.setText(pet.getDescription());

            // Configurar o telefone
            String numero = "123456789"; // Substitua com o número do pet
            holder.ic_telefone.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numero));
                v.getContext().startActivity(intent);
            });
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decidir oq vai colocar no compartilhar
                String postContent = "Aqui está o conteúdo do post que quero compartilhar!";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                shareIntent.putExtra(Intent.EXTRA_TEXT, postContent);

                v.getContext().startActivity(Intent.createChooser(shareIntent, "Compartilhar post via"));
            }
        });

            // Atribuição de outros campos, como nome do pet e foto do usuário (se disponível)
            // holder.username.setText(pet.getUsername());
            // Glide.with(holder.userPhoto.getContext()).load(pet.getUserPhoto()).into(holder.userPhoto);
            // holder.petsInPhoto.setText(pet.getPetsInPhoto());
            // holder.nomepet.setText(pet.getNomePet());

    }

    @Override
    public int getItemCount() {
        return petList.size() ;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userPhoto;
         ImageView fotoPrincipal;
        TextView username;
         TextView days;
         TextView petsInPhoto;
         TextView descricao;
         TextView nomepet;
         ImageView ic_telefone, shareButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userPhoto = itemView.findViewById(R.id.userPhoto);
            fotoPrincipal = itemView.findViewById(R.id.fotoPrincipal);
            username = itemView.findViewById(R.id.username);
            days = itemView.findViewById(R.id.days);
            petsInPhoto = itemView.findViewById(R.id.petsInPhoto);
            descricao = itemView.findViewById(R.id.descricaopet);
            nomepet = itemView.findViewById(R.id.nomepet2);
            ic_telefone = itemView.findViewById(R.id.ic_telefone);
            shareButton = itemView.findViewById(R.id.btn_compartilhar);
        }
    }
}
