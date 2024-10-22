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

import java.io.IOException;
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

    public void curtir(String id, String username, CurtirCallback callback) {
        String API = "https://apimongo-ghjh.onrender.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofit.create(ApiHome.class);

        Call<String> call = api.like(id, username);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Curtir response code", "Curtir: " + response.code());
                    callback.onSuccess(response.body());
                } else {
                    try {
                        // Aqui estamos pegando o corpo do erro corretamente
                        String errorResponse = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";
                        Log.e("Curti error", "Erro ao curtir: " + errorResponse);
                        callback.onError(errorResponse);
                    } catch (IOException e) {
                        e.printStackTrace();
                        callback.onError("Erro ao processar o corpo do erro");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("Curtir error onFailure", "Falha ao curtir: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public interface CurtirCallback {
        void onSuccess(String modelRetorno);
        void onError(String errorMessage);
    }

    public void getPets(List<Integer> ids, PetsCallBack callback) {
        String API = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofit.create(ApiHome.class);

        Call<List<String>> call = api.getPetNicknames(ids);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> listapets = response.body();
                    Log.d("Perfil", "Perfil: " + listapets);

                    callback.onSuccess(listapets);
                } else {

                    callback.onError("Nenhum perfil encontrado");

                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable throwable) {
                Log.e("Perfil", "Erro: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public interface PetsCallBack {
        void onSuccess(List<String> nomes);
        void onError(String errorMessage);
    }





}
