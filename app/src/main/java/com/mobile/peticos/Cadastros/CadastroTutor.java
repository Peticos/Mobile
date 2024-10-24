package com.mobile.peticos.Cadastros;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Padrao.CallBack.AuthCallback;
import com.mobile.peticos.Padrao.Upload.Camera;
import com.mobile.peticos.Padrao.Metodos;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.content.SharedPreferences;


public class CadastroTutor extends AppCompatActivity {

    // Variáveis de interface
    private ImageView btnUpload;
    private Button btnCadastrar;
    private EditText nomeCompleto, nomeUsuario, telefone, emailCadastro, senhaCadastro, senhaRepetida;
    private AutoCompleteTextView bairro, genero;
    private View senha1, senha2;

    // Variáveis de configuração
    private String url = null;
    private Metodos metodos = new Metodos();
    private AuthCallback callback;
    private Retrofit retrofit;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private List<String> generoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tutor);
        inicializarComponentes();
        configurarCameraLauncher();
        configurarGenero();
        carregarBairros();
        configurarCadastro();
    }
    // Método para cadastrar o tutor no banco de dados
    private void cadastrarTutorBanco(View view) {
        if (nomeCompleto.getText().toString().isEmpty() ||
                nomeUsuario.getText().toString().isEmpty() ||
                emailCadastro.getText().toString().isEmpty() ||
                bairro.getText().toString().isEmpty() ||
                telefone.getText().toString().isEmpty() ||
                genero.getText().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
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
                null,
                url,
                genero.getText().toString(),
                null
        );







        Call<Integer> call = aPIPerfil.insertTutor(perfil);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.code() == 200) {
                    SharedPreferences sharedPreferences = getSharedPreferences("Perfil", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    // Armazenar todas as informações no SharedPreferences
                    editor.putString("nome", perfil.getFullName());
                    editor.putString("nome_usuario", perfil.getUserName());
                    editor.putString("email", perfil.getEmail());
                    editor.putString("bairro", perfil.getBairro());
                    editor.putBoolean("mei", false);
                    editor.putString("telefone", perfil.getTelefone());
                    editor.putString("url", perfil.getProfilePicture());
                    editor.putString("genero", perfil.getGender());
                    editor.putInt("id", response.body());

                    editor.apply();
                    int id = response.body();
                    Metodos metodos = new Metodos();
                    metodos.Authentication(
                            view,
                            id,
                            emailCadastro.getText().toString(),
                            senhaCadastro.getText().toString(),
                            view.getContext(),
                            new AuthCallback() {
                                @Override
                                public void onSuccess(ModelRetorno perfil) {
                                    Toast.makeText(CadastroTutor.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CadastroTutor.this, DesejaCadastrarUmPet.class);
                                    startActivity(intent);
                                    finish();
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    // Lide com o erro, se necessário
                                    Toast.makeText(CadastroTutor.this, "Erro ao autenticar: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                }

                else {
                    Toast.makeText(CadastroTutor.this, "Falha no cadastro, tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("CadastroTutor", "Erro: " + t.getMessage());
                Toast.makeText(CadastroTutor.this, "Erro ao tentar cadastrar.", Toast.LENGTH_SHORT).show();
            }
        });
    }




    // Inicializa os componentes de interface
    private void inicializarComponentes() {
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

        senha1.setVisibility(View.INVISIBLE);
        senha2.setVisibility(View.INVISIBLE);
    }

    // Configura o ActivityResultLauncher para a câmera
    private void configurarCameraLauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            url = data.getStringExtra("url");
                            if (url != null) {
                                url = url.replace("\"", "").replace(" ", "");
                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .transform(new RoundedCorners(30));

                                Glide.with(this)
                                        .load(url)
                                        .apply(options)
                                        .into(btnUpload);
                            }
                        }
                    }
                }
        );

        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroTutor.this, Camera.class);
            Bundle bundle = new Bundle();
            bundle.putString("tipo", "tutor");
            intent.putExtras(bundle);
            cameraLauncher.launch(intent);
        });
    }

    // Configura o AutoCompleteTextView para gênero
    private void configurarGenero() {
        generoList.add("Masculino");
        generoList.add("Feminino");
        generoList.add("Não Binário");
        generoList.add("Prefiro não dizer");

        ArrayAdapter<String> adapterGenero = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                generoList
        );
        genero.setAdapter(adapterGenero);
    }

    // Carrega os bairros usando a API
    private void carregarBairros() {
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
                    ArrayAdapter<String> adapterBairro = new ArrayAdapter<>(
                            CadastroTutor.this,
                            android.R.layout.simple_dropdown_item_1line,
                            bairrosNomes
                    );
                    bairro.setAdapter(adapterBairro);
                    bairro.setThreshold(1);
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

    // Configura o botão de cadastro
    private void configurarCadastro() {
        btnCadastrar.setOnClickListener(v ->
                validarCampos(v));
    }

    // Método para validar os campos antes de cadastrar
    private void validarCampos(View view) {
        boolean erro = false;

        if (nomeCompleto.getText().toString().isEmpty()) {
            nomeCompleto.setError("Nome completo é obrigatório");
            erro = true;
        } else if (nomeCompleto.getText().toString().length() > 255) {
            nomeCompleto.setError("Excesso de caracteres. Max. 255");
            erro = true;
        }

        if (nomeUsuario.getText().toString().isEmpty()) {
            nomeUsuario.setError("Nome de usuário é obrigatório");
            erro = true;
        } else if (nomeUsuario.getText().toString().length() > 255) {
            nomeUsuario.setError("Excesso de caracteres. Max. 255");
            erro = true;
        }

        if (genero.getText().toString().isEmpty() || !generoList.contains(genero.getText().toString())) {
            genero.setError("Genero inválido");
            erro = true;
        }

        if (telefone.getText().toString().isEmpty() || !validarTelefone(telefone.getText().toString())) {
            telefone.setError("Telefone inválido");
            erro = true;
        }

        if (emailCadastro.getText().toString().isEmpty() ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(emailCadastro.getText().toString()).matches() ||
                emailCadastro.getText().toString().length() > 255) {
            emailCadastro.setError("E-mail inválido ou com excesso de caracteres");
            erro = true;
        }

        if (bairro.getText().toString().isEmpty()) {
            bairro.setError("Selecione um bairro");
            erro = true;
        }

        if (!erro) {
            cadastrarTutorBanco(view);
        }
    }


    // Método para validar o formato do telefone
    private boolean validarTelefone(String telefone) {
        return android.util.Patterns.PHONE.matcher(telefone).matches() && telefone.length() <= 15;
    }
}