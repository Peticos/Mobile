package com.mobile.peticos.Vakinhas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Local.WebViewActivity;
import com.mobile.peticos.Padrao.MetodosBanco;
import com.mobile.peticos.Perdidos.AdapterPerdidos;
import com.mobile.peticos.Perdidos.PetPerdido;
import com.mobile.peticos.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class VakinhasAdapter extends RecyclerView.Adapter<VakinhasAdapter.ViewHolder> {
    private final List<Vakinha> vakinhaList;

    public VakinhasAdapter(List<Vakinha> vakinhaList) {
        this.vakinhaList = vakinhaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vakinhas, parent, false);
        return new ViewHolder(view);
    }

    public void ConfigPerfilUserPost (ViewHolder holder,  Vakinha vakinha, MetodosBanco metodosBanco){

        metodosBanco.getPerfil(vakinha.getIdUser(), holder.itemView.getContext(), new MetodosBanco.PerfilCallback() {
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
                holder.username.setText(String.format("Erro: %s", vakinha.getIdUser()));
                Glide.with(holder.userPhoto.getContext())
                        .load(R.drawable.fotogenerica)
                        .error(R.drawable.fotogenerica)
                        .into(holder.userPhoto);
            }
        });
    }
    public void ConfigPetsPost (ViewHolder holder,  Vakinha vakinha, MetodosBanco metodosBanco){

        List<Integer> pets = new ArrayList<>();
        pets.add(vakinha.getIdPet());
        metodosBanco.getPets( pets, new MetodosBanco.PetsCallBack() {
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
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vakinha vakinha = vakinhaList.get(position);

        // Definindo as variáveis com as imagens locais

        ConfigPerfilUserPost(holder, vakinha, new MetodosBanco());


        Glide.with(holder.fotoPrincipal.getContext())
                .load(Uri.parse(vakinha.getImage()))
                .error(R.drawable.fotogenerica)
                .into(holder.fotoPrincipal);

        //holder.days.setText(vakinha.getDays());


        int valor = (int) vakinha.getGoalAmount();
        holder.progressBar.setMax(valor);

        int progressValue = vakinha.getTotalPercentage();
        holder.progressBar.setProgress(progressValue);


        ConfigPetsPost(holder, vakinha, new MetodosBanco());
        holder.descricao.setText(vakinha.getDescription());

        holder.doar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                intent.putExtra("url", vakinha.getLink());
                intent.putExtra("titulo", "Vakinhas");
                holder.itemView.getContext().startActivity(intent);
            }
        });

        //GEO ARRUMAR O TEXTO DO SHARE
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decidir oq vai colocar no compartilhar
                String postContent = "Alerta de pet perdido! \uD83D\uDC3E\n" +
                        "\n" +
                        "Ajude-nos a encontrar esse pet querido que está perdido! Ele(a) foi visto(a) pela última vez em "+" e seu(sua) dono(a) está muito preocupado(a).\n" +
                        "\n" +
                        "Por favor, compartilhe com seus amigos e fique atento(a) caso o veja. Qualquer informação pode ajudar! Entre em contato com "+ holder.username.getText().toString()+".\n" +
                        "\n" +
                        "Vamos juntos trazer "+" para casa! ❤\uFE0F\uD83D\uDC3E";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                shareIntent.putExtra(Intent.EXTRA_TEXT, postContent);

                v.getContext().startActivity(Intent.createChooser(shareIntent, "Compartilhar post via"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vakinhaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView userPhoto;
        final ImageView fotoPrincipal;
        final TextView username;
        final TextView days;
        final TextView petsInPhoto;
        final TextView descricao;
        ProgressBar progressBar;
        ImageView share, doar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userPhoto = itemView.findViewById(R.id.userPhoto);
            fotoPrincipal = itemView.findViewById(R.id.fotoPrincipal);
            username = itemView.findViewById(R.id.username);
            days = itemView.findViewById(R.id.days);
            petsInPhoto = itemView.findViewById(R.id.petsInPhoto);
            descricao = itemView.findViewById(R.id.descricao);
            progressBar = itemView.findViewById(R.id.valor);
            share = itemView.findViewById(R.id.share);
            doar = itemView.findViewById(R.id.doar);
        }
    }
}
