package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfilAuth;
import com.mobile.peticos.Cadastros.Tutor_ou_Profissional;
import com.mobile.peticos.Padrao.MetodosBanco;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    Button btnSalvar, btnCadastrar;
    EditText txtEmail, txtSenha;
    TextView senhainvalida;
    MetodosBanco metodosBanco = new MetodosBanco();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btnSalvar = findViewById(R.id.btnentrar);
        btnCadastrar = findViewById(R.id.btnRegistrar);
        senhainvalida = findViewById(R.id.senhainalida);





        SharedPreferences sharedPreferences = getSharedPreferences("Perfil", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Recupera o e-mail armazenado no SharedPreferences
        String emailLogin = sharedPreferences.getString("email", "");

        // Verifica se o e-mail tem mais de um caractere
        if (emailLogin.length() > 1) {
            // Se o e-mail for válido, exibe uma mensagem de sucesso e navega para a MainActivity
            Toast.makeText(Login.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }







        btnCadastrar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Tutor_ou_Profissional.class);
            startActivity(intent);
        });

        btnSalvar.setOnClickListener(v -> {
            // Recuperar os campos de texto
            txtEmail = findViewById(R.id.email);
            txtSenha = findViewById(R.id.senha);
            String email = txtEmail.getText().toString().trim();
            String senha = txtSenha.getText().toString().trim();

            // Validar os campos antes de autenticar
            if (email.isEmpty()) {
                txtEmail.setError("O campo Email é obrigatório!");
                txtEmail.requestFocus();
                Toast.makeText(Login.this, "Por favor, preencha o campo de Email.", Toast.LENGTH_SHORT).show();
            }
            if (senha.isEmpty()) {
                senhainvalida.setVisibility(View.VISIBLE);
                Toast.makeText(Login.this, "Por favor, preencha o campo de Senha.", Toast.LENGTH_SHORT).show();
            }
            if (email.isEmpty()||senha.isEmpty()) {
                return;
            }else{
                Authentication(v);
            }

        });

    }


    private void Authentication(View view) {

        String urlAPI = "https://apimongo-ghjh.onrender.com/";
        Retrofit retrofitPerfil = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPerfil aPIPerfil = retrofitPerfil.create(APIPerfil.class);
        ModelPerfilAuth perfil = new ModelPerfilAuth(
                txtEmail.getText().toString(),
                txtSenha.getText().toString()
        );

        Call<Integer> call = aPIPerfil.login(perfil);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int id = response.body();
                    if(id != -1){
                        metodosBanco.getPerfil(id, Login.this, new MetodosBanco.PerfilCallback() {
                                    @Override
                                    public void onSuccess(ModelPerfil perfil) {
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
                                        Toast.makeText(Login.this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent( Login.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onError(String errorMessage) {
                                        Toast.makeText(Login.this, "Erro ao tentar Logar.", Toast.LENGTH_SHORT).show();
                                        Log.e("Login", "Erro: " + errorMessage);
                                    }
                                }
                        );
                    }else{
                        Toast.makeText(Login.this, "nao ta no mongo", Toast.LENGTH_SHORT).show();
                    }



                } else {
                        Toast.makeText(Login.this, "Senha ou Email Incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("CadastroTutor", "Erro: " + t.getMessage());
                Toast.makeText(Login.this, "Erro ao tentar Logar.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void TutorOuPeofissional(View view) {
        Intent intent = new Intent(this, Tutor_ou_Profissional.class);
        startActivity(intent);
        finish();
    }

    public void abrirMainTeste(View view) {

    }
}