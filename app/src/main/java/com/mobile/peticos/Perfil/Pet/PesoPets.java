package com.mobile.peticos.Perfil.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.ModelPeso;
import com.mobile.peticos.Perfil.Pet.Vacinas.VacinasPets;
import com.mobile.peticos.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PesoPets extends AppCompatActivity {
    FloatingActionButton btnAdd;
    CardView cardCadastrarPeso;
    Button btnSair, btnSalvar;
    ImageView btn_voltar;
    EditText peso, data;

    APIPets apiPets;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peso_pets);

        btnAdd = findViewById(R.id.btnadd);
        btnSair = findViewById(R.id.btn_sair);
        btnSalvar = findViewById(R.id.btn_salvar);
        cardCadastrarPeso = findViewById(R.id.cardCadastrarPeso);
        btn_voltar = findViewById(R.id.btn_voltar);
        peso = findViewById(R.id.pesoAtual);
        data = findViewById(R.id.data);
        // Recuperar o ID do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Pet", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", 0);

        setupRetrofitFeed();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardCadastrarPeso.setVisibility(View.VISIBLE);
            }
        });
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardCadastrarPeso.setVisibility(View.GONE);
                peso.setText("");
                data.setText("");
            }
        });
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarPeso();
            }
        });
        btn_voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupRetrofitFeed() {
        String API = "https://apipeticos-ltwk.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPets = retrofit.create(APIPets.class);
    }
    public void criarPeso(){

        String dataFormatada = "";

        try {
            // Formato que o usuário digita, por exemplo, "dd/MM/yyyy"
            SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
            Date data1 = formatoEntrada.parse(data.getText().toString());

            // Formato desejado para a saída, por exemplo, "yyyy-MM-dd"
            SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd");
            dataFormatada = formatoSaida.format(data1);

        } catch (ParseException e) {
            e.printStackTrace();
            // Lidar com o erro, por exemplo, avisar que o formato de data está incorreto
        }


        ModelPeso modelPeso = new ModelPeso(
                id,
                Double.parseDouble(peso.getText().toString()),
                dataFormatada);


        Call<ModelRetorno> call = apiPets.insertWeight(modelPeso);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(PesoPets.this, "Cadastrado com sucesso!",  Toast.LENGTH_SHORT). show();
                    cardCadastrarPeso.setVisibility(View.GONE);
                    peso.setText("");
                    data.setText("");
                } else {
                    Log.e("Cadastrar Peso", "Erro: " + response.errorBody().toString());
                    Log.e("Cadastrar Peso", "Erro: " + response.code());


                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Log.e("Cadastrar Peso", "Erro: " + throwable.getMessage());
            }
        });
    }
}