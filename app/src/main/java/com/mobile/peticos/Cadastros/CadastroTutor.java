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
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Login;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroTutor extends AppCompatActivity {
    String txtEmail, textSenhaRepeita, txtSenha;

    Button btnCadastrar;
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tutor);


        txtEmail = ((EditText) findViewById(R.id.email_cadastro)).getText().toString();
        txtSenha = ((EditText) findViewById(R.id.senha_cadastro)).getText().toString();

        //botoes
        btnCadastrar = findViewById(R.id.cadastrar);
        btnCadastrar.setOnClickListener(v -> salvarUsuario());

        //chamando API Bairros
        String API = "https://apipeticosdev.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //criar chamada
        APIBairro apiBairro = retrofit.create(APIBairro.class);
        Call<List<ModelBairro>> call = apiBairro.getAll();

        //executar chamada
        call.enqueue(new Callback<List<ModelBairro>>() {
            @Override
            public void onResponse(Call<List<ModelBairro>> call, retrofit2.Response<List<ModelBairro>> response) {
                List<ModelBairro> bairrosList = response.body();

                // Criar uma lista de strings para armazenar os nomes dos bairros
                List<String> bairrosNomes = new ArrayList<>();

                // Iterar sobre os resultados e pegar os nomes
                for (ModelBairro bairro : bairrosList) {
                    bairrosNomes.add(bairro.getNeighborhood()); // Supondo que o método getNome() existe
                }

                // Converter a lista de bairros em um array de strings
                String[] bairrosArray = bairrosNomes.toArray(new String[0]);

                // Configurar o AutoCompleteTextView com o adapter
                AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        CadastroTutor.this,
                        android.R.layout.simple_dropdown_item_1line,
                        bairrosArray
                );
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setThreshold(1); // Número mínimo de caracteres para mostrar sugestões

            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {

            }
        });




    }
    //public void verificarSenha(View view) {
//
//        // Verificar se as senhas coincidem
//        if (!senha.equals(senhaRepetida)) {
//            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Verificar o comprimento mínimo da senha
//        if (senha.length() < 8) {
//            Toast.makeText(this, "A senha deve ter pelo menos 8 caracteres", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Verificar se a senha contém um número
//        if (!senha.matches(".*\\d.*")) {
//            Toast.makeText(this, "A senha deve conter pelo menos um número", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Verificar se a senha contém uma letra maiúscula
//        if (!senha.matches(".*[A-Z].*")) {
//            Toast.makeText(this, "A senha deve conter pelo menos uma letra maiúscula", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Verificar se a senha contém um caractere especial
//        if (!senha.matches(".*[!@#$%^&*+=?-].*")) {
//            Toast.makeText(this, "A senha deve conter pelo menos um caractere especial (!@#$%^&*+=?-)", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Se passar por todas as verificações, salvar o usuário
//        salvarUsuario();
//        Toast.makeText(this, "Usuário salvo com sucesso!", Toast.LENGTH_SHORT).show();
//    }


    public void EntrarLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }


    private void salvarUsuario() {
        FirebaseAuth autenticator = FirebaseAuth.getInstance();

        // Capturando o texto dos campos de EditText aqui dentro
        String txtEmail = ((EditText) findViewById(R.id.email_cadastro)).getText().toString();
        String txtSenha = ((EditText) findViewById(R.id.senha_cadastro)).getText().toString();

        // Verificar se os campos estão vazios
        if (txtEmail.isEmpty() || txtSenha.isEmpty()) {
            Toast.makeText(CadastroTutor.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criando o usuário com email e senha no Firebase
        autenticator.createUserWithEmailAndPassword(txtEmail, txtSenha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroTutor.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                            // Atualizar o perfil do usuário
                            FirebaseUser userLogin = autenticator.getCurrentUser();
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().build();

                            userLogin.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CadastroTutor.this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CadastroTutor.this, DesejaCadastrarUmPet.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(CadastroTutor.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

