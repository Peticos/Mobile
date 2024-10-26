package com.mobile.peticos.Home.Feed;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Padrao.MetodosBanco;
import com.mobile.peticos.R;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    public void ConfigTutor( FeedPetsViewHolder holder, FeedPet feedPet, MetodosBanco metodosBanco){
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
    }
    public void ConfigMei(FeedPetsViewHolder holder, FeedPet feedPet, MetodosBanco metodosBanco){
        holder.days.setText( "R$ "+feedPet.getPrice());
        holder.entrarContato.setVisibility(View.VISIBLE);
        holder.entrarContato.setOnClickListener(v -> {
            String phoneNumber = feedPet.getTelephone();
            if (TextUtils.isEmpty(phoneNumber)) {
                Toast.makeText(v.getContext(), "Contato indisponível", Toast.LENGTH_SHORT).show();
                return;
            }
            // Adicione o prefixo "tel:" ao número de telefone
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber)); // Corrigido para incluir "tel:"
            if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                v.getContext().startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "Aplicativo de telefone não encontrado", Toast.LENGTH_SHORT).show();
            }
        });

        holder.petsInPhoto.setText(feedPet.getProductName());

    }

    public void ConfigPerfilUserPost (FeedPetsViewHolder holder, FeedPet feedPet, MetodosBanco metodosBanco){
        metodosBanco.getPerfil(feedPet.userId, holder.itemView.getContext(), new MetodosBanco.PerfilCallback() {
            @Override
            public void onSuccess(ModelPerfil perfil) {
                // Configure as informações do perfil no holder, por exemplo:
                holder.username.setText("@"+perfil.getUsername());
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
    }

    public void CurtidoPor(FeedPetsViewHolder holder, FeedPet feedPet, boolean inicio, boolean descurtir, String username) {
        List<String> curtidasList = feedPet.getLikes();
        int totalCurtidas = curtidasList.size();

        if (descurtir) {
            // Lógica para descurtir
            // Remover o nome do usuário da lista de curtidas
            curtidasList.removeIf(nomeCurtidor -> nomeCurtidor.trim().equalsIgnoreCase(username));
            totalCurtidas = curtidasList.size(); // Atualiza o total de curtidas

            // Verifica se há curtidas
            if (totalCurtidas == 0) {
                holder.likedBy.setText("Publicação sem curtidas");
            } else if (totalCurtidas == 1) {
                holder.likedBy.setText("Curtido por: " + curtidasList.get(0));
            } else {
                String ultimaPessoa = curtidasList.get(totalCurtidas - 1);
                holder.likedBy.setText("Curtido por " + ultimaPessoa + " e outras " + (totalCurtidas - 1) + " pessoas");
            }
        } else {
            // Lógica para curtir
            if (inicio) {
                // Quando é a primeira vez que estamos lidando com a lista do banco
                curtidasList.add(username); // Adiciona o usuário à lista
                totalCurtidas = curtidasList.size(); // Atualiza o total de curtidas

                // Verifica se há curtidas
                if (totalCurtidas == 0) {
                    holder.likedBy.setText("Publicação sem curtidas");
                } else if (totalCurtidas == 1) {
                    holder.likedBy.setText("Curtido por: " + username);
                } else {
                    String ultimaPessoa = curtidasList.get(totalCurtidas - 1);
                    holder.likedBy.setText("Curtido por " + ultimaPessoa + " e outras " + (totalCurtidas - 1) + " pessoas");
                }
            } else {
                // Quando o username não está no banco
                // Se o usuário já curtiu
                if (!curtidasList.contains(username)) {
                    curtidasList.add(username); // Adiciona o usuário se não estiver na lista
                }

                totalCurtidas = curtidasList.size(); // Atualiza o total de curtidas
                if (totalCurtidas == 1) {
                    holder.likedBy.setText("Curtido por: " + username);
                } else {
                    String ultimaPessoa = curtidasList.get(totalCurtidas - 1);
                    holder.likedBy.setText("Curtido por " + ultimaPessoa + " e outras " + (totalCurtidas - 1) + " pessoas");
                }
            }
        }
    }


    public void Curtir(FeedPetsViewHolder holder){
        holder.likeButton.setImageResource(R.drawable.like); // Alterar ícone para curtido
        holder.liked = true; // Marca como curtido
        holder.curtida.setVisibility(View.VISIBLE);
        // Remove a curtida após 3 segundos
        new Handler().postDelayed(() -> {
            holder.curtida.setVisibility(View.GONE);
        }, 1000); // 3000 milliseconds = 3 segundos
    }
    public void DesCurtir(FeedPetsViewHolder holder){
        holder.likeButton.setImageResource(R.drawable.ic_like);
        holder.liked = false;
    }

    public void CurtirBanco(FeedPetsViewHolder holder, FeedPet feedPet, MetodosBanco metodosBanco, String username) {
        metodosBanco.curtir(feedPet.getId(), username, new MetodosBanco.CurtirCallback() {
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
    public void DesCurtirBanco(FeedPetsViewHolder holder, FeedPet feedPet, MetodosBanco metodosBanco, String username) {
        metodosBanco.descurtir(feedPet.getId(),username, new MetodosBanco.CurtirCallback() {
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

    public void CompartilharBanco(FeedPetsViewHolder holder, FeedPet feedPet,MetodosBanco metodosBanco, String username) {
        metodosBanco.share(feedPet.getId(),username, new MetodosBanco.CurtirCallback() {
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


    @Override
    public void onBindViewHolder(@NonNull FeedPetsViewHolder holder, int position) {
        FeedPet feedPet = feedList.get(position);
        MetodosBanco metodosBanco = new MetodosBanco();
        //tutor
        if(!feedPet.isIs_mei()){
            ConfigTutor(holder, feedPet, metodosBanco);

        }
        //mei
        if(feedPet.isIs_mei()){
            ConfigMei(holder, feedPet, metodosBanco);
        }
        //nome e foto do user
        ConfigPerfilUserPost(holder, feedPet, metodosBanco);

        //descricao
        holder.description.setText(feedPet.getCaption());

        // foto do produto
        Glide.with(holder.photo.getContext())
                .load(feedPet.getPicture())
                .into(holder.photo);


        //curtido por
        List<String> curtidasList = feedPet.getLikes();
        int totalCurtidas = curtidasList.size();

        SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("Perfil", Context.MODE_PRIVATE);

        totalCurtidas = curtidasList.size(); // Atualiza o total de curtidas

        // Verifica se há curtidas
        if (totalCurtidas == 0) {
            holder.likedBy.setText("Publicação sem curtidas");
        } else if (totalCurtidas == 1) {
            holder.likedBy.setText("Curtido por: " + curtidasList.get(0));
        } else {
            String ultimaPessoa = curtidasList.get(totalCurtidas - 1);
            holder.likedBy.setText("Curtido por " + ultimaPessoa + " e outras " + (totalCurtidas - 1) + " pessoas");
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
                    Curtir(holder);
                }
                else {
                    List<String> likesList = new ArrayList<>(feedPet.getLikes());
                    String nomeUsuario = sharedPreferences.getString("nome_usuario", "nome do tutor");
                    if (likesList.contains(nomeUsuario)) {
                        //lista de curtido por ja esta certa e o banco nao precisa ser chamado.
                        Curtir(holder);
                        return;
                    } else {
                        Curtir(holder);
                        CurtidoPor( holder,  feedPet,  false,false, nomeUsuario);
                        // Se não está curtido, vamos curtir
                        CurtirBanco(holder, feedPet, metodosBanco, nomeUsuario);
                        return;
                    }
                }
            }

            holder.lastClickTime = clickTime; // Atualizar o tempo do último clique
        });



        holder.likeButton.setOnClickListener(v -> {
            if (!holder.liked) {
                Curtir(holder);

                //curtido por
                List<String> likesList = new ArrayList<>(feedPet.getLikes());
                String nomeUsuario = sharedPreferences.getString("nome_usuario", "nome do tutor");
                // Adiciona o nome do usuário à lista, se ainda não estiver presente
                if (!likesList.contains(nomeUsuario)) {
                    CurtidoPor(holder, feedPet, false, false, nomeUsuario);
                } else {
                    return;
                }

                // Se não está curtido, vamos curtir
                CurtirBanco(holder, feedPet, metodosBanco, nomeUsuario);


            } else {
                DesCurtir(holder);
                String nomeUsuario = sharedPreferences.getString("nome_usuario", "nome do tutor");
                List<String> likesList = new ArrayList<>(feedPet.getLikes());
                if (!likesList.contains(nomeUsuario)) {
                    CurtidoPor(holder,  feedPet,  false, true, nomeUsuario);
                } else {
                    CurtidoPor(holder,  feedPet,  true, true, nomeUsuario);
                }

                DesCurtirBanco(holder, feedPet, metodosBanco, nomeUsuario);




            }

        });


        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = sharedPreferences.getString("nome_usuario", "nome do tutor");

                CompartilharBanco( holder,  feedPet,  metodosBanco,  nomeUsuario);

                // URL da imagem armazenada no Firebase
                String imageUrl = "https://firebase_storage_link_para_imagem.jpg";

                // Conteúdo do post que será compartilhado
                String postContent = "\uD83E\uDDB4 Ei, amigo(a) que ama pets! \uD83D\uDC3E\n" +
                        "\n" +
                        "Eu acabei de descobrir um app incrível para quem tem um pet em casa! No Peticos, você encontra dicas, histórias e muita ajuda sobre cuidados, saúde e diversão para nossos amigos de quatro patas! \uD83D\uDC36\uD83D\uDC31\n" +
                        "\n" +
                        "Além disso, você pode acompanhar um feed cheio de conteúdo feito para a gente e até participar da nossa comunidade de pais de pets!\n" +
                        "\n" +
                        "\uD83D\uDCF2 Baixe o app e venha fazer parte dessa rede! Seu pet vai adorar. \uD83D\uDC3E\uD83D\uDC96";

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
