package com.mobile.peticos.Cadastros;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Padrao.CallBack.AuthCallback;
import com.mobile.peticos.Padrao.MetodosBanco;
import com.mobile.peticos.Padrao.NotificationReciver;
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
    private AutoCompleteTextView bairro;
    private TextView senha1, senha2;
    private static final String CHANNEL_ID = "canal_notificacoes";
    MetodosBanco metodosBanco = new MetodosBanco();


    private String url = null;
    private Metodos metodos = new Metodos();
    private AuthCallback callback;
    private Retrofit retrofit;
    private ActivityResultLauncher<Intent> cameraLauncher;
    private TextView generoobrigatorio;
    private ProgressBar progressBar;
    private Spinner genero_drop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_tutor);
        inicializarComponentes();
        configurarMascaraTelefone();
        configurarCameraLauncher();
        carregarBairros();
        configurarCadastro();
        configurargenero();
        // Criar canal de notificação
        createNotificationChannel();

    }
    // Método para cadastrar o tutor no banco de dados
    private void cadastrarTutorBanco(View view) {


        if (nomeCompleto.getText().toString().isEmpty() ||
                nomeUsuario.getText().toString().isEmpty() ||
                emailCadastro.getText().toString().isEmpty() ||
                bairro.getText().toString().isEmpty() ||
                telefone.getText().toString().isEmpty() ||
                genero_drop.getSelectedItem().toString().isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        String urlAPI = "https://apipeticos.onrender.com";

        Retrofit retrofitPerfil = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPerfil aPIPerfil = retrofitPerfil.create(APIPerfil.class);
        String telefoneFormatado = telefone.getText().toString().replaceAll("[^\\d]", "");
        ModelPerfil perfil = new ModelPerfil(
                nomeCompleto.getText().toString(),
                nomeUsuario.getText().toString().replaceAll("\\s+", ""),
                emailCadastro.getText().toString().replaceAll("\\s+", ""),
                bairro.getText().toString(),
                "Sem Plano",
                telefoneFormatado,
                null,
                url,
                genero_drop.getSelectedItem().toString(),
                null
        );


        progressBar.setVisibility(View.VISIBLE);
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
                            emailCadastro.getText().toString().replaceAll("\\s+", ""),
                            senhaCadastro.getText().toString().replaceAll("\\s+", ""),
                            view.getContext(),
                            new AuthCallback() {
                                @Override
                                public void onSuccess(ModelRetorno perfil) {
                                    Toast.makeText(CadastroTutor.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CadastroTutor.this, DesejaCadastrarUmPet.class);
                                    progressBar.setVisibility(View.GONE);
                                    startActivity(intent);
                                    notificar();
                                    finish();
                                }

                                @Override
                                public void onError(String errorMessage) {
                                    progressBar.setVisibility(View.GONE);
                                    // Lide com o erro, se necessário
                                    Toast.makeText(CadastroTutor.this, "Ocorreu um erro, tente novamente!", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );

                }

                else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(CadastroTutor.this, "Falha no cadastro, tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("CadastroTutor", "Erro: " + t.getMessage());
                Toast.makeText(CadastroTutor.this, "Erro ao tentar cadastrar.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void configurargenero(){
        String[] doseOptions = {"Selecione o seu genero", "Feminino", "Masculino", "Prefiro não dizer", "Outros"};

        // Criação do ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, doseOptions) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0; // Desabilita o primeiro item para não ser clicável
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Define o texto do hint em cinza
                } else {
                    tv.setTextColor(Color.BLACK); // Define os itens selecionáveis em preto
                }
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY); // Hint em cinza
                } else {
                    tv.setTextColor(Color.BLACK); // Itens selecionáveis em preto
                }
                return view;
            }
        };

        // Configura o layout dos itens na lista suspensa
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        genero_drop.setAdapter(adapter);



    }

    // Inicializa os componentes de interface
    private void inicializarComponentes() {
        nomeCompleto = findViewById(R.id.nomeCompleto);
        nomeUsuario = findViewById(R.id.nomeUsuario);
        telefone = findViewById(R.id.telefone);
        bairro = findViewById(R.id.bairro);
        genero_drop = findViewById(R.id.genero);
        emailCadastro = findViewById(R.id.email_cadastro);
        senhaCadastro = findViewById(R.id.senha_cadastro);
        senhaRepetida = findViewById(R.id.senharepetida_cadastro);
        btnCadastrar = findViewById(R.id.cadastrar);
        senha1 = findViewById(R.id.senhainalida);
        senha2 = findViewById(R.id.senhainalida1);
        btnUpload = findViewById(R.id.upload);
        generoobrigatorio = findViewById(R.id.generoobrigatorio);
        generoobrigatorio.setVisibility(View.GONE);


        progressBar = findViewById(R.id.progressBar2);
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
            cameraLauncher.launch(intent);
        });
    }


    // Carrega os bairros usando a API
    private void carregarBairros() {
        String API = "https://apipeticos.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIBairro apiBairro = retrofit.create(APIBairro.class);
        progressBar.setVisibility(View.VISIBLE);
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
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
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
        final boolean[] erro = {false};
        String telefoneFormatado = telefone.getText().toString().replaceAll("[^\\d]", "");

        APIPerfil aPIPerfil = retrofit.create(APIPerfil.class);

        if(url == null){
            Toast.makeText(this, "Imagem Obrigatória", Toast.LENGTH_SHORT).show();
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .transform(new RoundedCorners(30));
            Glide.with(this)
                    .load(R.drawable.adicionar_imagem_vermelho)
                    .apply(options)
                    .into(btnUpload);
            erro[0] = true;
        }
        if (nomeCompleto.getText().toString().isEmpty()) {
            nomeCompleto.setError("Nome completo é obrigatório");
            erro[0] = true;
        } else if (nomeCompleto.getText().toString().length() > 255) {
            nomeCompleto.setError("Excesso de caracteres. Max. 255");
            erro[0] = true;
        }

        if (nomeUsuario.getText().toString().isEmpty()) {
            nomeUsuario.setError("Nome de usuário é obrigatório");
            erro[0] = true;
        } else if (nomeUsuario.getText().toString().length() > 255) {
            nomeUsuario.setError("Excesso de caracteres. Max. 255");
            erro[0] = true;
        }

        aPIPerfil.getByUsername(nomeUsuario.getText().toString()).enqueue(new Callback<ModelPerfil>() {
            @Override
            public void onResponse(Call<ModelPerfil> call, Response<ModelPerfil> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // O nome de usuário já está em uso
                    nomeUsuario.setError("Nome de usuário em uso");
                    erro[0] = true;
                } else if (response.code() == 404) {
                    nomeUsuario.setError(null);
                } else {
                    erro[0] = true;
                    // Resposta inesperada
                    Log.e("ValidarNomeUsuario", "Erro: " + response.code());
                    Toast.makeText(getApplicationContext(), "Erro ao verificar nome de usuário", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelPerfil> call, Throwable t) {
            }
        });

        String generoSelecionado = genero_drop.getSelectedItem().toString().trim();
        if (generoSelecionado.equals("Selecione o seu genero") || generoSelecionado.isEmpty()) {
            generoobrigatorio.setVisibility(View.VISIBLE);
            erro[0] = true;
        } else {
            generoobrigatorio.setVisibility(View.GONE);
        }

        if (telefone.getText().toString().isEmpty() || !validarTelefone(telefoneFormatado)) {
            telefone.setError("Telefone inválido");
            erro[0] = true;
        }

        if (emailCadastro.getText().toString().isEmpty() ||
                !android.util.Patterns.EMAIL_ADDRESS.matcher(emailCadastro.getText().toString()).matches() ||
                emailCadastro.getText().toString().length() > 255) {
            emailCadastro.setError("E-mail inválido ou com excesso de caracteres");
            erro[0] = true;
        }

        if (bairro.getText().toString().isEmpty()) {
            bairro.setError("Selecione um bairro");
            erro[0] = true;
        }
        if(senhaCadastro.getText().toString().replaceAll("\\s+", "").isEmpty()){
            senha1.setVisibility(view.VISIBLE);
            erro[0] = true;
        }
        if(!senhaRepetida.getText().toString().replaceAll("\\s+", "").equals(senhaCadastro.getText().toString().replaceAll("\\s+", "")) || senhaRepetida.getText().toString().replaceAll("\\s+", "").isEmpty()){
            senha2.setVisibility(view.VISIBLE);
            senha1.setVisibility(view.VISIBLE);
            senha2.setText("As senhas nao se coicidem.");
            senha1.setText("As senhas nao se coicidem.");
            erro[0] = true;
        }

        else if (!isStrongPassword(senhaCadastro.getText().toString())) {
            senha2.setText("A senha deve ter pelo menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.");
            senha2.setVisibility(View.VISIBLE);
            senha1.setText("A senha deve ter pelo menos 8 caracteres, incluindo letras maiúsculas, minúsculas, números e caracteres especiais.");
            senha1.setVisibility(View.VISIBLE);
        }


        if (!erro[0]) {
            //             Verificar se o bairro é válido antes de continuar o cadastro
            metodosBanco.verificarBairro(new MetodosBanco.BairroCallback() {
                @Override
                public void onResult(boolean bairroEncontrado) {
                    if (bairroEncontrado) {
                        // Se o bairro for encontrado, prossiga com o cadastro
                        cadastrarTutorBanco(view);
                    } else {
                        // Mostra um erro se o bairro não for válido
                        bairro.setError("Selecione um bairro válido");
                    }
                }
            }, bairro); // Passando o EditText bairro como argumento


        }
    }
    // Método para verificar se a senha é forte
    private boolean isStrongPassword(String password) {
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        return password != null && password.matches(passwordPattern);
    }



    // Método para validar o formato do telefone
    private boolean validarTelefone(String telefone) {
        return android.util.Patterns.PHONE.matcher(telefone).matches() && telefone.length() == 11;
    }

    private void configurarMascaraTelefone() {
        telefone.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private final String mask = "(##) #####-####";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String unmasked = s.toString().replaceAll("[^\\d]", "");
                StringBuilder formatted = new StringBuilder();

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m == '#') {
                        if (i < unmasked.length()) {
                            formatted.append(unmasked.charAt(i));
                            i++;
                        } else {
                            break;
                        }
                    } else {
                        formatted.append(m);
                    }
                }

                isUpdating = true;
                telefone.setText(formatted.toString());
                telefone.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }
    private void notificar() {
        Context context = this;

        Intent intentAndroid = new Intent(context, NotificationReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intentAndroid,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        @SuppressLint("NotificationTrampoline") NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_app_logo)
                .setContentTitle("Bem vindo ao Peticos!")
                .setContentText("Seja muito bem vindo ao melhor app para donas de pets do Brasil!!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        } else {
            // Solicitar permissão se não tiver
            Log.e("SuaActivity", "Permissão de notificações não concedida.");
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notificar",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

}