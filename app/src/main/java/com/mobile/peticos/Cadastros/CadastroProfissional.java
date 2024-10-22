package com.mobile.peticos.Cadastros;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
<<<<<<< HEAD
import android.net.Uri;
=======
import android.content.SharedPreferences;
>>>>>>> 76312e3696b5ddca060882ef5836125117b90647
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;


import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Padrao.Camera;
import com.mobile.peticos.Login;
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

    Button btnCadastrar;

    // Inicializando variáveis
    private ActivityResultLauncher<Intent> cameraLauncher;
    EditText nomeCompleto, nomeUsuario, telefone, email, cnpj;
    AutoCompleteTextView bairro;
    TextInputEditText senha1, senha2;
    TextView senhaInvalida1, senhaInvalida2;
    ImageView btnUpload;
    String url;





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
        btnUpload = findViewById(R.id.upload);
        Bairros();

        // Esconder as mensagens de erro de senha inicialmente
        senhaInvalida1.setVisibility(View.INVISIBLE);
        senhaInvalida2.setVisibility(View.INVISIBLE);

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
            Intent intent = new Intent(CadastroProfissional.this, Camera.class);
            Bundle bundle = new Bundle();
            bundle.putString("tipo", "Profissional");
            intent.putExtras(bundle);
            cameraLauncher.launch(intent); // Apenas lance o Intent sem o código de solicitação
        });




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
                null,
                cnpj.getText().toString()
        );


        Call<Integer> call = aPIPerfil.insertProfissional(perfil);

        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
<<<<<<< HEAD
                    Toast.makeText(CadastroProfissional.this, "Cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(CadastroProfissional.this, DesejaCadastrarUmPet.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", response.body());
                    intent.putExtras(bundle);

                    startActivity(intent);
                    finish();
=======
                    int id = response.body();

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
                                    Intent intent = new Intent(CadastroProfissional.this, DesejaCadastrarUmPet.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("id", id);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    finish();
                                }
>>>>>>> 76312e3696b5ddca060882ef5836125117b90647

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
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(CadastroProfissional.this, "Erro de conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Bairros (){

        // Chamar API de bairros
        String API = "https://apipeticos.onrender.com";
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
        if (cnpj.getText().toString().isEmpty()) {
            cnpj.setError("CNPJ é obrigatório");
            erro = true;
        } else if (!validarCNPJ(cnpj.getText().toString())) {
            cnpj.setError("CNPJ inválido");
            erro = true;
        }

        if (bairro.getText().toString().isEmpty()) {
            bairro.setError("Selecione um bairro");
            erro = true;
        }

        if (!erro) {
            // Verificar se o bairro é válido antes de continuar o cadastro
//            verificarBairro(new CadastroTutor.BairroCallback() {
//                @Override
//                public void onResult(boolean bairroEncontrado) {
//                    if (bairroEncontrado) {
            cadastrarTutorBanco(view); // Continuar com o cadastro
//                    } else {
//                        bairro.setError("Selecione um bairro válido");
//                    }
//                }
//            });
        }
    }
    private boolean validarTelefone(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("[^\\d]", "");
        return phoneNumber.length() == 10 || phoneNumber.length() == 11;
    }
    //verificar se o bairro selecionado esta na api
    private void verificarBairro(CadastroTutor.BairroCallback callback) {
        // URL da API
        String API = "https://apipeticos.onrender.com";
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

    // Função para validar CNPJ
    private boolean validarCNPJ(String cnpj) {
        // Remove qualquer pontuação do CNPJ
        cnpj = cnpj.replaceAll("[^\\d]", "");

        if (cnpj.length() != 14) {
            return false;
        }

        try {
            int[] peso = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

            // Cálculo do primeiro dígito verificador
            int soma = 0;
            for (int i = 0; i < 12; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso[i + 1];
            }

            int resto = soma % 11;
            char digito1 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            // Cálculo do segundo dígito verificador
            soma = 0;
            for (int i = 0; i < 13; i++) {
                soma += Character.getNumericValue(cnpj.charAt(i)) * peso[i];
            }

            resto = soma % 11;
            char digito2 = (resto < 2) ? '0' : (char) ((11 - resto) + '0');

            return (cnpj.charAt(12) == digito1) && (cnpj.charAt(13) == digito2);

        } catch (Exception e) {
            return false;
        }
    }


}