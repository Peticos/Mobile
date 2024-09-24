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
    EditText nomeCompleto, nomeUsuario, telefone, emailCadastro, senhaCadastro, senhaRepetida;

    View senha1, senha2;

    AutoCompleteTextView bairro;
    Button cadastrarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tutor);


        txtEmail = ((EditText) findViewById(R.id.email_cadastro)).getText().toString();
        txtSenha = ((EditText) findViewById(R.id.senha_cadastro)).getText().toString();

        //botoes
        btnCadastrar = findViewById(R.id.cadastrar);
        senha1 = findViewById(R.id.senhainalida1);
        senha2 = findViewById(R.id.senhainvalida);
        senha1.setVisibility(View.INVISIBLE);
        senha2.setVisibility(View.INVISIBLE);
        btnCadastrar.setOnClickListener(v -> validarCampos());


        nomeCompleto = findViewById(R.id.nomeCompleto);
        nomeUsuario = findViewById(R.id.NomeUsuario);
        telefone = findViewById(R.id.Telefone);
        bairro = findViewById(R.id.autoCompleteTextView);
        emailCadastro = findViewById(R.id.email_cadastro);
        senhaCadastro = findViewById(R.id.senha_cadastro);
        senhaRepetida = findViewById(R.id.senharepetida_cadastro);
        cadastrarButton = findViewById(R.id.cadastrar);
        textSenhaRepeita = senhaRepetida.getText().toString();

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

    private void validarCampos() {
        boolean erro = false;
        // Exemplo de validação para os campos
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

        }else if(!validarTelefone(telefone.getText().toString())){
            telefone.setError("Telefone inválido");
            erro = true;
        }

        if (emailCadastro.getText().toString().isEmpty()) {
            emailCadastro.setError("Campo obrigatorio");
            erro = true;
        }else if(!validarEmail(emailCadastro.getText().toString())){
            emailCadastro.setError("E-mail inválido");
            erro = true;
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailCadastro.getText().toString()).matches()){
            emailCadastro.setError("E-mail ja cadastrado");
            erro = true;
        }

//        if (senhaCadastro.getText().toString().isEmpty()) {
//            senha1.setVisibility(View.VISIBLE);
//            senha2.setVisibility(View.VISIBLE);
//            erro = true;
//
//        }else{
//            senha1.setVisibility(View.INVISIBLE);
//            senha2.setVisibility(View.INVISIBLE);
//
//        }
//
//
//        if (!senhaCadastro.getText().toString().equals(senhaRepetida.getText().toString())) {
//            senha1.setVisibility(View.VISIBLE);
//            senha2.setVisibility(View.VISIBLE);
//            erro = true;
//        }else{
//            senha1.setVisibility(View.INVISIBLE);
//            senha2.setVisibility(View.INVISIBLE);
//        }


        if(bairro.getText().toString().isEmpty()){
            bairro.setError("Selecione um bairro");
            erro = true;

        }
//        if(verificarSenha()){
//            senha1.setVisibility(View.INVISIBLE);
//            senha2.setVisibility(View.INVISIBLE);
//        }else{
//            senha1.setVisibility(View.VISIBLE);
//            senha2.setVisibility(View.VISIBLE);
//            erro = true;
//        }

        if(erro){
            salvarUsuario();
        };



        // Se todos os campos estiverem preenchidos corretamente, prossiga
    }

    public boolean verificarSenha() {
        String txtSenha = senhaCadastro.getText().toString(), textSenhaRepeita = senhaRepetida.getText().toString();

//
//        // Verificar se as senhas coincidem
//        if (!txtSenha.equals(textSenhaRepeita)) {
//            Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
//
//            return false;
//        }
//
//        // Verificar o comprimento mínimo da senha
//        if (txtSenha.length() < 8) {
//            Toast.makeText(this, "A senha deve ter pelo menos 8 caracteres", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        // Verificar se a senha contém um número
//        if (!txtSenha.matches(".*\\d.*")) {
//            Toast.makeText(this, "A senha deve conter pelo menos um número", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        // Verificar se a senha contém uma letra maiúscula
//        if (!txtSenha.matches(".*[A-Z].*")) {
//            Toast.makeText(this, "A senha deve conter pelo menos uma letra maiúscula", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        // Verificar se a senha contém um caractere especial
//        if (!txtSenha.matches(".*[!@#$%^&*+=?-].*")) {
//            Toast.makeText(this, "A senha deve conter pelo menos um caractere especial (!@#$%^&*+=?-)", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        // Se passar por todas as verificações, salvar o usuário
        return true;

    }


    public void EntrarLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }


    private void salvarUsuario() {
        FirebaseAuth autenticator = FirebaseAuth.getInstance();

        // Capturando o texto dos campos de EditText aqui dentro
        String txtEmail = emailCadastro.getText().toString(); // Captura o texto do email diretamente do EditText
        String txtSenha = senhaCadastro.getText().toString(); // Captura o texto da senha
        String nomeUsuario = this.nomeUsuario.getText().toString(); // Captura o texto do nome de usuário

        if(txtEmail.isEmpty() || txtSenha.isEmpty() || nomeUsuario.isEmpty()){
            Toast.makeText(CadastroTutor.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
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
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nomeUsuario) // Define o nome de usuário aqui
                                    .build();

                            userLogin.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    // Método para verificar se o telefone é válido
    private boolean validarTelefone(String phoneNumber) {
        // Remove espaços e pontuações do número
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");

        // Valida o número de telefone brasileiro: 10 ou 11 dígitos
        return phoneNumber.length() == 10 || phoneNumber.length() == 11;
    }
    private boolean validarEmail(String email) {
        // Expressão regular para verificar se o e-mail corresponde a um dos domínios permitidos
        return email.matches("^[\\w-\\.]+@(gmail\\.com|outlook\\.com\\.br|yahoo\\.com|germinare\\.org\\.br)$");
    }
}

