
package com.mobile.peticos.Home;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Home.AdcionarFoto.AdicionarAoFeedPrincipal;
import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.Feed.FeedPetsAdapter;
import com.mobile.peticos.Home.HomeDica.AdapterCuriosidadesDiarias;
import com.mobile.peticos.Home.HomeDica.DicasDoDia;
import com.mobile.peticos.Padrao.MetodosBanco;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;
    public static final String[] REQUIRED_PERMISSIONS;
    CardView cardFeedErro, cardDicasErro, cardFeedSemPost;
    MetodosBanco metodosBanco = new MetodosBanco();
    private ProgressBar progressBar;

    static {
        List<String> requiredPermissions = new ArrayList<>();
        // Adicionando a permissão de notificação para Android 13 ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requiredPermissions.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        REQUIRED_PERMISSIONS = requiredPermissions.toArray(new String[0]);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Verificar e solicitar permissão de notificação ao abrir a tela
        checkNotificationPermission();


        cardFeedErro = view.findViewById(R.id.cardFeedErro);
        cardDicasErro = view.findViewById(R.id.cardDicasErro);
        cardFeedSemPost = view.findViewById(R.id.cardFeedSemPost);
        progressBar = view.findViewById(R.id.progressBar2);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Perfil", Context.MODE_PRIVATE);

        Boolean mei = sharedPreferences.getBoolean("mei", true);

        // Encontrar o ImageView com o ID correto
        ImageView btnCadastrar = view.findViewById(R.id.btn_cadastrar_feed);

        if (btnCadastrar != null) {
            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mei) {
                        // Adicionar produto
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, AdicionarProduto.newInstance())
                                .addToBackStack(null)
                                .commit();
                    } else {
                        // Adicionar ao feed
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, AdicionarAoFeedPrincipal.newInstance())
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        } else {
            // Log para depuração, caso o botão ainda seja nulo
            Log.e("HomeFragment", "btnCadastrar é nulo");
        }


        metodosBanco.dicasDoDia(new MetodosBanco.DicaCallback() {
            @Override
            public void onResult(boolean isSuccess) {

                if (isSuccess) {
                    progressBar.setVisibility(View.VISIBLE);
                    carregarDicas(view);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    chamarDica(view);

                }
            }
        });

        setupRetrofitFeed();
        initRecyclerViewFeed(view);

        setupRetrofiAdapter();
        String APIRedis = "https://apiredis-63tq.onrender.com";
        Retrofit retrofitRedis = new Retrofit.Builder()
                .baseUrl(APIRedis)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofitRedis.create(ApiHome.class);



        return view;
    }

    private void carregarDicas(View view) {

        metodosBanco.dicasDoDia(new MetodosBanco.DicaCallback() {
            @Override
            public void onResult(boolean isSuccess) {

                if (isSuccess) {
                    metodosBanco.getDicasDoDia(getContext(), new MetodosBanco.DicaDoDiaCallback() {
                        @Override
                        public void onSuccess(List<DicasDoDia> dicas) {
                            progressBar.setVisibility(View.GONE);
                            updateRecyclerViewDicas(dicas, view);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), "Erro ao carregar dicas: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {

                    Toast.makeText(getContext(), "Erro ao carregar dicas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Verificar e solicitar permissão de notificação
    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_NOTIFICATION_PERMISSION);
            }
        }
    }
    ApiHome ApiHome;
    Retrofit retrofit;
    // Configuração do Retrofit
    private void setupRetrofitFeed() {
        String API = "https://api-mongo-i1jq.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHome = retrofit.create(ApiHome.class);
    }
    // Inicializa o RecyclerView com todos os locais
    private void initRecyclerViewFeed(View v) {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<FeedPet>> call = ApiHome.getAll();
        call.enqueue(new Callback<List<FeedPet>>() {
            @Override
            public void onResponse(Call<List<FeedPet>> call, Response<List<FeedPet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    List<FeedPet> feedList = response.body();
                    updateRecyclerViewFeed(feedList, v);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.e("FeedPet", "Erro: " + response.errorBody().toString());
                    //cardFeedSemPost.setVisibility(View.VISIBLE);
                    //Toast.makeText(getContext(), "Nenhum Post encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FeedPet>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Erro: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("FeedPet", "Erro: " + throwable.getMessage());

               // cardFeedErro.setVisibility(View.VISIBLE);
            }
        });
    }

    private void updateRecyclerViewFeed(List<FeedPet> feedList, View v) {
        List<FeedPet> postagens = new ArrayList<>();
        for (FeedPet postagem : feedList) {
            postagens.add(new FeedPet(
                    postagem.getId(),
                    postagem.getUserId(),
                    postagem.getLikes(),
                    postagem.getShares(),
                    postagem.getPicture(),
                    postagem.getCaption(),
                    postagem.getPets(),
                    postagem.getPostDate(),
                    postagem.isIs_mei(),
                    postagem.getPrice(),
                    postagem.getTelephone(),
                    postagem.getProductName()
            ));
        }
        // Configuração do RecyclerView para o feed de pets
        RecyclerView recyclerViewFeedPets = v.findViewById(R.id.RecyclerViewFeedPets);
        recyclerViewFeedPets.setLayoutManager(new LinearLayoutManager(getContext()));



        // Configuração do Adapter para o RecyclerViewFeedPets
        FeedPetsAdapter feedPetsAdapter = new FeedPetsAdapter(postagens);
        recyclerViewFeedPets.setAdapter(feedPetsAdapter);
    }

    // Curiosidades

    private void setupRetrofiAdapter() {
        String API = "https://apipeticos-ltwk.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHome = retrofit.create(ApiHome.class);
    }
    private void chamarDica(View v) {

        Call<List<DicasDoDia>> call = ApiHome.getDayHint();
        call.enqueue(new Callback<List<DicasDoDia>>() {
            @Override
            public void onResponse(Call<List<DicasDoDia>> call, Response<List<DicasDoDia>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    List<DicasDoDia> dicas = response.body();
                    metodosBanco.insertDicaDia(dicas, new MetodosBanco.DicaCallback() {
                        @Override
                        public void onResult(boolean isSuccess) {
                            if (isSuccess) {
                                Log.d("DICA DO DIA", "Dica do dia inserida com sucesso");
                                Toast.makeText(getContext(), "carregar", Toast.LENGTH_SHORT).show();
                                carregarDicas(v);
                            } else {
                                Log.e("DICA DO DIA", "Erro ao inserir dica do dia");
                            }
                        }
                    });



                } else {
//                    progressBar.setVisibility(View.GONE);
//                    cardDicasErro.setVisibility(View.VISIBLE);
                    Log.e("DICA DO DIA", "Erro: " + response.errorBody().toString());
                    Toast.makeText(getContext(), "Nenhuma Dica encontrada", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DicasDoDia>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                cardDicasErro.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "Erro ao carregar dicas", Toast.LENGTH_SHORT).show();
                Log.e("DICA DO DIA", throwable.getMessage());

            }
        });
    }
    private void updateRecyclerViewDicas(List<DicasDoDia> dicasdodia, View v) {


        List<String> dicas = new ArrayList<>();
        for (DicasDoDia dica : dicasdodia) {
            dicas.add(dica.getHint_text());
        }

        // Configuração do RecyclerView para o dica do dia
        RecyclerView recyclerViewDicas = v.findViewById(R.id.RecyclerViewDicas);
        recyclerViewDicas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Configuração do Adapter para o RecyclerViewFeedPets
        AdapterCuriosidadesDiarias dicasAdapter = new AdapterCuriosidadesDiarias(dicas);
        recyclerViewDicas.setAdapter(dicasAdapter);

    }


}
