package com.mobile.peticos.Local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalFragment extends Fragment {

    // Botões e RecyclerView
    private Button btnVet, btnConsulta, btnLazer, btnOngs;
    private ImageView btn_semfiltro;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private ApiLocais apiLocais;
    CardView cardErroLocal;

    // Construtor
    public LocalFragment() {
        // Construtor vazio necessário
    }

    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);

        // Configuração do RecyclerView
        recyclerView = view.findViewById(R.id.recyclerViewLocais);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cardErroLocal = view.findViewById(R.id.cardErroLugares);

        // Configuração dos botões
        btnConsulta = view.findViewById(R.id.btnConsulta);
        btnVet = view.findViewById(R.id.btnVet);
        btnLazer = view.findViewById(R.id.btnLazer);
        btnOngs = view.findViewById(R.id.btnOngs);
        btn_semfiltro = view.findViewById(R.id.btn_semfiltro);

        // Configuração do Retrofit e da API
        setupRetrofit();

        // Inicializa o RecyclerView com todos os locais
        initRecyclerView();

        // Configura os botões com os filtros
        setupButtonListeners();

        return view;
    }

    // Configuração do Retrofit
    private void setupRetrofit() {
        String API = "https://apipeticos-ltwk.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiLocais = retrofit.create(ApiLocais.class);
    }
    // Inicializa o RecyclerView com todos os locais
    private void initRecyclerView() {
        Call<List<Local>> call = apiLocais.getAll();
        call.enqueue(new Callback<List<Local>>() {
            @Override
            public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Local> localList = response.body();
                    updateRecyclerView(localList);
                } else {
                    showToast("Nenhum local encontrado");
                }
            }

            @Override
            public void onFailure(Call<List<Local>> call, Throwable throwable) {
                cardErroLocal.setVisibility(View.VISIBLE);
            }
        });
    }

    // Configura os botões para aplicar os filtros
    private void setupButtonListeners() {

        btnVet.setOnClickListener(v -> LocaisFiltrado(1));
        btnLazer.setOnClickListener(v -> LocaisFiltrado(2));
        btnOngs.setOnClickListener(v -> LocaisFiltrado(3));
        btnConsulta.setOnClickListener(v -> LocaisFiltrado(4));
        btn_semfiltro.setOnClickListener(v -> initRecyclerView());
    }

    // Função para buscar locais filtrados
    private void LocaisFiltrado(int type) {
        Call<List<Local>> call = apiLocais.getByType(type);
        call.enqueue(new Callback<List<Local>>() {
            @Override
            public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Local> localList = response.body();
                    updateRecyclerView(localList);
                } else {
                    showToast("Nenhum local encontrado para este filtro");
                }
            }

            @Override
            public void onFailure(Call<List<Local>> call, Throwable throwable) {
                showToast("Erro ao carregar Locais");
            }
        });
    }

    // Atualiza o RecyclerView com a lista de locais
    private void updateRecyclerView(List<Local> localList) {
        List<Local> localModels = new ArrayList<>();
        for (Local local : localList) {
            localModels.add(new Local(
                    local.getDescription(),
                    local.getLocalPicture(),
                    local.getLinkKnowMore(),
                    local.getLocalName(),
                    local.getStreet()
            ));
        }
        recyclerView.setAdapter(new LocaisAdapter(localModels));
    }

    // Exibe uma mensagem Toast
    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
