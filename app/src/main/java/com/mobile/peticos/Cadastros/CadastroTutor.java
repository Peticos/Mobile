package com.mobile.peticos.Cadastros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Login;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroTutor extends AppCompatActivity {

    // Declaração de variáveis
    String txtEmail, textSenhaRepetida, txtSenha;
    Button btnCadastrar;
    Retrofit retrofit;
    EditText nomeCompleto, nomeUsuario, telefone, emailCadastro, senhaCadastro, senhaRepetida;
    AutoCompleteTextView bairro, genero;
    View senha1, senha2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tutor);

        // Inicializando variáveis de interface
        nomeCompleto = findViewById(R.id.nomeCompleto);
        nomeUsuario = findViewById(R.id.NomeUsuario);
        telefone = findViewById(R.id.Telefone);
        bairro = findViewById(R.id.autoCompleteTextView);
        genero = findViewById(R.id.genero);
        emailCadastro = findViewById(R.id.email_cadastro);
        senhaCadastro = findViewById(R.id.senha_cadastro);
        senhaRepetida = findViewById(R.id.senharepetida_cadastro);
        btnCadastrar = findViewById(R.id.cadastrar);
        senha1 = findViewById(R.id.senhainalida1);
        senha2 = findViewById(R.id.senhainvalida);

        // Esconder as mensagens de erro de senha inicialmente
        senha1.setVisibility(View.INVISIBLE);
        senha2.setVisibility(View.INVISIBLE);

        // Configuração do Spinner de gênero
        List<String> generoList = new ArrayList<>();
        generoList.add("Masculino");
        generoList.add("Feminino");
        generoList.add("Não Binário");
        generoList.add("Prefiro não dizer");

        ArrayAdapter<String> adapterGenero = new ArrayAdapter<>(
                CadastroTutor.this,
                android.R.layout.simple_dropdown_item_1line,
                generoList
        );
        genero.setAdapter(adapterGenero);

        // Chamar API de bairros
        String API = "https://apipeticosdev.onrender.com";
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
                            CadastroTutor.this,
                            android.R.layout.simple_dropdown_item_1line,
                            bairrosNomes
                    );
                    bairro.setAdapter(adapterBairro);
                    bairro.setThreshold(1); // Número mínimo de caracteres para sugestões
                }
            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {
                Toast.makeText(CadastroTutor.this, "Erro ao carregar bairros", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar o evento do botão de cadastro
        btnCadastrar.setOnClickListener(v -> validarCampos(v));
    }

    // Método para validar os campos antes de cadastrar
    private void validarCampos(View view) {
        boolean erro = false;

        if (nomeCompleto.getText().toString().isEmpty()) {
            nomeCompleto.setError("Nome completo é obrigatório");
            erro = true;
        }

        if (nomeUsuario.getText().toString().isEmpty()) {
            nomeUsuario.setError("Nome de usuário é obrigatório");
            erro = true;
        }

        if (telefone.getText().toString().isEmpty()) {
            telefone.setError("Telefone é obrigatório");
            erro = true;
        } else if (!validarTelefone(telefone.getText().toString())) {
            telefone.setError("Telefone inválido");
            erro = true;
        }

        if (emailCadastro.getText().toString().isEmpty()) {
            emailCadastro.setError("Campo obrigatório");
            erro = true;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailCadastro.getText().toString()).matches()) {
            emailCadastro.setError("E-mail inválido");
            erro = true;
        }

        if (bairro.getText().toString().isEmpty()) {
            bairro.setError("Selecione um bairro");
            erro = true;
        }

        // Se não houve erros, salvar o usuário
        if (!erro) {
            salvarUsuario(view);
        }
    }

    // Método para salvar o usuário no Firebase e no banco
    private void salvarUsuario(View view) {
        FirebaseAuth autenticator = FirebaseAuth.getInstance();
        String txtEmail = emailCadastro.getText().toString();
        String txtSenha = senhaCadastro.getText().toString();
        String nomeUsuario = this.nomeUsuario.getText().toString();

        if (txtEmail.isEmpty() || txtSenha.isEmpty() || nomeUsuario.isEmpty()) {
            Toast.makeText(CadastroTutor.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        autenticator.createUserWithEmailAndPassword(txtEmail, txtSenha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser userLogin = autenticator.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(nomeUsuario)
                                .build();

                        if (userLogin != null) {
                            userLogin.updateProfile(profileUpdates).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    cadastrarTutorBanco(view);
                                }
                            });
                        }
                    } else {
                        Toast.makeText(CadastroTutor.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Método para inserir o tutor no banco via Retrofit
    private void cadastrarTutorBanco(View view) {
        APIPerfil aPIPerfil = retrofit.create(APIPerfil.class);
        ModelPerfil perfil = new ModelPerfil(
                bairro.getText().toString(),
                nomeCompleto.getText().toString(),
                nomeUsuario.getText().toString(),
                emailCadastro.getText().toString(),
                genero.getText().toString(),
                telefone.getText().toString(),
                "Sem plano"
        );

        Call<ModelPerfil> call = aPIPerfil.insert(perfil);

        call.enqueue(new Callback<ModelPerfil>() {
            @Override
            public void onResponse(Call<ModelPerfil> call, Response<ModelPerfil> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroTutor.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroTutor.this, DesejaCadastrarUmPet.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(CadastroTutor.this, "Erro ao cadastrar: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPerfil> call, Throwable t) {
                Toast.makeText(CadastroTutor.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Verifica se o telefone é válido (10 ou 11 dígitos)
    private boolean validarTelefone(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        return phoneNumber.length() == 10 || phoneNumber.length() == 11;
    }
}
