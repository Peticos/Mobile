package com.mobile.peticos.Cadastros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Login;
import com.mobile.peticos.ModelRetorno;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroProfissional extends AppCompatActivity {

    Button btnCadastrar;

    // Inicializando variáveis
    EditText nomeCompleto, nomeUsuario, telefone, email, cnpj;
    AutoCompleteTextView bairro;
    TextInputEditText senha1, senha2;
    TextView senhaInvalida1, senhaInvalida2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_profissional);

        btnCadastrar = findViewById(R.id.cadastrar);
        btnCadastrar.setOnClickListener(v -> validarCampos(v));

        // Inicializando variáveis
        nomeCompleto = findViewById(R.id.nomeCompleto);
        nomeUsuario = findViewById(R.id.nomeUsuario);
        telefone = findViewById(R.id.telefone);
        email = findViewById(R.id.email_cadastro);
        cnpj = findViewById(R.id.cnpj);
        bairro = findViewById(R.id.bairro);
        senha1 = findViewById(R.id.senha_cadastro);
        senha2 = findViewById(R.id.senharepetida_cadastro);
        senhaInvalida1 = findViewById(R.id.senhainalida);
        senhaInvalida2 = findViewById(R.id.senhainalida1);
        Bairros();

        // Esconder as mensagens de erro de senha inicialmente
        senhaInvalida1.setVisibility(View.INVISIBLE);
        senhaInvalida2.setVisibility(View.INVISIBLE);



    }

    public void EntrarLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
    public void CadastraPet(View view) {
        Intent intent = new Intent(this, DesejaCadastrarUmPet.class);
        startActivity(intent);
        finish();
    }
    private void salvarUsuario() {
        FirebaseAuth autenticator = FirebaseAuth.getInstance();

        // Capturando o texto dos campos de EditText aqui dentro



        // Verificar se os campos estão vazios
        if (email.getText().toString().isEmpty() || senha1.getText().toString().isEmpty()) {
            Toast.makeText(CadastroProfissional.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criando o usuário com email e senha no Firebase
        autenticator.createUserWithEmailAndPassword(email.getText().toString(), senha1.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroProfissional.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                            // Atualizar o perfil do usuário
                            FirebaseUser userLogin = autenticator.getCurrentUser();
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().build();

                            userLogin.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(CadastroProfissional.this, DesejaCadastrarUmPet.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(CadastroProfissional.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void cadastrarTutorBanco(View view) {
        // Verificando se os campos obrigatórios estão preenchidos
        if (nomeCompleto.getText().toString().isEmpty() ||
                nomeUsuario.getText().toString().isEmpty() ||
                email.getText().toString().isEmpty() ||
                bairro.getText().toString().isEmpty() ||
                telefone.getText().toString().isEmpty() ||
                cnpj.getText().toString().isEmpty()) {

            Toast.makeText(CadastroProfissional.this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlAPI = "https://apipeticos.onrender.com";
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();


        Retrofit retrofitPerfil = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIPerfil aPIPerfil = retrofitPerfil.create(APIPerfil.class);

        ModelPerfil perfil = new ModelPerfil(
                nomeCompleto.getText().toString(),
                nomeUsuario.getText().toString(),
                email.getText().toString(),
                bairro.getText().toString(),
                "Plano Profissional - Básico",
                telefone.getText().toString(),
                null,
                cnpj.getText().toString()
        );


        Log.d("teste", perfil.toString());
        Call<ModelRetorno> call = aPIPerfil.insertTutor(perfil);

        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    salvarUsuario();
                    Toast.makeText(CadastroProfissional.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroProfissional.this, DesejaCadastrarUmPet.class);
                    startActivity(intent);
                    finish();
                } else {
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
                    Toast.makeText(CadastroProfissional.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable t) {
                Toast.makeText(CadastroProfissional.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Bairros (){

        // Chamar API de bairros
        String API = "https://apipeticosdev.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
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
                            CadastroProfissional.this,
                            android.R.layout.simple_dropdown_item_1line,
                            bairrosNomes
                    );
                    bairro.setAdapter(adapterBairro);
                    bairro.setThreshold(1); // Número mínimo de caracteres para sugestões
                }
            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {
                Toast.makeText(CadastroProfissional.this, "Erro ao carregar bairros", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // Método para validar os campos antes de cadastrar
    private void validarCampos(View view)
    {
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

        if (email.getText().toString().isEmpty()) {
            email.setError("Campo obrigatório");
            erro = true;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("E-mail inválido");
            erro = true;
        }else if (email.getText().toString().length() > 255) {
            email.setError("Excesso de caracteres. Max. 255");
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
                        cadastrarTutorBanco(view); // Continuar com o cadastro
                    } else {
                        bairro.setError("Selecione um bairro válido");
                    }
                }
            });
        }
    }
    private boolean validarTelefone(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        return phoneNumber.length() == 10 || phoneNumber.length() == 11;
    }
    //verificar se o bairro selecionado esta na api
    private void verificarBairro(CadastroTutor.BairroCallback callback) {
        // URL da API
        String API = "https://apipeticosdev.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
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