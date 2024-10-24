package com.mobile.peticos.Perfil.Tutor.Posts;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.Feed.FeedPetsAdapter;
import com.mobile.peticos.Perdidos.AdapterPerdidos;
import com.mobile.peticos.Perdidos.PetPerdido;
import com.mobile.peticos.Perfil.APIPerfil;
import com.mobile.peticos.Perfil.Tutor.PerfilFragment;
import com.mobile.peticos.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerdidosTutor extends Fragment {

    private static final String BASE_URL = "https://apipeticos.onrender.com/";

    private APIPerfil apiPerfil;
    private RecyclerView recyclerView;

    public static PerdidosTutor newInstance() {
        return new PerdidosTutor();
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
        View view = inflater.inflate(R.layout.fragment_perdidos_pett, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        ImageButton voltar = view.findViewById(R.id.goBack);

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
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Perfil", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id",10);

        Call<List<PetPerdido>> call = apiPerfil.getRescuedLostByid(281); // Use o ID do sharedPreferences
        call.enqueue(new Callback<List<PetPerdido>>() {
            @Override
            public void onResponse(Call<List<PetPerdido>> call, Response<List<PetPerdido>> response) {
                Log.d("FeedDoPet", "Resposta da API recebida. Código: " + response.code()); // Logando o código de resposta

                if (response.isSuccessful() && response.body() != null) {
                    List<PetPerdido> feedList = response.body();

                    Log.d("FeedDoPet", "Dados recebidos: " + feedList.toString()); // Logando os dados recebidos

                    updateRecyclerViewFeed(feedList);
                } else {
                    Log.e("FeedDoPet", "Erro na resposta: " + (response.errorBody() != null ? response.errorBody().toString() : "Resposta vazia"));
                    Toast.makeText(getContext(), "Nenhum Post encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PetPerdido>> call, Throwable throwable) {
                Log.e("FeedPet", "Erro: " + throwable.getMessage());
                Toast.makeText(getContext(), "Erro ao carregar posts: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerViewFeed(List<PetPerdido> feedList) {
        // Configuração do RecyclerView para o feed de pets
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuração do Adapter para o RecyclerView
        AdapterPerdidos feedPetsAdapter = new AdapterPerdidos(feedList);
        recyclerView.setAdapter(feedPetsAdapter);  // Aqui, finalmente vinculando o Adapter
    }
}
