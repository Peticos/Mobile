package com.mobile.peticos.Padrao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfilAuth;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Home.AdcionarFoto.AdicionarAoFeedPrincipal;
import com.mobile.peticos.Home.ApiHome;
import com.mobile.peticos.Home.HomeDica.DicasDoDia;
import com.mobile.peticos.Login;
import com.mobile.peticos.MainActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MetodosBanco {



    public void getDicasDoDia(Context context, DicaDoDiaCallback callback) {
        String APIRedis = "https://apiredis-n0ke.onrender.com";
        Retrofit retrofitRedis = new Retrofit.Builder()
                .baseUrl(APIRedis)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofitRedis.create(ApiHome.class);

        api.getDayHintRedis().enqueue(new Callback<List<DicasDoDia>>() {
            @Override
            public void onResponse(Call<List<DicasDoDia>> call, Response<List<DicasDoDia>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.d("DICA DO DIA", response.body().toString());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.message());
                    Log.e("DICA DO DIA ERRO", response.message());
                    Log.e("DICA DO DIA ERRO", response.code() + " " );
                }
            }

            @Override
            public void onFailure(Call<List<DicasDoDia>> call, Throwable throwable) {
                Log.e("DICA DO DIA ERRO", throwable.getMessage());
                throwable.printStackTrace();
                String errorMessage = throwable.getMessage();
                if (errorMessage == null || errorMessage.isEmpty()) {
                    errorMessage = "Erro desconhecido ao carregar dicas do dia.";
                }

                Log.e("DICAS_DO_DIA_ERRO", errorMessage, throwable); // Registra o erro com detalhes
                callback.onError(errorMessage);
            }
        });
    }


    public void insertDicaDia(List<DicasDoDia> dicas, DicaCallback callback) {
        String APIRedis = "https://apiredis-n0ke.onrender.com";
        Retrofit retrofitRedis = new Retrofit.Builder()
                .baseUrl(APIRedis)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofitRedis.create(ApiHome.class);

        api.insertDayHint(dicas).enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    callback.onResult(true);
                } else {
                    callback.onResult(false);
                    Log.e("DICA DO DIA ERRO", response.message());
                    Log.e("DICA DO DIA ERRO", response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Log.e("DICA DO DIA ERRO", throwable.getMessage());
                throwable.printStackTrace();
                callback.onResult(false);

            }
        });

    }

    public void dicasDoDia(DicaCallback callback) {
        String API = "https://apiredis-n0ke.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofit.create(ApiHome.class);
        Call<ModelRetorno> call = api.checkDayHint();

        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    callback.onResult(true);
                    Log.d("DICA DO DIA", "Dica do dia existe");
                } else {
                    Log.e("DICA DO DIA", "Erro ao inserir dica do dia");
                    callback.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Log.e("DICA DO DIA ERRO", throwable.getMessage());
                throwable.printStackTrace();
                callback.onResult(false);
            }
        });
    }

    public interface DicaCallback {
        void onResult(boolean isSuccess);
    }

    public interface DicaDoDiaCallback {
        void onSuccess(List<DicasDoDia> dicas);
        void onError(String errorMessage);

    }



    public void verificarBairro(BairroCallback callback, EditText bairro) {
        // URL da API
        String API = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Criar chamada
        APIBairro apiBairro = retrofit.create(APIBairro.class);
        Call<List<ModelBairro>> call = apiBairro.getAll();

        // Defina o bairro que você deseja verificar
        String bairroProcurado = bairro.getText().toString();

        // Executar chamada da API
        call.enqueue(new Callback<List<ModelBairro>>() {
            @Override
            public void onResponse(Call<List<ModelBairro>> call, retrofit2.Response<List<ModelBairro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ModelBairro> bairrosList = response.body();

                    // Verificar se o bairro está presente
                    boolean bairroEncontrado = false;
                    for (ModelBairro item : bairrosList) {
                        if (bairroProcurado.equalsIgnoreCase(item.getNeighborhood())) {
                            bairroEncontrado = true;
                            break;
                        }
                    }

                    // Chamar o callback com o resultado
                    callback.onResult(bairroEncontrado);
                } else {
                    callback.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {
                throwable.printStackTrace();
                callback.onResult(false);
            }
        });
    }

    // Defina a interface BairroCallback
    public interface BairroCallback {
        void onResult(boolean bairroEncontrado);
    }

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
        String API = "https://apiredis-n0ke.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHome api = retrofit.create(ApiHome.class);

        Call<ModelRetorno> call = api.like(id, username);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    Log.d("Curtir response code", "Curtir: " + response.code());
                    callback.onSuccess(response.body());

                } else {
                    Log.d("Curtir response code", "Curtir: " + response.code());

                    callback.onError("Erro ao curtir");
                }
                Log.d("Response", response.toString());

            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Log.e("Curtir error onFailure", "Falha ao curtir: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public void descurtir(String id, String username, CurtirCallback callback) {
        String API = "https://apiredis-n0ke.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofit.create(ApiHome.class);

        Call<ModelRetorno> call = api.dislike(id, username);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    Log.d("Curtir response code", "Curtir: " + response.code());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro ao descurtir" + response.code());

                }


            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Log.e("Curtir error onFailure", "Falha ao curtir: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public void share(String id, String username, CurtirCallback callback) {
        String API = "https://apimongo-ghjh.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofit.create(ApiHome.class);

        Call<ModelRetorno> call = api.share(id, username);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    Log.d("Share response code", "SHare: " + response.code());
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Erro ao compartilhar");
                }
                Log.d("Response", response.toString());

            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Log.e("Curtir error onFailure", "Falha ao compartilhar: " + throwable.getMessage());
                callback.onError(throwable.getMessage());
            }
        });
    }
    public interface CurtirCallback {
        void onSuccess(ModelRetorno modelRetorno);
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
