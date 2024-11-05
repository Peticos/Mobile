package com.mobile.peticos.AreaRestrita;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfilAuth;
import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRestrito extends AppCompatActivity {

    Button btnSalvar, btnCadastrar;
    EditText txtEmail, txtSenha;
    TextView btnRestrita;
    TextView senhainvalida;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_restrito);
        btnSalvar = findViewById(R.id.btnentrar);
        btnCadastrar = findViewById(R.id.btnentrar);
        senhainvalida = findViewById(R.id.senhainalida);
        progressBar = findViewById(R.id.progressBar2);

        btnSalvar.setOnClickListener(v -> {
            // Recuperar os campos de texto
            txtEmail = findViewById(R.id.email_adm);
            txtSenha = findViewById(R.id.senha_adm);
            String email = txtEmail.getText().toString().replaceAll("\\s+", "");
            String senha = txtSenha.getText().toString().replaceAll("\\s+", "");

            // Validar os campos antes de autenticar
            if (email.isEmpty()) {
                txtEmail.setError("O campo Email é obrigatório!");
                txtEmail.requestFocus();
                Toast.makeText(LoginRestrito.this, "Por favor, preencha o campo de Email.", Toast.LENGTH_SHORT).show();
            }
            if (senha.isEmpty()) {
                senhainvalida.setVisibility(View.VISIBLE);
                Toast.makeText(LoginRestrito.this, "Por favor, preencha o campo de Senha.", Toast.LENGTH_SHORT).show();
            } else{
                Authentication(v);
            }

        });

    }
    private void Authentication(View view) {
        progressBar.setVisibility(View.VISIBLE);
        String urlAPI = "https://apipeticos-ltwk.onrender.com";
        Retrofit retrofitPerfil = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPerfil aPIPerfil = retrofitPerfil.create(APIPerfil.class);


        ModelLoginRestrita perfil = new ModelLoginRestrita(
                txtEmail.getText().toString().replaceAll("\\s+", ""),
                txtSenha.getText().toString().replaceAll("\\s+", "")
        );


        Call<Integer> call = aPIPerfil.loginAdmin(perfil);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(LoginRestrito.this, AreaRestrita.class);
                    startActivity(intent);
                    finish();


                } else {
                    Toast.makeText(LoginRestrito.this, "Senha ou Email Incorretos", Toast.LENGTH_SHORT).show();
                    Log.d("Login", "Erro: " + response.code());
                    Log.d("Login", "Erro: " + response.message());
                    Log.d("Login", "Erro: " + response.errorBody());
                    Log.d("Login", "Erro: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("CadastroTutor", "Erro: " + t.getMessage());
                Toast.makeText(LoginRestrito.this, "Erro ao tentar Logar.", Toast.LENGTH_SHORT).show();
            }
        });
    }


}