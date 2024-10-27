package com.mobile.peticos.Padrao;

import android.content.Context;
import android.util.Log;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Home.AdicionarAoFeedPrincipal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetodosBanco {

    public void getPerfil(int id, Context context, PerfilCallback callback) {
        String API = "https://apipeticos-ltwk.onrender.com";
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
        String API = "https://api-mongo-i1jq.onrender.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AdicionarAoFeedPrincipal.APIHome api = retrofit.create(AdicionarAoFeedPrincipal.APIHome.class);

        Call<String> call = api.like(id, username);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Curtir response code", "Curtir: " + response.code());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro ao curtir");
                }
                Log.d("Response", response.toString());

            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("Curtir error onFailure", "Falha ao curtir: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public void descurtir(String id, String username, CurtirCallback callback) {
        String API = "https://api-mongo-i1jq.onrender.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AdicionarAoFeedPrincipal.APIHome api = retrofit.create(AdicionarAoFeedPrincipal.APIHome.class);

        Call<String> call = api.dislike(id, username);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Curtir response code", "Curtir: " + response.code());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro ao curtir" + response.code());

                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("Curtir error onFailure", "Falha ao curtir: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public void share(String id, String username, CurtirCallback callback) {
        String API = "https://api-mongo-i1jq.onrender.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AdicionarAoFeedPrincipal.APIHome api = retrofit.create(AdicionarAoFeedPrincipal.APIHome.class);

        Call<String> call = api.share(id, username);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("Share response code", "SHare: " + response.code());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro ao compartilhar");
                }
                Log.d("Response", response.toString());

            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                Log.e("Curtir error onFailure", "Falha ao compartilhar: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public interface CurtirCallback {
        void onSuccess(String modelRetorno);
        void onError(String errorMessage);
    }

    public void getPets(List<Integer> ids, PetsCallBack callback) {
        String API = "https://apipeticos-ltwk.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AdicionarAoFeedPrincipal.APIHome api = retrofit.create(AdicionarAoFeedPrincipal.APIHome.class);

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
