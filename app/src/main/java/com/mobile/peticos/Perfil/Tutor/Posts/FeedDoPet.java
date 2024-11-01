package com.mobile.peticos.Perfil.Tutor.Posts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.Feed.FeedPetsAdapter;
import com.mobile.peticos.Perfil.APIPerfil;
import com.mobile.peticos.Perfil.Tutor.PerfilFragment;
import com.mobile.peticos.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedDoPet extends Fragment {

    private static final String BASE_URL = "https://api-mongo-i1jq.onrender.com";
    private static final String PREFS_NAME = "Perfil";
    private static final String KEY_ID = "id";
    private static final int DEFAULT_ID = 2;

    private APIPerfil apiPerfil;
    private RecyclerView recyclerView;
    private CardView cardFeedErro, cardFeedSemPost;
    private ProgressBar progressBar;

    public static FeedDoPet newInstance() {
        return new FeedDoPet();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRetrofitFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed_do_pet, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        ImageButton voltar = view.findViewById(R.id.goBack);
        cardFeedErro = view.findViewById(R.id.cardFeedErro);
        cardFeedSemPost = view.findViewById(R.id.cardFeedSemPost);
        progressBar = view.findViewById(R.id.progressBar2);

        initRecyclerViewFeed();

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, PerfilFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    // Configuração do Retrofit
    private void setupRetrofitFeed() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPerfil = retrofit.create(APIPerfil.class);
    }

    // Inicializa o RecyclerView com todos os locais
    private void initRecyclerViewFeed() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String id = String.valueOf(sharedPreferences.getInt(KEY_ID, DEFAULT_ID));

        progressBar.setVisibility(View.VISIBLE);
        Call<List<FeedPet>> call = apiPerfil.getPostByid(id); // Use o ID do sharedPreferences
        call.enqueue(new Callback<List<FeedPet>>() {
            @Override
            public void onResponse(Call<List<FeedPet>> call, Response<List<FeedPet>> response) {
                Log.d("FeedDoPet", "Resposta da API recebida. Código: " + response.code()); // Logando o código de resposta

                if (response.isSuccessful() && response.body() != null) {
                    List<FeedPet> feedList = response.body();

                    progressBar.setVisibility(View.GONE);
                    Log.d("FeedDoPet", "Dados recebidos: " + feedList.toString()); // Logando os dados recebidos

                    updateRecyclerViewFeed(feedList);
                } else {
                    progressBar.setVisibility(View.GONE);
                    cardFeedSemPost.setVisibility(View.VISIBLE);
                    Log.e("FeedDoPet", "Erro na resposta: " + (response.errorBody() != null ? response.errorBody().toString() : "Resposta vazia"));
                    Toast.makeText(getContext(), "Nenhum Post encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FeedPet>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                cardFeedErro.setVisibility(View.VISIBLE);
                Log.e("FeedPet", "Erro: " + throwable.getMessage());
                Toast.makeText(getContext(), "Erro ao carregar posts: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerViewFeed(List<FeedPet> feedList) {
        // Configuração do RecyclerView para o feed de pets
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuração do Adapter para o RecyclerView
        FeedPetsAdapter feedPetsAdapter = new FeedPetsAdapter(feedList);
        recyclerView.setAdapter(feedPetsAdapter);  // Aqui, finalmente vinculando o Adapter
    }
}
