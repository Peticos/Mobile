package com.mobile.peticos.Home;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.mobile.peticos.R;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FeedPetsAdapter extends RecyclerView.Adapter<FeedPetsAdapter.FeedPetsViewHolder> {

    private List<FeedPet> feedList;

    public FeedPetsAdapter(List<FeedPet> feedList) {
        this.feedList = feedList;
    }

    @NonNull
    @Override
    public FeedPetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_feed_pets, parent, false);
        return new FeedPetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedPetsViewHolder holder, int position) {
        FeedPet feedPet = feedList.get(position);
        //data OU PREÇO
        if(!feedPet.isIs_mei()){
            // Parseando a data da foto para um OffsetDateTime e depois pegando a data (LocalDate)
            OffsetDateTime dateTime = OffsetDateTime.parse(feedPet.getPostDate());
            LocalDate dataAnterior = dateTime.toLocalDate();

            // Obtendo a data atual
            LocalDate dataAtual = LocalDate.now();

            // Calculando a diferença em dias
            long dias = ChronoUnit.DAYS.between(dataAnterior, dataAtual);

            // Configurando o texto com o número de dias + " dias atrás"
            if(dias == 0){
                holder.days.setText("Hoje");
            }else if(dias == 1){
                holder.days.setText("Ontem");

            }else{
                holder.days.setText("Há "+dias + " dias atrás");

            }
        }else{
            holder.days.setText( "R$ "+feedPet.getPrice());
            holder.entrarContato.setVisibility(View.VISIBLE);
            holder.entrarContato.setOnClickListener(v -> {
                String phoneNumber = "tel:123456789"; // Substitua pelo número que você deseja discar
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneNumber));
                v.getContext().startActivity(intent);
            });}

        // NOMES
        





        // Bind data to views
        holder.username.setText(String.valueOf(feedPet.getUserId()));
        holder.petsInPhoto.setText(feedPet.getPets() != null ? feedPet.getPets().toString() : "");
        holder.description.setText(feedPet.getCaption());


        holder.likedBy.setText("joao"); // Ajuste conforme a sua lógica


        // foto do produto
        Glide.with(holder.photo.getContext())
                .load(feedPet.getPicture())
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return feedList != null ? feedList.size() : 0;
    }

    // ViewHolder class to represent each item
    public static class FeedPetsViewHolder extends RecyclerView.ViewHolder {

        ImageView userPhoto;
        TextView username;
        TextView petsInPhoto;
        TextView days;
        ImageView photo, curtida;
        ImageButton shareButton, commentButton, likeButton;
        TextView likedBy;
        TextView description;
        CardView entrarContato;

        public FeedPetsViewHolder(@NonNull View itemView) {
            super(itemView);

            userPhoto = itemView.findViewById(R.id.userPhoto);
            username = itemView.findViewById(R.id.username);
            petsInPhoto = itemView.findViewById(R.id.petsInPhoto);
            days = itemView.findViewById(R.id.days);
            photo = itemView.findViewById(R.id.photo);
            commentButton = itemView.findViewById(R.id.commentButton);
            likeButton = itemView.findViewById(R.id.likeButton);
            likedBy = itemView.findViewById(R.id.liked_by);
            description = itemView.findViewById(R.id.decription);
            entrarContato = itemView.findViewById(R.id.entrarContato);
            curtida = itemView.findViewById(R.id.curtida);



        }
    }
}
