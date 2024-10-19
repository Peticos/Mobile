
package com.mobile.peticos.Cadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mobile.peticos.MainActivity;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.APIPets;

import com.mobile.peticos.Perfil.Pet.API.Cor;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.Perfil.Pet.API.Raca;
import com.mobile.peticos.Perfil.Pet.EditarPerfilPet;
import com.mobile.peticos.Perfil.Pet.PersonalizarPets;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class CadastrarPet extends AppCompatActivity {
    Button btnCadastrar;
    AutoCompleteTextView especie, raca, cor, porte, genero;
    EditText idade, nome;

    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_pet);

        btnCadastrar = findViewById(R.id.btnCadastrar);
        especie = findViewById(R.id.especie);
        raca = findViewById(R.id.raca);
        cor = findViewById(R.id.cor);
        porte = findViewById(R.id.porte);
        genero = findViewById(R.id.genero);
        idade = findViewById(R.id.idade);
        nome = findViewById(R.id.nome);

        // Chamar API para setar os drops downs
        String API = "https://apipeticos.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        setarDropDowns();

        btnCadastrar.setOnClickListener(this::Cadastrar);
    }

    //cadastrar pet
    public void Cadastrar(View view) {
        if (especie.getText().toString().isEmpty() || raca.getText().toString().isEmpty() || cor.getText().toString().isEmpty() || porte.getText().toString().isEmpty() || genero.getText().toString().isEmpty()) {
            Toast.makeText(CadastrarPet.this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }


        APIPets api = retrofit.create(APIPets.class);
        if (genero.getText().toString().equals("Macho")) {
            genero.setText("M");
        } else {
            genero.setText("F");
        }

        ModelPetBanco pet = new ModelPetBanco(
                11,
                "giooo",
                nome.getText().toString(),
                Integer.parseInt(idade.getText().toString()),
                genero.getText().toString(),
                especie.getText().toString(),
                raca.getText().toString(),
                porte.getText().toString(),
                cor.getText().toString()
        );
        Call<ModelRetorno> call = api.insertPet(pet);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastrarPet.this, "Pet Cadastrado", Toast.LENGTH_SHORT).show();
                    Bundle bundleCadastro = getIntent().getExtras();
                    int id = bundleCadastro.getInt("id");

                    Intent intent = new Intent(CadastrarPet.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", id);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(CadastrarPet.this, "Erro ao Cadastrar: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable t) {
                Toast.makeText(CadastrarPet.this, "Erro ao Cadastrar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    //setar dropdowns
    public void setarDropDowns() {
        // Espécie
        List<String> especiesList = new ArrayList<>();
        especiesList.add("Cachorro");
        especiesList.add("Gato");
        ArrayAdapter<String> adapterEspecie = new ArrayAdapter<>(
                CadastrarPet.this,
                android.R.layout.simple_dropdown_item_1line,
                especiesList
        );
        especie.setAdapter(adapterEspecie);
        especie.setThreshold(1);

        // Porte
        List<String> porteList = new ArrayList<>();
        porteList.add("Grande");
        porteList.add("Médio");
        porteList.add("Pequeno");

        ArrayAdapter<String> adapterPorte = new ArrayAdapter<>(
                CadastrarPet.this,
                android.R.layout.simple_dropdown_item_1line,
                porteList
        );
        porte.setAdapter(adapterPorte);
        porte.setThreshold(1);

        // Cor
        APIPets apiPets = retrofit.create(APIPets.class);
        Call<List<Cor>> call = apiPets.getAllColors();
        call.enqueue(new Callback<List<Cor>>() {
            @Override
            public void onResponse(Call<List<Cor>> call, Response<List<Cor>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Cor> corList = response.body();
                    List<String> corNomes = new ArrayList<>();

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
                }
            }

            @Override
            public void onFailure(Call<List<Cor>> call, Throwable throwable) {
                Toast.makeText(CadastrarPet.this, "Erro ao carregar Cores", Toast.LENGTH_SHORT).show();
                Log.e("CadastrarPet", "Erro ao carregar Cores", throwable);
            }
        });

        // Raça
        Call<List<Raca>> callRaca = apiPets.getAllRaces();
        callRaca.enqueue(new Callback<List<Raca>>() {
            @Override
            public void onResponse(Call<List<Raca>> call, Response<List<Raca>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Raca> racaList = response.body();
                    List<String> racaNomes = new ArrayList<>();

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
                }
            }

            @Override
            public void onFailure(Call<List<Raca>> call, Throwable throwable) {
                Toast.makeText(CadastrarPet.this, "Erro ao carregar Raças", Toast.LENGTH_SHORT).show();
                Log.e("CadastrarPet", "Erro ao carregar Raças", throwable);
            }
        });
        //genero
        List<String> generoList = new ArrayList<>();
        generoList.add("Macho");
        generoList.add("Fêmea");
        ArrayAdapter<String> adapterGenero = new ArrayAdapter<>(
                CadastrarPet.this,
                android.R.layout.simple_dropdown_item_1line,
                generoList
        );
        genero.setAdapter(adapterGenero);
        genero.setThreshold(1);
    }

//    public void cadastrarFoto(){
//        String API = "https://apimongo-ghjh.onrender.com";
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        Personalizacao pet = new Personalizacao(
//                id,
//                species,
//                hatId,
//                hairId,
//                toyId,
//                glassesId
//        );
//
//        APIPets apiPets = retrofit.create(APIPets.class);
//        Call<ModelRetorno> call = apiPets.personalizarPet(pet);
//        call.enqueue(new Callback<ModelRetorno>() {
//            @Override
//            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(PersonalizarPets.this, "Pet personalizado", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(PersonalizarPets.this, EditarPerfilPet.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(PersonalizarPets.this, "Erro: " + response.message(), Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ModelRetorno> call, Throwable t) {
//                Toast.makeText(PersonalizarPets.this, "Erro ao Cadastrar: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
