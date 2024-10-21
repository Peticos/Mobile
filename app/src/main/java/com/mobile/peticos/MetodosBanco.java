package com.mobile.peticos;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Home.ApiHome;
import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.HomeDica.DicasDoDia;
import com.mobile.peticos.Padrao.ModelRetorno;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetodosBanco {

    public void getPerfil(int id, Context context, PerfilCallback callback) {
        String API = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPerfil api = retrofit.create(APIPerfil.class);

        Call<ModelPerfil> call = api.findById(id);
        call.enqueue(new Callback<ModelPerfil>() {
            @Override
            public void onResponse(Call<ModelPerfil> call, Response<ModelPerfil> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelPerfil perfil = response.body();
                    Log.d("Perfil", "Perfil: " + perfil);

                    callback.onSuccess(perfil);
                } else {

                    callback.onError("Nenhum perfil encontrado");

                }
            }

            @Override
            public void onFailure(Call<ModelPerfil> call, Throwable throwable) {
                Log.e("Perfil", "Erro: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public interface PerfilCallback {
        void onSuccess(ModelPerfil perfil);
        void onError(String errorMessage);
    }

    public void curtir(int id, FeedPet feedPet, CurtirCallback callback) {
        String API = "https://apimongo-ghjh.onrender.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofit.create(ApiHome.class);

        Call<ModelRetorno> call = api.like(id, feedPet);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Curtir", "Curtir: " + response.body());
                    callback.onSuccess(response.body());


                } else {
                    Log.d("Curtir", "Curtir: " + response.errorBody().toString());
                    callback.onError(response.errorBody().toString());


                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Log.d("Curtir", "Curtir: " + throwable.getMessage());
                callback.onError(throwable.getMessage());

            }
        });

    }
    public interface CurtirCallback {
        void onSuccess(ModelRetorno modelRetorno);
        void onError(String errorMessage);
    }





}
