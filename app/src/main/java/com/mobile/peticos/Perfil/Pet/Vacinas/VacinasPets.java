package com.mobile.peticos.Perfil.Pet.Vacinas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.PerfilPet;
import com.mobile.peticos.Perfil.Pet.PesoPets;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VacinasPets extends AppCompatActivity {

    private RecyclerView recyclerViewVacina; // Declarar o RecyclerView
    private APIPets apiPets;
    ImageView btn_voltar;
    FloatingActionButton btnadd;
    int id;
    CardView cardCadastrarVacina;
    EditText nome;
    Spinner doses;
    Button btnsalvar, btnsair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacinas_pets);

        // Inicialize o RecyclerView corretamente
        recyclerViewVacina = findViewById(R.id.recyclerViewVacina);
        btnadd = findViewById(R.id.btnadd);
        cardCadastrarVacina = findViewById(R.id.cardCadastrarVacina);
        nome = findViewById(R.id.nome_vacina);
        doses = findViewById(R.id.num_doses);
        btnsair = findViewById(R.id.btn_sair);
        btnsalvar = findViewById(R.id.btn_salvar);



        configurardoses();


        // Recuperar o ID do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Pet", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", 0);
        btn_voltar = findViewById(R.id.btn_voltar);
        btn_voltar.setOnClickListener(v ->{
            Intent intent = new Intent(VacinasPets.this, PerfilPet.class);
            startActivity(intent);
            finish();
        });

        btnadd.setOnClickListener(v ->{
            cardCadastrarVacina.setVisibility(View.VISIBLE);
        });
        btnsalvar.setOnClickListener(v ->{
            criarVacina();

        });

        btnsair.setOnClickListener(v->{
            cardCadastrarVacina.setVisibility(View.GONE);
            nome.setText("");
            doses.setSelection(0);
        });



        // Configuração do Retrofit
        setupRetrofitFeed();

        // Inicializar o RecyclerView com dados
        initRecyclerViewFeed();
    }


    private void criarVacina(){
        // Obtendo o nome da vacina
        String nomeVacina = nome.getText().toString();

        // Obtendo a dose selecionada do Spinner
        String doseSelecionadaString =  doses.getSelectedItem().toString();
        int doseSelecionada = 0; // Valor padrão

        try {
            // Convertendo a string para um inteiro
            doseSelecionada = Integer.parseInt(doseSelecionadaString);
        } catch (NumberFormatException e) {
            // Lidar com o caso em que a conversão falha (opcional)
            e.printStackTrace(); // Você pode registrar o erro ou mostrar uma mensagem ao usuário
        }

        ModelVacina vacina = new ModelVacina(
                0,
                id,
                nomeVacina,
                doseSelecionada,
                0
        );


        Call<Integer> call = apiPets.insertVacina(vacina);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(VacinasPets.this, "Cadastrado com sucesso!",  Toast.LENGTH_SHORT). show();
                    cardCadastrarVacina.setVisibility(View.GONE);
                    nome.setText("");
                    doses.setSelection(0);
                    recreate();
                } else {
                    Log.e("Cadastrar Vacina", "Erro: " + response.errorBody().toString());
                    Log.e("Cadastrar Vacina", "Erro: " + response.code());


                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable throwable) {
                Log.e("Cadastrar vacina", "Erro: " + throwable.getMessage());
            }
        });
    }



    private void configurardoses(){
        String[] doseOptions = {"Selecione a quantidade de doses", "1", "2", "3", "4", "5"};

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
        doses.setAdapter(adapter);



    }


    // Configuração do Retrofit
    private void setupRetrofitFeed() {
        String API = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPets = retrofit.create(APIPets.class);
    }

    // Inicializa o RecyclerView com dados de vacina
    private void initRecyclerViewFeed() {
        Call<List<ModelVacina>> call = apiPets.getallVacina(id);
        call.enqueue(new Callback<List<ModelVacina>>() {
            @Override
            public void onResponse(Call<List<ModelVacina>> call, Response<List<ModelVacina>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ModelVacina> feedList = response.body();
                    updateRecyclerViewFeed(feedList);
                } else {
                    Log.e("FeedPet", "Erro: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<ModelVacina>> call, Throwable throwable) {
                Log.e("FeedPet", "Erro: " + throwable.getMessage());
            }
        });
    }

    // Atualiza o RecyclerView com a lista de vacinas
    private void updateRecyclerViewFeed(List<ModelVacina> feedList) {
        List<ModelVacina> vacinas = new ArrayList<>();
        for (ModelVacina vacina : feedList) {
            vacinas.add(new ModelVacina(
                    vacina.getIdVaccine(),
                    vacina.getIdPet(),
                    vacina.getName(),
                    vacina.getNumDoses(),
                    vacina.getDosesTaked()
            ));
        }
        if(vacinas== null){
            Toast.makeText(VacinasPets.this, "Nenhuma vacina!",  Toast.LENGTH_SHORT). show();

        }

        // Configuração do RecyclerView
        recyclerViewVacina.setLayoutManager(new LinearLayoutManager(this));
        VacinasAdapter feedPetsAdapter = new VacinasAdapter(vacinas);
        recyclerViewVacina.setAdapter(feedPetsAdapter);
    }
}
