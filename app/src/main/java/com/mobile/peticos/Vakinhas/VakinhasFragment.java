package com.mobile.peticos.Vakinhas;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.mobile.peticos.Perdidos.Adcionar.AdapterPetFeedTriste;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VakinhasFragment extends Fragment {
    private RecyclerView recyclerView, recyclerPetsVakinha;
    private VakinhasAdapter adapter;
    private AdapterAdicionarVakinha adapterAdicionarVakinha;
    private Button btn_sair;
    ImageButton btAdicionar;
    private List<Vakinha> vakinhaList;

    // Novo: Definindo as referências para infoVakinha, fechar e cardInfoVakinha
    private ImageView infoVakinha, fechar;
    private CardView cardInfoVakinha, cardErroVakinhas, cardNovaVakinha;

    public VakinhasFragment() {
        // Required empty public constructor
    }

    public static VakinhasFragment newInstance() {
        return new VakinhasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vakinhas, container, false);

        recyclerView = view.findViewById(R.id.RecyclerViewVakinhas);
        recyclerPetsVakinha = view.findViewById(R.id.recyclerPetsVakinha);
        btAdicionar = view.findViewById(R.id.btnAdicionar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardErroVakinhas = view.findViewById(R.id.cardErroVakinhas);
        cardNovaVakinha = view.findViewById(R.id.cardNovaVakinha);
        btn_sair = view.findViewById(R.id.btn_sair);

        // Inicializando o card como GONE inicialmente
        cardInfoVakinha = view.findViewById(R.id.cardInfoVakinha);

        // Referenciando os botões
        infoVakinha = view.findViewById(R.id.infoVakinha);
        fechar = view.findViewById(R.id.fechar);

        // Adicione o listener para mostrar o card ao clicar no infoVakinha
        infoVakinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInfoVakinha.setVisibility(View.VISIBLE); // Mostrar o card
            }
        });

        // Adicione o listener para esconder o card ao clicar no fechar
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInfoVakinha.setVisibility(View.GONE); // Esconder o card
            }
        });

        // Inicialize a lista de vakinhas
        vakinhaList = new ArrayList<>();

        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNovaVakinha.setVisibility(View.VISIBLE);
            }
        });

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardNovaVakinha.setVisibility(View.GONE);
            }
        });

        // Adicione exemplos de vakinhas à lista (imagens locais)
        vakinhaList.add(new Vakinha(
                R.drawable.user1, // Exemplo de imagem do usuário
                R.drawable.pet_vakinha, // Exemplo de imagem principal
                "geogeo43", // Username
                "Há 2 dias", // Days
                "Nutela", // Pets in Photo
                "Lorem ipsum dolor sit amet consectetur adipisicing elit." // Descrição
        ));

        vakinhaList.add(new Vakinha(
                R.drawable.user1, // Exemplo de imagem do usuário
                R.drawable.pet_vakinha, // Exemplo de imagem principal
                "john_doe", // Username
                "Há 5 dias", // Days
                "Bella", // Pets in Photo
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." // Descrição
        ));

        // Adicione mais vakinhas conforme necessário

        adapter = new VakinhasAdapter(getContext(), vakinhaList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerPetsVakinha.setLayoutManager(layoutManager);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Perfil", MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apipeticos-ltwk.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPets apiPets = retrofit.create(APIPets.class);

        Call<List<ModelPetBanco>> call = apiPets.getPets(sharedPreferences.getString("nome_usuario", "modolo"));
        call.enqueue(new Callback<List<ModelPetBanco>>() {
            @Override
            public void onResponse(Call<List<ModelPetBanco>> call, Response<List<ModelPetBanco>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ModelPetBanco> listaPets = response.body();
                    adapterAdicionarVakinha = new AdapterAdicionarVakinha(listaPets);

                    recyclerPetsVakinha.setAdapter(adapterAdicionarVakinha);
                } else {
                    Log.e("API_ERROR", "Erro: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ModelPetBanco>> call, Throwable t) {
                Log.e("API_ERROR", "Falha na chamada", t);
                Toast.makeText(getContext(), "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}
