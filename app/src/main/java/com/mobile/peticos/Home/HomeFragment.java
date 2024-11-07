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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Home.AdcionarFoto.AdicionarAoFeedPrincipal;
import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.Feed.FeedPetsAdapter;
import com.mobile.peticos.Home.HomeDica.AdapterCuriosidadesDiarias;
import com.mobile.peticos.Home.HomeDica.DicasDoDia;
import com.mobile.peticos.Padrao.MetodosBanco;
import com.mobile.peticos.Perfil.Tutor.Posts.FeedDoPet;
import com.mobile.peticos.R;

import java.io.IOException;
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
    CardView cardFeedErro, cardDicasErro, cardFeedSemPost, cardTimeout, cardSemNet;
    MetodosBanco metodosBanco = new MetodosBanco();
    private ProgressBar progressBar, recarregarPosts;
    Button btnRecarregar;

    static {
        List<String> requiredPermissions = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requiredPermissions.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        REQUIRED_PERMISSIONS = requiredPermissions.toArray(new String[0]);
    }

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    String username;

    RecyclerView recyclerViewFeedPets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        checkNotificationPermission();

        cardFeedErro = view.findViewById(R.id.cardFeedErro);
        cardDicasErro = view.findViewById(R.id.cardDicasErro);
        cardFeedSemPost = view.findViewById(R.id.cardFeedSemPost);
        progressBar = view.findViewById(R.id.progressBar2);
        recarregarPosts = view.findViewById(R.id.recarregarPosts);
        btnRecarregar = view.findViewById(R.id.btnRecarregar);

        cardSemNet = view.findViewById(R.id.cardSemNet);
        cardTimeout = view.findViewById(R.id.cardTimeOut);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Perfil", Context.MODE_PRIVATE);
        Boolean mei = sharedPreferences.getBoolean("mei", true);
        username = sharedPreferences.getString("nome_usuario", "");
        ImageView btnCadastrar = view.findViewById(R.id.btn_cadastrar_feed);


        recyclerViewFeedPets = view.findViewById(R.id.RecyclerViewFeedPets);
        recyclerViewFeedPets.setLayoutManager(new LinearLayoutManager(getContext()));


        if (btnCadastrar != null) {
            btnCadastrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mei) {
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, AdicionarProduto.newInstance())
                                .addToBackStack(null)
                                .commit();
                    } else {
                        getParentFragmentManager().beginTransaction()
                                .replace(R.id.fragmentContainerView, AdicionarAoFeedPrincipal.newInstance())
                                .addToBackStack(null)
                                .commit();
                    }
                }
            });
        } else {
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
        initRecyclerViewFeed(view, 1);
        setupRetrofiAdapter();

        btnRecarregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initRecyclerViewFeedNew(view);
            }
        });

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
                            List<String> dicasString = new ArrayList<>();
                            for (DicasDoDia dica : dicas) {
                                dicasString.add(dica.getHint_text());

                            }

                            updateRecyclerViewDicas(dicasString, view);
                        }

                        @Override
                        public void onError(String errorMessage) {
                            cardDicasErro.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                } else {
                    cardDicasErro.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_NOTIFICATION_PERMISSION);
            }
        }
    }

    ApiHome ApiHome;
    Retrofit retrofit;

    private void setupRetrofitFeed() {
        String API = "https://apiredis-63tq.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHome = retrofit.create(ApiHome.class);
    }

    private void initRecyclerViewFeed(View v, int tipo) {
        progressBar.setVisibility(View.VISIBLE);
        Log.e("FeedPet", username);
        Call<List<FeedPet>> call = ApiHome.getAll(username);

        call.enqueue(new Callback<List<FeedPet>>() {
            @Override
            public void onResponse(Call<List<FeedPet>> call, Response<List<FeedPet>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<FeedPet> feedList = response.body();
                    updateRecyclerViewFeed(feedList, v);
                } else {
                    progressBar.setVisibility(View.GONE);
                    cardFeedSemPost.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<FeedPet>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                if (throwable instanceof java.net.SocketTimeoutException) {
                    cardTimeout.setVisibility(View.VISIBLE);
                } else if (throwable instanceof java.io.IOException) {
                    cardSemNet.setVisibility(View.VISIBLE);
                } else {
                    cardFeedErro.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initRecyclerViewFeedNew(View v) {
        progressBar.setVisibility(View.VISIBLE);
        String API = "https://apiredis-63tq.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiHome = retrofit.create(ApiHome.class);
        Call<List<FeedPet>> call = ApiHome.getNewFeed(username);
        call.enqueue(new Callback<List<FeedPet>>() {
            @Override
            public void onResponse(Call<List<FeedPet>> call, Response<List<FeedPet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    try {
                        Log.e("FeedPet", "Erro: " + (response.errorBody() != null ? response.errorBody().string() : "Resposta nula"));
                    } catch (IOException e) {
                        Log.e("FeedPet", "Erro ao processar o corpo de erro: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<FeedPet>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                if (throwable instanceof java.net.SocketTimeoutException) {
                    cardTimeout.setVisibility(View.VISIBLE);
                } else if (throwable instanceof java.io.IOException) {
                    cardSemNet.setVisibility(View.VISIBLE);
                } else {
                    cardFeedErro.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void resetRecyclerViewFeed(View v) {
        RecyclerView recyclerViewFeedPets = v.findViewById(R.id.RecyclerViewFeedPets);
        recyclerViewFeedPets.setAdapter(null); // Remove o adaptador atual, limpando a lista
    }

    private void updateRecyclerViewFeed(List<FeedPet> feedList, View v) {
        RecyclerView recyclerViewFeedPets = v.findViewById(R.id.RecyclerViewFeedPets);
        resetRecyclerViewFeed(v); // Limpa o RecyclerView antes de definir o novo adaptador
        recyclerViewFeedPets.setLayoutManager(new LinearLayoutManager(getContext()));
        FeedPetsAdapter feedPetsAdapter = new FeedPetsAdapter(feedList);
        recyclerViewFeedPets.setAdapter(feedPetsAdapter);
        progressBar.setVisibility(View.GONE);
        recyclerViewFeedPets.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == feedList.size() - 1) {

                    btnRecarregar.setVisibility(View.VISIBLE);

                } else {
                    btnRecarregar.setVisibility(View.GONE);
                }
            }});
    }

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
                    List<DicasDoDia> dicaList = response.body();
                    List<String> dicas = new ArrayList<>();

                    for (DicasDoDia dica : dicaList) {
                        dicas.add(dica.getHint_text());

                    }
                    updateRecyclerViewDicas(dicas, v);
                } else {
                    cardDicasErro.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<DicasDoDia>> call, Throwable throwable) {
                Log.e("ErroDicas", throwable.getMessage());
                progressBar.setVisibility(View.GONE);
                if (throwable instanceof java.net.SocketTimeoutException) {
                    cardTimeout.setVisibility(View.VISIBLE);
                } else if (throwable instanceof java.io.IOException) {
                    cardSemNet.setVisibility(View.VISIBLE);
                } else {
                    cardFeedErro.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // Método para atualizar o RecyclerView com uma lista de Strings
    private void updateRecyclerViewDicas(List<String> dicaList, View v) {
        RecyclerView recyclerViewCuriosidadesDiarias = v.findViewById(R.id.RecyclerViewDicas);
        recyclerViewCuriosidadesDiarias.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));

        // Instancia o adapter passando a lista de Strings
        AdapterCuriosidadesDiarias adapterCuriosidadesDiarias = new AdapterCuriosidadesDiarias(dicaList, recyclerViewCuriosidadesDiarias);
        recyclerViewCuriosidadesDiarias.setAdapter(adapterCuriosidadesDiarias);
    }



}