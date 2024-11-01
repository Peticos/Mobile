package com.mobile.peticos.Vakinhas;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.mobile.peticos.Local.ApiLocais;
import com.mobile.peticos.Local.LocaisAdapter;
import com.mobile.peticos.Local.Local;
import com.mobile.peticos.Padrao.ModelRetorno;
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
    private Button btn_sair, btn_salvar;
    ProgressBar progressBar;
    ImageButton btAdicionar;
    Retrofit retrofit;
    EditText linkVakinha;
    VakinhAPI apiVakinhas;

    private ImageView infoVakinha, fechar;
    CardView cardInfoVakinha, cardErroVakinhas, cardNovaVakinha;

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

        progressBar = view.findViewById(R.id.progressBar2);
        recyclerView = view.findViewById(R.id.RecyclerViewVakinhas);
        recyclerPetsVakinha = view.findViewById(R.id.recyclerPetsVakinha);
        btAdicionar = view.findViewById(R.id.btnAdicionar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardErroVakinhas = view.findViewById(R.id.cardErroVakinhas);
        cardNovaVakinha = view.findViewById(R.id.cardNovaVakinha);
        btn_sair = view.findViewById(R.id.btn_sair);
        linkVakinha = view.findViewById(R.id.linkVakinha);
        btn_salvar = view.findViewById(R.id.btn_salvar);

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
                SharedPreferences sharedPreferencesPet = getContext().getSharedPreferences("PetVakinha", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferencesPet.edit();
                editor.putString("selectedPet", "0");
                editor.apply();
                cardInfoVakinha.setVisibility(View.GONE); // Esconder o card
            }
        });

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCampos();
            }
        });




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

        setupRetrofit();
        configadcionarvakinha();
        initRecyclerView();





        return view;
    }

    //validar campos
    Boolean erro = false;
    private void validarCampos(){
        SharedPreferences sharedPreferencesPet = getContext().getSharedPreferences("PetVakinha", Context.MODE_PRIVATE);
        int idPet = Integer.parseInt(sharedPreferencesPet.getString("selectedPet", "0"));
        if(linkVakinha.getText().toString().isEmpty()){
            linkVakinha.setError("Por favor, insira um link.");
            erro = true;
        }
        if (idPet== 0){
            //TRATAR O ERRO SE TIVER VAZIO
            erro = true;
        }
        if(erro == false){
            cadastrarVakinha();
        }
    }

    //cnfigurar adicionar vakinhas
    private void configadcionarvakinha(){
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
    }

    // Configuração do Retrofit
    private void setupRetrofit() {
        String API = "https://apipeticos-ltwk.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiVakinhas = retrofit.create(VakinhAPI.class);
    }

    private void cadastrarVakinha() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Perfil", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 278);
        SharedPreferences sharedPreferencesPet = getContext().getSharedPreferences("PetVakinha", Context.MODE_PRIVATE);
        String idPet = sharedPreferencesPet.getString("selectedPet", "0");
        SharedPreferences.Editor editor = sharedPreferencesPet.edit();

        int idPetInt = Integer.parseInt(idPet);
        Vakinha vakinha = new Vakinha(
                id,
                idPetInt,
                "https://www.vakinha.com.br/5167185"
        );
        Call<ModelRetorno> call = apiVakinhas.insertVakinha(vakinha);

        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Vakinha criada com sucesso", Toast.LENGTH_SHORT).show();
                    cardNovaVakinha.setVisibility(View.GONE);
                    editor.putString("selectedPet", "0");

                    editor.apply();


                } else {
                    Toast.makeText(getContext(), "Erro ao criar vakinha", Toast.LENGTH_SHORT).show();
                    cardNovaVakinha.setVisibility(View.GONE);
                    editor.putString("selectedPet", "0");
                    editor.apply();


                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable t) {
                Toast.makeText(getContext(), "Erro de conexão", Toast.LENGTH_SHORT).show();
                cardNovaVakinha.setVisibility(View.GONE);
                editor.putString("selectedPet", "0");

                editor.apply();

            }
        });
    }

    // Inicializa o RecyclerView com todos as vakinhas
    private void initRecyclerView() {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Vakinha>> call = apiVakinhas.getAll();
        call.enqueue(new Callback<List<Vakinha>>() {
            @Override
            public void onResponse(Call<List<Vakinha>> call, Response<List<Vakinha>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    List<Vakinha> vakinhaList = response.body();

                    recyclerView.setAdapter(new VakinhasAdapter(vakinhaList));
                } else {
                    progressBar.setVisibility(View.GONE);
                    cardErroVakinhas.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Nenhuma vakinha encontrada", Toast.LENGTH_SHORT).show();;
                    Log.e("API_ERROR", "Erro: " + response.errorBody());
                    Log.e("API_ERROR", "CODE: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Vakinha>> call, Throwable throwable) {
               progressBar.setVisibility(View.GONE);
                cardErroVakinhas.setVisibility(View.VISIBLE);
                Log.e("API_ERROR", "Falha na chamada", throwable);
            }
        });
    }





}
