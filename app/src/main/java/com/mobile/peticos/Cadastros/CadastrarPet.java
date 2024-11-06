package com.mobile.peticos.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.MainActivity;

import com.mobile.peticos.Padrao.CallBack.AuthCallback;
import com.mobile.peticos.Padrao.Metodos;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.Cor;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.Perfil.Pet.API.Raca;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class CadastrarPet extends AppCompatActivity {
    Button btnCadastrar;
    AutoCompleteTextView  raca, cor;
    TextView nome, idade, porteobrigatorio, generoobrigatorio, especieobrigatorio;
    Retrofit retrofit1, retrofit2;
    int id;
    String username;
    ProgressBar progressBar;
    Spinner genero_drop, porte_drop, especie_drop;
    Button btnSair;
    List<String> racaNomes, corNomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pet);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        especie_drop = findViewById(R.id.especie);
        raca = findViewById(R.id.raca);
        cor = findViewById(R.id.cor);
        porte_drop = findViewById(R.id.porte);
        genero_drop = findViewById(R.id.genero);
        nome = findViewById(R.id.nomeCompleto);
        idade = findViewById(R.id.idade);
        progressBar = findViewById(R.id.progressBar2);
        porteobrigatorio = findViewById(R.id.porteobrigatorio);
        generoobrigatorio = findViewById(R.id.generoobrigatorio);
        especie_drop = findViewById(R.id.especie);
        especieobrigatorio = findViewById(R.id.especiebrigatorio);
        generoobrigatorio.setVisibility(View.GONE);
        porteobrigatorio.setVisibility(View.GONE);
        especieobrigatorio.setVisibility(View.GONE);
        btnSair = findViewById(R.id.btnSair);
        btnSair.setOnClickListener(v->{
            finish();
        });


        SharedPreferences sharedPreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", 0);
        username = sharedPreferences.getString("nome_usuario", "null");



        // Chamar API para setar os drops downs
        String APISQL = "https://apipeticos-ltwk.onrender.com";
        retrofit1 = new Retrofit.Builder()
                .baseUrl(APISQL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        String APIMONGO = "https://api-mongo-i1jq.onrender.com/";
        retrofit2 = new Retrofit.Builder()
                .baseUrl(APIMONGO)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        setarDropDowns();
        configurargenero();
        configurarporte();
        configurarespecie();



        btnCadastrar.setOnClickListener(v -> {
            validarCampos(v);
        });
    }

    //validar campos
    public void validarCampos(View v){
        boolean erro = false;

        if (nome.getText().toString().isEmpty()) {
            nome.setError("Campo obrigatório");
            erro = true;
        } else if (nome.getText().toString().length() > 255) {
            nome.setError("Excesso de caracteres. Max. 255");
            erro = true;
        }


        String generoSelecionado = genero_drop.getSelectedItem().toString().trim();
        if (generoSelecionado.equals("Selecione o genero do pet") || generoSelecionado.isEmpty()) {
            generoobrigatorio.setVisibility(View.VISIBLE);
            erro = true;
        } else {
            generoobrigatorio.setVisibility(View.GONE);
        }

        if (idade.getText().toString().isEmpty())  {
            idade.setError("Campo obrigatório");
            erro = true;
        }else if (Integer.parseInt(idade.getText().toString())>30){
            idade.setError("Idade inválida");
            erro = true;
        }

        String porteselecionada = porte_drop.getSelectedItem().toString().trim();
        if (porteselecionada.equals("Selecione o porte do pet")) {
            porteobrigatorio.setVisibility(View.VISIBLE);
            erro = true;
        }else{
            porteobrigatorio.setVisibility(View.GONE);
        }

        String especieselecionada = especie_drop.getSelectedItem().toString().trim();
        if (especieselecionada.equals("Selecione a espécie do pet")) {
            especieobrigatorio.setVisibility(View.VISIBLE);
            erro = true;
        }else{
            especieobrigatorio.setVisibility(View.GONE);
        }


        if (raca.getText().toString().isEmpty()) {
            raca.setError("Selecione uma raça");
            erro = true;
        }else if (!racaNomes.contains(raca.getText().toString())){
            raca.setError("Selecione uma raça válida");
            erro = true;
        }

        if (cor.getText().toString().isEmpty()) {
            cor.setError("Selecione uma cor");
            erro = true;
        } else if(!corNomes.contains(cor.getText().toString())){
            cor.setError("Selecione uma cor válida");
            erro = true;
        }

        if (!erro) {
            Cadastrar(v);
        }
    }


    //cadastrar pet
    public void Cadastrar(View view) {
        APIPets api1 = retrofit1.create(APIPets.class);
        APIPets api2 = retrofit2.create(APIPets.class);

        String g = "F";
        if(genero_drop.getSelectedItem().toString().equals("Masculino")){
            g = "M";
        }else if(genero_drop.getSelectedItem().toString().equals("Feminino")){
            g = "F";
        }



        // Cria o objeto do pet com os dados fornecidos
        ModelPetBanco pet = new ModelPetBanco(
             nome.getText().toString(),
            Integer.parseInt(idade.getText().toString()),
            g,
            especie_drop.getSelectedItem().toString(),
            raca.getText().toString(),
            porte_drop.getSelectedItem().toString(),
            cor.getText().toString(),
            username
        );

        // Faz a chamada à API para inserir o pet
        // Chamar API para setar os drops downs

        setarDropDowns();
        progressBar.setVisibility(View.VISIBLE);

        Call<Integer> call = api1.insertPet(pet);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                // Verifica se a resposta da API é bem-sucedida
                if (response.isSuccessful() && response.body() != null) {

                    int id = response.body();
                    Personalizacao petPersonalizado = new Personalizacao(
                            id,
                            especie_drop.getSelectedItem().toString(),  // Pode ser modificado conforme a lógica do seu app
                            0,
                            0,
                            0,
                            0
                    );

                    // Chama a API para personalizar o pet
                    Call<ModelRetorno> callPersonalizacao = api2.personalizarPet(petPersonalizado);
                    callPersonalizacao.enqueue(new Callback<ModelRetorno>() {
                        @Override
                        public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                            if (response.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(CadastrarPet.this, "Pet cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CadastrarPet.this, MainActivity.class);
                                startActivity(intent);
                                finish();


                            } else {
                                progressBar.setVisibility(View.GONE);
                                Log.e("Personalizacao", "Falha ao personalizar pet: " + response.code() + " - " + response.message());
                                Toast.makeText(CadastrarPet.this, "Falha ao personalizar o pet, tente novamente.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ModelRetorno> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            Log.e("Personalizacao", "Erro: " + t.getMessage());
                            Toast.makeText(CadastrarPet.this, "Erro ao tentar personalizar o pet.", Toast.LENGTH_SHORT).show();
                        }                    });
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.e("Cadastro", "Falha no cadastro: " + response.code() + " - " + response.message());
                    Toast.makeText(CadastrarPet.this, "Falha no cadastro, tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("Cadastro", "Erro: " + t.getMessage());
                Toast.makeText(CadastrarPet.this, "Erro ao tentar cadastrar o pet.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //genero
    private void configurargenero(){
        String[] doseOptions = {"Selecione o genero do pet", "Fêmea", "Macho"};

        // Criação do ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, doseOptions) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Desabilita o primeiro item para não ser clicável
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Define o texto do hint em cinza
                } else {
                    tv.setTextColor(Color.BLACK); // Define os itens selecionáveis em preto
                }
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Hint em cinza
                } else {
                    tv.setTextColor(Color.BLACK); // Itens selecionáveis em preto
                }
                return view;
            }
        };

        // Configura o layout dos itens na lista suspensa
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        genero_drop.setAdapter(adapter);

    }
    //porte
    private void configurarporte(){
        String[] doseOptions = {"Selecione o porte do pet", "Pequeno", "Médio", "Grande"};

        // Criação do ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, doseOptions) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Desabilita o primeiro item para não ser clicável
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Define o texto do hint em cinza
                } else {
                    tv.setTextColor(Color.BLACK); // Define os itens selecionáveis em preto
                }
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Hint em cinza
                } else {
                    tv.setTextColor(Color.BLACK); // Itens selecionáveis em preto
                }
                return view;
            }
        };

        // Configura o layout dos itens na lista suspensa
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        porte_drop.setAdapter(adapter);

    }
    //especie
    private void configurarespecie(){
        String[] doseOptions = {"Selecione a espécie do pet", "Cachorro", "Gato"};

        // Criação do ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, doseOptions) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Desabilita o primeiro item para não ser clicável
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Define o texto do hint em cinza
                } else {
                    tv.setTextColor(Color.BLACK); // Define os itens selecionáveis em preto
                }
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Hint em cinza
                } else {
                    tv.setTextColor(Color.BLACK); // Itens selecionáveis em preto
                }
                return view;
            }
        };

        // Configura o layout dos itens na lista suspensa
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        especie_drop.setAdapter(adapter);

    }

    //setar dropdowns
    public void setarDropDowns() {



        // Cor
        APIPets apiPets = retrofit1.create(APIPets.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Cor>> call = apiPets.getAllColors();
        call.enqueue(new Callback<List<Cor>>() {
            @Override
            public void onResponse(Call<List<Cor>> call, Response<List<Cor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Cor> corList = response.body();
                    corNomes = new ArrayList<>();

                    for (Cor cor : corList) {
                        corNomes.add(cor.color);
                    }

                    ArrayAdapter<String> adapterCor = new ArrayAdapter<>(
                            CadastrarPet.this,
                            android.R.layout.simple_dropdown_item_1line,
                            corNomes
                    );
                    cor.setAdapter(adapterCor);
                    cor.setThreshold(1);
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Cor>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CadastrarPet.this, "Erro ao carregar Cores", Toast.LENGTH_SHORT).show();
                Log.e("CadastrarPet", "Erro ao carregar Cores", throwable);
            }
        });

        progressBar.setVisibility(View.VISIBLE);
        // Raça
        Call<List<Raca>> callRaca = apiPets.getAllRaces();
        callRaca.enqueue(new Callback<List<Raca>>() {
            @Override
            public void onResponse(Call<List<Raca>> call, Response<List<Raca>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Raca> racaList = response.body();
                     racaNomes = new ArrayList<>();

                    for (Raca raca : racaList) {
                        racaNomes.add(raca.race);
                    }

                    ArrayAdapter<String> adapterRaca = new ArrayAdapter<>(
                            CadastrarPet.this,
                            android.R.layout.simple_dropdown_item_1line,
                            racaNomes
                    );
                    raca.setAdapter(adapterRaca);
                    raca.setThreshold(1);
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Raca>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CadastrarPet.this, "Erro ao carregar Raças", Toast.LENGTH_SHORT).show();
                Log.e("CadastrarPet", "Erro ao carregar Raças", throwable);
            }
        });

    }
}
