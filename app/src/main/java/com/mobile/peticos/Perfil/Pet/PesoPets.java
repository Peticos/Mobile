package com.mobile.peticos.Perfil.Pet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
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
    WebView webView;
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
        webView = findViewById(R.id.webView);
        webView.loadUrl("https://app.powerbi.com/groups/me/reports/cd340a9f-fd1b-4239-8d75-c71de374f3ce/53275a6882fc9cbb770d?ctid=b148f14c-2397-402c-ab6a-1b4711177ac0&experience=power-bi");
        // Recuperar o ID do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("Pet", Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("id", 0);

        setupRetrofitFeed();
        formatarData(data);
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
            Date data1 = formatoEntrada.parse(data.getText().toString().replaceAll("[^\\d]", ""));

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
    public static void formatarData(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating; // Para evitar chamadas recursivas
            private String old = ""; // Para armazenar o texto anterior
            private final String format = "##/##/####"; // Formato da máscara (dd/MM/yyyy)

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não precisamos de implementação aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Não precisamos de implementação aqui
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) {
                    return; // Evita chamadas recursivas
                }

                String input = s.toString();
                StringBuilder formatted = new StringBuilder();

                // Removendo caracteres não numéricos
                input = input.replaceAll("[^\\d]", "");

                int j = 0;
                for (int i = 0; i < format.length(); i++) {
                    if (format.charAt(i) == '#') {
                        if (j < input.length()) {
                            formatted.append(input.charAt(j));
                            j++;
                        } else {
                            break; // Para quando não há mais dígitos
                        }
                    } else {
                        formatted.append(format.charAt(i)); // Adiciona caracteres da máscara
                    }
                }

                isUpdating = true;
                editText.setText(formatted.toString());
                editText.setSelection(formatted.length()); // Manter o cursor no final
                isUpdating = false;
            }
        });
    }
}