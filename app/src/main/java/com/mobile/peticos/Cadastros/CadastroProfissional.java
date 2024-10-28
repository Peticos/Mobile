package com.mobile.peticos.Cadastros;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.MainActivity;
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

public class CadastroProfissional extends AppCompatActivity {

    // Variáveis de layout e lógica
    private Button btnCadastrar;
    private EditText nomeCompleto, nomeUsuario, telefone, email, cnpj;
    private AutoCompleteTextView bairro;
    private TextInputEditText senha1, senha2;
    private TextView senhaInvalida1, senhaInvalida2;
    private ImageView btnUpload;
    private String url;
    private ProgressBar progressBar;
    private ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_profissional);

        // Inicialização de variáveis e configuração dos listeners
        inicializarComponentes();
        configurarCameraLauncher();
        configurarBairros();
    }

    // Método para inicializar os componentes
    private void inicializarComponentes() {
        btnCadastrar = findViewById(R.id.cadastrar);
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
        btnUpload = findViewById(R.id.upload);
        progressBar = findViewById(R.id.progressBar2);

        // Esconder as mensagens de erro de senha inicialmente
        senhaInvalida1.setVisibility(View.INVISIBLE);
        senhaInvalida2.setVisibility(View.INVISIBLE);

        // Listener para o botão de cadastro
        btnCadastrar.setOnClickListener(v -> validarCampos(v));

        // Listener para o upload da imagem
        btnUpload.setOnClickListener(v -> {
            Intent intent = new Intent(CadastroProfissional.this, Camera.class);
            cameraLauncher.launch(intent);
        });
    }

    // Método para configurar o launcher da câmera
    private void configurarCameraLauncher() {
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            url = data.getStringExtra("url");
                            if (url != null) {
                                url = url.replace("\"", "").trim();
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
    }

    // Método para configurar a chamada da API dos bairros
    private void configurarBairros() {
        String API = "https://apipeticos-ltwk.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIBairro apiBairro = retrofit.create(APIBairro.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<List<ModelBairro>> call = apiBairro.getAll();

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
                            CadastroProfissional.this,
                            android.R.layout.simple_dropdown_item_1line,
                            bairrosNomes
                    );
                    bairro.setAdapter(adapterBairro);
                    bairro.setThreshold(1);
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CadastroProfissional.this, "Erro ao carregar bairros", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para validar os campos antes de cadastrar
    private void validarCampos(View view) {
        boolean erro = false;

        // Validação dos campos de entrada
        if (validarCampo(nomeCompleto, 255, "Nome completo é obrigatório")) erro = true;
        if (validarCampo(nomeUsuario, 255, "Nome de usuário é obrigatório")) erro = true;
        if (validarCampoTelefone(telefone)) erro = true;
        if (validarCampoEmail(email)) erro = true;
        if (validarCampoCNPJ(cnpj)) erro = true;
        if (bairro.getText().toString().isEmpty()) {
            bairro.setError("Selecione um bairro");
            erro = true;
        }

        // Se não houver erros, prosseguir com o cadastro
        if (!erro) {
            cadastrarTutorBanco(view);
        }
    }

    // Métodos de validação dos campos
    private boolean validarCampo(EditText campo, int maxLength, String errorMsg) {
        if (campo.getText().toString().isEmpty()) {
            campo.setError(errorMsg);
            return true;
        } else if (campo.getText().toString().length() > maxLength) {
            campo.setError("Excesso de caracteres. Max. " + maxLength);
            return true;
        }
        return false;
    }

    private boolean validarCampoTelefone(EditText telefone) {
        String phoneNumber = telefone.getText().toString().replaceAll("[^\\d]", "");
        if (phoneNumber.isEmpty()) {
            telefone.setError("Telefone é obrigatório");
            return true;
        } else if (phoneNumber.length() != 10 && phoneNumber.length() != 11) {
            telefone.setError("Telefone inválido");
            return true;
        }
        return false;
    }

    private boolean validarCampoEmail(EditText email) {
        if (email.getText().toString().isEmpty()) {
            email.setError("Campo obrigatório");
            return true;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("E-mail inválido");
            return true;
        } else if (email.getText().toString().length() > 255) {
            email.setError("Excesso de caracteres. Max. 255");
            return true;
        }
        return false;
    }

    private boolean validarCampoCNPJ(EditText cnpj) {
        String cnpjText = cnpj.getText().toString().replaceAll("[^\\d]", "");
        if (cnpjText.isEmpty()) {
            cnpj.setError("CNPJ é obrigatório");
            return true;
        } else if (!validarCNPJ(cnpjText)) {
            cnpj.setError("CNPJ inválido");
            return true;
        }
        return false;
    }

    private boolean validarCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("[^\\d]", "");
        if (cnpj.length() != 14) return false;

        try {
            int[] peso = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso[i + 1];
            }
            int resto = soma % 11;
            char digito1 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso[i];
            }
            resto = soma % 11;
            char digito2 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            return cnpj.charAt(12) == digito1 && cnpj.charAt(13) == digito2;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para cadastrar o tutor no banco de dados
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

        String urlAPI = "https://apipeticos-ltwk.onrender.com";


        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofitPerfil = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIPerfil aPIPerfil = retrofitPerfil.create(APIPerfil.class);

        ModelPerfil perfil = new ModelPerfil(
                nomeCompleto.getText().toString(),
                nomeUsuario.getText().toString(),
                email.getText().toString(),
                bairro.getText().toString(),
                "Sem Plano",
                telefone.getText().toString(),
                null,
                url,
                cnpj.getText().toString()
        );

        Call<Integer> call = aPIPerfil.insertProfissional(perfil);
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
                    editor.putBoolean("mei", true);
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
                            email.getText().toString(),
                            senha1.getText().toString(),
                            view.getContext(),
                            new AuthCallback() {
                                @Override
                                public void onSuccess(ModelRetorno perfil) {
                                    Toast.makeText(CadastroProfissional.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CadastroProfissional.this, MainActivity.class);
                                    startActivity(intent);
                                    progressBar.setVisibility(View.GONE);
                                    finish();
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    progressBar.setVisibility(View.GONE);
                                    // Lide com o erro, se necessário
                                    Toast.makeText(CadastroProfissional.this, "Erro ao autenticar: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CadastroProfissional.this, "Falha no cadastro, tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("CadastroTutor", "Erro: " + t.getMessage());
                Toast.makeText(CadastroProfissional.this, "Erro ao tentar cadastrar.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}