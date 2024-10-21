package com.mobile.peticos.Padrao;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfilAuth;
import com.mobile.peticos.Padrao.CallBack.AuthCallback;
import com.mobile.peticos.Padrao.CallBack.PerfilCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Metodos {
    public void Authentication(View view, int id, String emailCadastro, String senhaCadastro, Context context, AuthCallback callback) {

        String urlAPI = "https://apimongo-ghjh.onrender.com/";
        Retrofit retrofitPerfil = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPerfil aPIPerfil = retrofitPerfil.create(APIPerfil.class);
        ModelPerfilAuth perfil = new ModelPerfilAuth(
                emailCadastro.trim(),
                senhaCadastro.trim(),
                id
        );

        Call<ModelRetorno> call = aPIPerfil.register(perfil);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(context, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    ModelRetorno retorno = response.body();
                    callback.onSuccess(retorno);
                } else {
                    Log.e("CadastroTutor", "Erro: " + response.code());
                    Toast.makeText(context, "Falha no cadastro, tente novamente.", Toast.LENGTH_SHORT).show();
                    callback.onError("Falha no cadastro, código de erro: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable t) {
                Log.e("CadastroTutor", "Erro: " + t.getMessage());
                callback.onError(t.getMessage());
                Toast.makeText(context, "Erro ao tentar cadastrar.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getPerfil(int id, Context context, PerfilCallback callback) {
        String API = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPerfil api = retrofit.create(APIPerfil.class);

        Call<ModelPerfil> call = api.getById(id);
        call.enqueue(new Callback<ModelPerfil>() {
            @Override
            public void onResponse(Call<ModelPerfil> call, Response<ModelPerfil> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelPerfil perfil = response.body();
                    callback.onSuccess(perfil);
                } else {
                    Toast.makeText(context, "Nenhuma Dica encontrada", Toast.LENGTH_SHORT).show();
                    callback.onError("Nenhuma Dica encontrada");
                }
            }

            @Override
            public void onFailure(Call<ModelPerfil> call, Throwable throwable) {
                Toast.makeText(context, "Erro ao carregar posts", Toast.LENGTH_SHORT).show();
                callback.onError(throwable.getMessage());
            }
        });
    }




}
