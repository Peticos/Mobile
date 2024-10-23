package com.mobile.peticos.Home.Feed;

import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
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
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.MetodosBanco;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.R;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
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
        MetodosBanco metodosBanco = new MetodosBanco();
        //data OU PREÇO
        if(!feedPet.isIs_mei()){
            holder.entrarContato.setVisibility(View.GONE);
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
            metodosBanco.getPets( feedPet.getPets(), new MetodosBanco.PetsCallBack() {
                @Override
                public void onSuccess(List<String> pets) {

                    // Supondo que pets é uma lista de strings
                    String petsConcatenados = TextUtils.join(", ", pets);
                    holder.petsInPhoto.setText(petsConcatenados);

                }

                @Override
                public void onError(String errorMessage) {
                    // Trate o erro se você deseja, por exemplo:
                    Log.e("Erro", errorMessage);
                    holder.petsInPhoto.setText("Erro ao buscar pets");
                }

            });
        } if(feedPet.isIs_mei()){
            holder.days.setText( "R$ "+feedPet.getPrice());
            holder.entrarContato.setVisibility(View.VISIBLE);
            holder.entrarContato.setOnClickListener(v -> {
                String phoneNumber = feedPet.getTelephone();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneNumber));
                v.getContext().startActivity(intent);
            });
            holder.petsInPhoto.setText(feedPet.getProductName());

        }


        // Buscando o perfil do usuário e atualizando a UI após obter os dados

        metodosBanco.getPerfil(feedPet.userId, holder.itemView.getContext(), new MetodosBanco.PerfilCallback() {
            @Override
            public void onSuccess(ModelPerfil perfil) {
                // Configure as informações do perfil no holder, por exemplo:
                holder.username.setText(perfil.getFullName());
                // Adicione outros campos conforme necessário
                if (perfil.getProfilePicture() != null){

                    Glide.with(holder.userPhoto.getContext())
                            .load(Uri.parse(perfil.getProfilePicture()))
                            .error(R.drawable.fotogenerica)
                            .into(holder.userPhoto);
                }else {
                    Glide.with(holder.userPhoto.getContext())
                            .load(R.drawable.fotogenerica)
                            .error(R.drawable.fotogenerica)
                            .into(holder.userPhoto);
                }
        }



            @Override
            public void onError(String errorMessage) {
                // Trate o erro se necessário, por exemplo:
                Log.e("Erro", errorMessage);
                holder.username.setText(String.format("Erro: %s", feedPet.userId));
                Glide.with(holder.userPhoto.getContext())
                        .load(R.drawable.fotogenerica)
                        .error(R.drawable.fotogenerica)
                        .into(holder.userPhoto);
            }
        });

        // Bind data to views

        holder.description.setText(feedPet.getCaption());


        //holder.likedBy.setText(feedPet.getLikes()); // Ajuste conforme a sua lógica


        // foto do produto
        Glide.with(holder.photo.getContext())
                .load(feedPet.getPicture())
                .into(holder.photo);



        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("Perfil", Context.MODE_PRIVATE);


        //curtido por
        String curtidas = TextUtils.join(", ", feedPet.getLikes()).trim();
        if(curtidas.equals("")){
            holder.likedBy.setText("Publicação sem curtidas");
        }else{
            holder.likedBy.setText("Curtido por: " + curtidas);
        }

        // se ja curtiu banco
        if(feedPet.getLikes().contains(sharedPreferences.getString("nome_usuario", "nome do tutor"))){
            holder.likeButton.setImageResource(R.drawable.like);
            holder.liked = true;
        }else{
            holder.likeButton.setImageResource(R.drawable.ic_like);
            holder.liked = false;
        }
        //curtir e descurtir
        holder.photo.setOnClickListener(v -> {
            long clickTime = System.currentTimeMillis();

            // Verifica se é um double click (menos de 300ms entre os cliques)
            if (clickTime - holder.lastClickTime < 300) {
                if (holder.liked) {
                    holder.liked = true; // Marcar como curtido

                    holder.curtida.setVisibility(View.VISIBLE);
                    // Remove a curtida após 3 segundos
                    new Handler().postDelayed(() -> {
                        holder.curtida.setVisibility(View.GONE);
                    }, 1000); // 3000 milliseconds = 3 segundos

                }
                else {
                    //curtido por

                    List<String> likesList = new ArrayList<>(feedPet.getLikes());

                    String nomeUsuario = sharedPreferences.getString("nome_usuario", "nome do tutor");

                    // Adiciona o nome do usuário à lista, se ainda não estiver presente
                    if (!likesList.contains(nomeUsuario)) {
                        likesList.add(nomeUsuario);

                        // Atualizar a visualização para mostrar a nova lista de curtidas
                        String curtidasAtualizadas = TextUtils.join(", ", likesList).trim();
                        holder.likedBy.setText("Curtido por: " + curtidasAtualizadas);
                    } else {
                        holder.liked = true; // Marcar como curtido
                        holder.curtida.setVisibility(View.VISIBLE);
                        // Remove a curtida após 3 segundos
                        new Handler().postDelayed(() -> {
                            holder.curtida.setVisibility(View.GONE);
                        }, 1000); // 3000 milliseconds = 3 segundos
                        holder.likeButton.setImageResource(R.drawable.like); // Alterar ícone para curtido

                        return;
                    }



                    holder.curtida.setVisibility(View.VISIBLE); // Mostrar ícone de curtida
                    holder.likeButton.setImageResource(R.drawable.like); // Alterar ícone para curtido
                    holder.liked = true; // Marcar como curtido

                    // Remove a curtida após 3 segundos
                    new Handler().postDelayed(() -> {
                        holder.curtida.setVisibility(View.GONE);
                    }, 1000); // 3000 milliseconds = 3 segundos

                    // Se não está curtido, vamos curtir
                    metodosBanco.curtir(feedPet.getId(), sharedPreferences.getString("nome_usuario", "nome do tutor"), new MetodosBanco.CurtirCallback() {
                        @Override
                        public void onSuccess(String modelRetorno) {
                            Toast.makeText(holder.itemView.getContext(), "foi", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Log.e("Erro", errorMessage);
                        }
                    });
                }
            }

            holder.lastClickTime = clickTime; // Atualizar o tempo do último clique
        });



        holder.likeButton.setOnClickListener(v -> {
            if (!holder.liked) {
                holder.curtida.setVisibility(View.VISIBLE);
                holder.likeButton.setImageResource(R.drawable.like);
                // Remove a curtida após 3 segundos
                new Handler().postDelayed(() -> {
                    holder.curtida.setVisibility(View.GONE);
                }, 3000); // 3000 milliseconds = 3 segundos
                holder.liked = true; // Marca como curtido

                //curtido por

                List<String> likesList = new ArrayList<>(feedPet.getLikes());

                String nomeUsuario = sharedPreferences.getString("nome_usuario", "nome do tutor");

                // Adiciona o nome do usuário à lista, se ainda não estiver presente
                if (!likesList.contains(nomeUsuario)) {
                    likesList.add(nomeUsuario);

                    // Atualizar a visualização para mostrar a nova lista de curtidas
                    String curtidasAtualizadas = TextUtils.join(", ", likesList).trim();
                    holder.likedBy.setText("Curtido por: " + curtidasAtualizadas);
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Você já curtiu!", Toast.LENGTH_SHORT).show();
                    return;
                }



                holder.curtida.setVisibility(View.VISIBLE); // Mostrar ícone de curtida
                holder.likeButton.setImageResource(R.drawable.like); // Alterar ícone para curtido
                holder.liked = true; // Marcar como curtido

                // Remove a curtida após 3 segundos
                new Handler().postDelayed(() -> {
                    holder.curtida.setVisibility(View.GONE);
                }, 3000); // 3000 milliseconds = 3 segundos

                // Se não está curtido, vamos curtir
                metodosBanco.curtir(feedPet.getId(), sharedPreferences.getString("nome_usuario", "nome do tutor"), new MetodosBanco.CurtirCallback() {
                    @Override
                    public void onSuccess(String modelRetorno) {
                        Toast.makeText(holder.itemView.getContext(), "foi", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("Erro", errorMessage);
                    }
                });



            } else {
                holder.likeButton.setImageResource(R.drawable.ic_like);
                holder.liked = false; // Marca como não curtido
                //curtido por
                List<String> likesList = new ArrayList<>(feedPet.getLikes());

                String nomeUsuario = sharedPreferences.getString("nome_usuario", "nome do tutor");

                // Remover o nome do usuário da lista de curtidas
                likesList.removeIf(nomeCurtidor -> nomeCurtidor.trim().equalsIgnoreCase(nomeUsuario));

                // Recombinar a lista de volta em uma string, separados por vírgulas
                String semNome = TextUtils.join(", ", likesList).trim();

                // Exibir os nomes restantes ou ocultar se não houver nenhum nome
                if (!semNome.isEmpty()) {
                    holder.likedBy.setText("Curtido por: " + semNome);
                } else {
                    holder.likedBy.setText("Publicação sem curtidas");
                }

                holder.likeButton.setImageResource(R.drawable.ic_like); // Voltar ícone de não curtido
                holder.liked = false; // Marcar como não curtido
                metodosBanco.descurtir(feedPet.getId(), sharedPreferences.getString("nome_usuario", "nome do tutor"), new MetodosBanco.CurtirCallback() {
                    @Override
                    public void onSuccess(String modelRetorno) {
                        Toast.makeText(holder.itemView.getContext(), "foi", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(String errorMessage) {
                        Log.e("Erro", errorMessage);
                    }
                });
            }

        });


        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // URL da imagem armazenada no Firebase
                String imageUrl = "https://firebase_storage_link_para_imagem.jpg";

                // Conteúdo do post que será compartilhado
                String postContent = "Aqui está o conteúdo do post que quero compartilhar junto com a imagem: " + imageUrl;

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain"); // Tipo de conteúdo ajustado para compartilhar texto e URL
                shareIntent.putExtra(Intent.EXTRA_TEXT, postContent);

                // Verifica se há algum aplicativo capaz de compartilhar o conteúdo
                if (shareIntent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(Intent.createChooser(shareIntent, "Compartilhar post via"));
                }
            }
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
        ImageButton shareButton, likeButton;
        TextView likedBy;
        TextView description;
        CardView entrarContato;
        private long lastClickTime = 0;


        public FeedPetsViewHolder(@NonNull View itemView) {
            super(itemView);

            userPhoto = itemView.findViewById(R.id.userPhoto);
            username = itemView.findViewById(R.id.username);
            petsInPhoto = itemView.findViewById(R.id.petsInPhoto);
            days = itemView.findViewById(R.id.days);
            photo = itemView.findViewById(R.id.photo);
            likeButton = itemView.findViewById(R.id.likeButton);
            likedBy = itemView.findViewById(R.id.liked_by);
            description = itemView.findViewById(R.id.decription);
            entrarContato = itemView.findViewById(R.id.entrarContato);
            curtida = itemView.findViewById(R.id.curtida);
            shareButton = itemView.findViewById(R.id.shareButton);




        }
    }
}
