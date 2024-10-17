package com.mobile.peticos.Perfil.Profissional;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Cadastros.CadastroTutor;
import com.mobile.peticos.Cadastros.DesejaCadastrarUmPet;
import com.mobile.peticos.Camera;
import com.mobile.peticos.Perfil.Tutor.EditarPerfil;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditarPerfilProfissional extends AppCompatActivity {
    EditText nomeCompleto, nomeUsuario, telefone, cnpj;
    Button btAtualizar;
    AutoCompleteTextView bairro;
    ImageView voltar, btUpload;
    Retrofit retrofit;
    String emailUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_profissional);

        carregarDadosDoPerfil();

        nomeCompleto = findViewById(R.id.NomeCompleto);
        nomeUsuario = findViewById(R.id.NomeUsuario);
        telefone = findViewById(R.id.Telefone);
        bairro = findViewById(R.id.Bairro);
        cnpj = findViewById(R.id.cnpj);

        btUpload = findViewById(R.id.btUpload);
        btAtualizar = findViewById(R.id.btAtualizar);


        voltar = findViewById(R.id.imageView22);

        voltar.setOnClickListener(v -> {
            finish();
        });

        //upload da imagem
        btUpload.setOnClickListener(v -> {
            Intent intent = new Intent(EditarPerfilProfissional.this, Camera.class);
            startActivity(intent);
        });

        // Chamar API de bairros
        String API = "https://apipeticos.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIBairro apiBairro = retrofit.create(APIBairro.class);
        Call<List<ModelBairro>> call = apiBairro.getAll();

        // Executar chamada para pegar os bairros
        call.enqueue(new Callback<List<ModelBairro>>() {
            @Override
            public void onResponse(Call<List<ModelBairro>> call, Response<List<ModelBairro>> response) {
                List<ModelBairro> bairrosList = response.body();
                List<String> bairrosNomes = new ArrayList<>();

                if (bairrosList != null) {
                    for (ModelBairro bairro : bairrosList) {
                        bairrosNomes.add(bairro.getNeighborhood());
                    }

                    // Configurando o AutoCompleteTextView com o adapter
                    ArrayAdapter<String> adapterBairro = new ArrayAdapter<>(
                            EditarPerfilProfissional.this,
                            android.R.layout.simple_dropdown_item_1line,
                            bairrosNomes
                    );
                    bairro.setAdapter(adapterBairro);
                    bairro.setThreshold(1); // Número mínimo de caracteres para sugestões
                }
            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {
                Toast.makeText(EditarPerfilProfissional.this, "Erro ao carregar bairros", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar o evento do botão de cadastro
        btAtualizar.setOnClickListener(v -> validarCampos(v));
    }
    private void carregarDadosDoPerfil(){
        String urlAPI = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIPerfil api = retrofit.create(APIPerfil.class);

        FirebaseAuth autenticator = FirebaseAuth.getInstance();

        //Chamada para buscar o perfil pelo nome de usuário
        Call<ModelPerfil> call = api.getByUsername(autenticator.getCurrentUser().getDisplayName());
        call.enqueue(new Callback<ModelPerfil>() {
            @Override
            public void onResponse(Call<ModelPerfil> call, Response<ModelPerfil> response) {
                if(response.isSuccessful() && response.body() != null) {
                    ModelPerfil model = response.body();
                    emailUser = model.email;

                    //Preencher os campos com os dados do perfil
                    nomeCompleto.setText(model.fullName);
                    nomeUsuario.setText(model.username);
                    bairro.setText(model.bairro);
                    telefone.setText(model.phone);
                    cnpj.setText(model.cnpj);

                }else{
                    Toast.makeText(EditarPerfilProfissional.this, "Erro ao carregar perfil", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ModelPerfil> call, Throwable t) {
                Toast.makeText(EditarPerfilProfissional.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para validar os campos antes de cadastrar
    private void validarCampos(View view) {
        boolean erro = false;

        if (nomeCompleto.getText().toString().isEmpty()) {
            nomeCompleto.setError("Nome completo é obrigatório");
            erro = true;
        }else if (nomeCompleto.getText().toString().length() > 255) {
            nomeCompleto.setError("Excesso de caracteres. Max. 255");
            erro = true;
        }

        if (nomeUsuario.getText().toString().isEmpty()) {
            nomeUsuario.setError("Nome de usuário é obrigatório");
            erro = true;
        }else if (nomeUsuario.getText().toString().length() > 255) {
            nomeUsuario.setError("Excesso de caracteres. Max. 255");
            erro = true;
        }

        if (telefone.getText().toString().isEmpty()) {
            telefone.setError("Telefone é obrigatório");
            erro = true;
        } else if (!validarTelefone(telefone.getText().toString())) {
            telefone.setError("Telefone inválido");
            erro = true;
        }

        if (bairro.getText().toString().isEmpty()) {
            bairro.setError("Selecione um bairro");
            erro = true;
        }

        if (!erro) {
            // Verificar se o bairro é válido antes de continuar o cadastro
            verificarBairro(new CadastroTutor.BairroCallback() {
                @Override
                public void onResult(boolean bairroEncontrado) {
                    if (bairroEncontrado) {
                        atualizarTutorBanco(view); // Continuar com o cadastro
                    } else {
                        bairro.setError("Selecione um bairro válido");
                    }
                }
            });
        }
    }

    private void atualizarTutorBanco(View view) {

        String urlAPI = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIPerfil api = retrofit.create(APIPerfil.class);

        ModelPerfil perfil = new ModelPerfil(
                0,
                nomeCompleto.getText().toString(),
                nomeUsuario.getText().toString(),
                emailUser,
                bairro.getText().toString(),
                "Sem Plano",
                telefone.getText().toString(),
                null,
                9,
                cnpj.getText().toString()
        );

        Log.d("EDITAR_PERFIL", perfil.toString());
        Call<ModelPerfil> call = api.update(perfil.getId() , perfil);

        call.enqueue(new Callback<ModelPerfil>() {
            @Override
            public void onResponse(Call<ModelPerfil> call, Response<ModelPerfil> response) {
                if(response.isSuccessful() && response.body() != null) {
                    Toast.makeText(EditarPerfilProfissional.this, "Perfil Editado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditarPerfilProfissional.this, DesejaCadastrarUmPet.class);
                    startActivity(intent);
                    finish();
                } else{
                    String errorMessage;
                    switch (response.code()) {
                        case 400:
                            errorMessage = "Erro 400: Requisição inválida. Verifique os dados.";
                            break;
                        case 404:
                            errorMessage = "Erro 404: Servidor não encontrado.";
                            break;
                        case 500:
                            errorMessage = "Erro 500: Erro no servidor.";
                            break;
                        default:
                            errorMessage = "Erro ao cadastrar: " + response.code();
                            break;
                    }
                    Toast.makeText(EditarPerfilProfissional.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPerfil> call, Throwable throwable) {
                Toast.makeText(EditarPerfilProfissional.this, "Erro de conexão: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validarTelefone(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        return phoneNumber.length() == 10 || phoneNumber.length() == 11;
    }

    private void verificarBairro(CadastroTutor.BairroCallback callback) {
        // URL da API
        String API = "https://apipeticos.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Criar chamada
        APIBairro apiBairro = retrofit.create(APIBairro.class);
        Call<List<ModelBairro>> call = apiBairro.getAll();

        // Defina o bairro que você deseja verificar
        String bairroProcurado = bairro.getText().toString();

        // Executar chamada da API
        call.enqueue(new Callback<List<ModelBairro>>() {
            @Override
            public void onResponse(Call<List<ModelBairro>> call, retrofit2.Response<List<ModelBairro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ModelBairro> bairrosList = response.body();

                    // Verificar se o bairro está presente
                    boolean bairroEncontrado = false;
                    for (ModelBairro bairro : bairrosList) {
                        if (bairroProcurado.equalsIgnoreCase(bairro.getNeighborhood())) {
                            bairroEncontrado = true;
                            break;
                        }
                    }

                    // Chamar o callback com o resultado
                    callback.onResult(bairroEncontrado);
                } else {
                    callback.onResult(false);
                }
            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {
                throwable.printStackTrace();
                callback.onResult(false);
            }
        });
    }
}