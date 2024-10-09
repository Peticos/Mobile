package com.mobile.peticos.Cadastros;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Camera;
import com.mobile.peticos.ModelRetorno;
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
    ImageView btnUpload;
    private ActivityResultLauncher<Intent> cameraLauncher;

    Button btnCadastrar;
    String url;
    Retrofit retrofit;
    EditText nomeCompleto, nomeUsuario, telefone, emailCadastro, senhaCadastro, senhaRepetida;
    AutoCompleteTextView bairro, genero;
    View senha1, senha2;
    List<String> generoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tutor);

        // Inicializando variáveis de interface
        nomeCompleto = findViewById(R.id.nomeCompleto);
        nomeUsuario = findViewById(R.id.nomeUsuario);
        telefone = findViewById(R.id.telefone);
        bairro = findViewById(R.id.bairro);
        genero = findViewById(R.id.genero);
        emailCadastro = findViewById(R.id.email_cadastro);
        senhaCadastro = findViewById(R.id.senha_cadastro);
        senhaRepetida = findViewById(R.id.senharepetida_cadastro);
        btnCadastrar = findViewById(R.id.cadastrar);
        senha1 = findViewById(R.id.senhainalida1);
        senha2 = findViewById(R.id.senhainalida);
        btnUpload = findViewById(R.id.upload);
        url = null;

        // Inicializa o ActivityResultLauncher
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            url = data.getStringExtra("url"); // Obter a URL do Intent
                            if(url != null){
                                url.replace("\"", "");
                                url.replace(" ", "");
                                RequestOptions options = new RequestOptions()
                                        .centerCrop() // Garante que a imagem preencha o espaço
                                        .transform(new RoundedCorners(30)); // Aplica a transformação de cantos arredondados

                                Glide.with(this)
                                        .load(url)
                                        .apply(options)
                                        .into(btnUpload);

                            }

                        }
                    }
                }
        );


        //upload da imagem
        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroTutor.this, Camera.class);
            Bundle bundle = new Bundle();
            bundle.putString("tipo", "tutor");
            intent.putExtras(bundle);
            cameraLauncher.launch(intent); // Apenas lance o Intent sem o código de solicitação
        });



        // Esconder as mensagens de erro de senha inicialmente
        senha1.setVisibility(View.INVISIBLE);
        senha2.setVisibility(View.INVISIBLE);

        // Configuração do Spinner de gênero

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
        if(genero.getText().toString().isEmpty()){
            genero.setError("Genero é obrigatório");
            erro = true;
        }else if(!generoList.contains(genero.getText().toString())){
            genero.setError("Genero inválido");
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
        }else if (emailCadastro.getText().toString().length() > 255) {
            emailCadastro.setError("Excesso de caracteres. Max. 255");
            erro = true;
        }

        if (bairro.getText().toString().isEmpty()) {
            bairro.setError("Selecione um bairro");
            erro = true;
        }

        if (!erro) {
            // Verificar se o bairro é válido antes de continuar o cadastro
            verificarBairro(new BairroCallback() {
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


    // Método para salvar o usuário no Firebase e no banco
    private void salvarUsuarioFireBase(View view) {
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
                                .setPhotoUri(Uri.parse(url))
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




    private void cadastrarTutorBanco(View view) {
        // Verificando se os campos obrigatórios estão preenchidos
        if (nomeCompleto.getText().toString().isEmpty() ||
                nomeUsuario.getText().toString().isEmpty() ||
                emailCadastro.getText().toString().isEmpty() ||
                bairro.getText().toString().isEmpty() ||
                telefone.getText().toString().isEmpty() ||
                genero.getText().toString().isEmpty()) {

            Toast.makeText(CadastroTutor.this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlAPI = "https://apipeticos.onrender.com";

        Retrofit retrofitPerfil = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIPerfil aPIPerfil = retrofitPerfil.create(APIPerfil.class);

        ModelPerfil perfil = new ModelPerfil(
                nomeCompleto.getText().toString(),
                nomeUsuario.getText().toString(),
                emailCadastro.getText().toString(),
                bairro.getText().toString(),
                "Sem Plano",
                telefone.getText().toString(),
                genero.getText().toString(),
                null
        );


        Log.d("teste", perfil.toString());
        Call<ModelRetorno> call = aPIPerfil.insertTutor(perfil);

        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    salvarUsuarioFireBase(view);
                    Toast.makeText(CadastroTutor.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroTutor.this, DesejaCadastrarUmPet.class);
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
                    Toast.makeText(CadastroTutor.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable t) {
                Toast.makeText(CadastroTutor.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    // Verifica se o telefone é válido (10 ou 11 dígitos)
    private boolean validarTelefone(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        return phoneNumber.length() == 10 || phoneNumber.length() == 11;
    }

    //verificar se o bairro selecionado esta na api
    private void verificarBairro(BairroCallback callback) {
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

    // Interface de callback para a verificação de bairro
    public interface BairroCallback {
        void onResult(boolean bairroEncontrado);
    }
    // Método para validar se a senha é forte
    private boolean validarSenhaForte(String senha) {
        if (senha.length() < 8) {
            return false;
        }

        boolean temLetraMaiuscula = false;
        boolean temLetraMinuscula = false;
        boolean temNumero = false;
        boolean temCaractereEspecial = false;

        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                temLetraMaiuscula = true;
            } else if (Character.isLowerCase(c)) {
                temLetraMinuscula = true;
            } else if (Character.isDigit(c)) {
                temNumero = true;
            } else if (!Character.isLetterOrDigit(c)) {
                temCaractereEspecial = true;
            }
        }

        return temLetraMaiuscula && temLetraMinuscula && temNumero && temCaractereEspecial;
    }
}