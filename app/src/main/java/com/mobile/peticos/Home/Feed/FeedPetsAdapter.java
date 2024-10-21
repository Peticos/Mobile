package com.mobile.peticos.Home.Feed;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.MetodosBanco;
import com.mobile.peticos.Padrao.ModelRetorno;
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
        }
        else{
            holder.days.setText( "R$ "+feedPet.getPrice());
            holder.entrarContato.setVisibility(View.VISIBLE);
            holder.entrarContato.setOnClickListener(v -> {
                String phoneNumber = "tel:123456789"; // Substitua pelo número que você deseja discar
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneNumber));
                v.getContext().startActivity(intent);
            });}

        // Buscando o perfil do usuário e atualizando a UI após obter os dados
        MetodosBanco metodosBanco = new MetodosBanco();
        metodosBanco.getPerfil(feedPet.userId, holder.itemView.getContext(), new MetodosBanco.PerfilCallback() {
            @Override
            public void onSuccess(ModelPerfil perfil) {
                // Configure as informações do perfil no holder, por exemplo:
                holder.username.setText(perfil.getFullName());
                //holder.userPhoto.setImageURI(Uri.parse(perfil.get()));
                // Adicione outros campos conforme necessário
            }

            @Override
            public void onError(String errorMessage) {
                // Trate o erro se necessário, por exemplo:
                Log.e("Erro", errorMessage);
                holder.username.setText("Erro ao buscar perfil");
            }
        });

        // Bind data to views

        holder.petsInPhoto.setText(feedPet.getPets() != null ? feedPet.getPets().toString() : "");
        holder.description.setText(feedPet.getCaption());


        holder.likedBy.setText(feedPet.getLikes().toString()); // Ajuste conforme a sua lógica


        // foto do produto
        Glide.with(holder.photo.getContext())
                .load(feedPet.getPicture())
                .into(holder.photo);
        // foto de perfil
        FirebaseAuth autenticator = FirebaseAuth.getInstance();
        Glide.with(holder.userPhoto.getContext())
                .load((autenticator.getCurrentUser().getPhotoUrl()))
                .into(holder.userPhoto);

        holder.photo.setOnClickListener(v -> {
            //arrumar com a bianca
//            metodosBanco.curtir(102, feedPet, new MetodosBanco.CurtirCallback() {
//                @Override
//                public void onSuccess(ModelRetorno modelRetorno) {
//                    holder.curtida.setVisibility(View.VISIBLE);
//                    holder.likedBy.setVisibility(View.VISIBLE);
//                    // Criar um Handler para remover a curtida após 5 segundos
//                    new Handler().postDelayed(() -> {
//                        holder.curtida.setVisibility(View.GONE);
//
//                    }, 5000); // 5000 milliseconds = 5 segundos
//                }
//
//                @Override
//                public void onError(String errorMessage) {
//                    // Trate o erro se necessário, por exemplo:
//                    Log.e("Erro", errorMessage);
//                    holder.username.setText("Erro ao buscar perfil");
//                }
//            });

            holder.curtida.setVisibility(View.VISIBLE);
            holder.likeButton.setImageResource(R.drawable.like); // Use setBackgroundResource para definir o fundo
            holder.liked = true;
            new Handler().postDelayed(() -> {
                holder.curtida.setVisibility(View.GONE);
            }, 1000); // 5000 milliseconds = 5 segundos

        });
        holder.likeButton.setOnClickListener(v -> {
            if(holder.liked){
                holder.liked = false;
                holder.likeButton.setImageResource(R.drawable.like);
            }else{
                holder.liked = true;
                holder.likeButton.setImageResource(R.drawable.ic_like);
            }
            // Verifica o estado atual da curtida

        });



    }

    @Override
    public int getItemCount() {
        return feedList != null ? feedList.size() : 0;
    }

    // ViewHolder class to represent each item
    public static class FeedPetsViewHolder extends RecyclerView.ViewHolder {
        Boolean liked = false;

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
            curtida = itemView.findViewById(R.id.imagem);




        }
    }
}
