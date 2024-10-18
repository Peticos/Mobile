package com.mobile.peticos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfilAuth;
import com.mobile.peticos.Cadastros.CadastroTutor;
import com.mobile.peticos.Cadastros.DesejaCadastrarUmPet;
import com.mobile.peticos.Home.HomeFragment;
import com.mobile.peticos.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {
    Button btnSalvar, btnCadastrar;
    EditText txtEmail, txtSenha;
    TextView senhainvalida;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth autenticator = FirebaseAuth.getInstance();
        FirebaseUser userLogin = autenticator.getCurrentUser();

        btnSalvar = findViewById(R.id.btnentrar);
        btnCadastrar = findViewById(R.id.btnRegistrar);
        senhainvalida = findViewById(R.id.senhainalida);


        if (userLogin != null) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
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

        Call<ModelRetorno> call = aPIPerfil.login(perfil);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful()) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                        Toast.makeText(Login.this, "Senha ou Email Incorretos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable t) {
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