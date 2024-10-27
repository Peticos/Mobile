package com.mobile.peticos.Perdidos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Padrao.MetodosBanco;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perdidos.Achar.PetFoundDialogFragment;
import com.mobile.peticos.R;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    public void acharPet (int id, ViewHolder holder){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apipeticos-ltwk.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiPerdidos apiPets = retrofit.create(ApiPerdidos.class);
        //2024-10-23

        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());


        Call<ModelRetorno> call = apiPets.acharPet(id, data);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelRetorno perdido = response.body();
                    Log.d("Perfil", "perdido: " + perdido);
                    Toast.makeText(holder.itemView.getContext(), "Achou", Toast.LENGTH_SHORT).show();
                    // Exibir o modal com os detalhes do pet encontrado
                    PetFoundDialogFragment dialog = PetFoundDialogFragment.newInstance(
                            "Ebaa!!! Mais um amiguinho encontrado!",
                            "Nós da Peticos ficamos feliz que você tenha encontrado seu Pet! Que tal fazer uma publicação em comemoração?"
                    );
                    dialog.show(((FragmentActivity) holder.itemView.getContext()).getSupportFragmentManager(), "PetFoundDialog");

                } else {
                    Log.e("FeedPet", "Erro: " + response.errorBody().toString());
                    Log.e("FeedPet", "Erro código: " + response.code());
                    Log.e("FeedPet", "Erro mensagem: " + response.message());
                    Toast.makeText(holder.itemView.getContext(), "erro em achar", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Toast.makeText(holder.itemView.getContext(), "Erro ao carregar posts", Toast.LENGTH_SHORT).show();
                Log.e("FeedPet", "Erro: " + throwable.getMessage());

            }
        });
    }

    public void ConfigPerfilUserPost (ViewHolder holder, PetPerdido feedPet, MetodosBanco metodosBanco){

        metodosBanco.getPerfil(feedPet.idUser, holder.itemView.getContext(), new MetodosBanco.PerfilCallback() {
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
                holder.username.setText(String.format("Erro: %s", feedPet.getIdUser()));
                Glide.with(holder.userPhoto.getContext())
                        .load(R.drawable.fotogenerica)
                        .error(R.drawable.fotogenerica)
                        .into(holder.userPhoto);
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetPerdido pet = petList.get(position);

        ConfigPerfilUserPost(holder, pet, new MetodosBanco()) ;
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

            holder.nomepet.setText(pet.getTitle());
            holder.petsInPhoto.setText(pet.getTitle());
            SharedPreferences sharedPreferences = holder.itemView.getContext().getSharedPreferences("Perfil", Context.MODE_PRIVATE);
            int id = sharedPreferences.getInt("id", 0);
            if(id == pet.getIdUser()){
                holder.acharPet.setVisibility(View.VISIBLE);
                holder.acharPet.setOnClickListener(v->{
                    acharPet(id,holder);
                });
            }else{
                holder.acharPet.setVisibility(View.GONE);

            }



        // Descrição do pet
            holder.descricao.setText(pet.getDescription());

            // Configurar o telefone
            String numero = pet.getPhone(); // Substitua com o número do pet
            holder.ic_telefone.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + numero));
                v.getContext().startActivity(intent);
            });
            holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decidir oq vai colocar no compartilhar
                String postContent = "Venha perder seu pet!";

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");

                shareIntent.putExtra(Intent.EXTRA_TEXT, postContent);

                v.getContext().startActivity(Intent.createChooser(shareIntent, "Compartilhar post via"));
            }
        });




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
         CardView acharPet;

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
            acharPet = itemView.findViewById(R.id.encontrarpet);

        }
    }
}

