package com.mobile.peticos.Cadastros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.mobile.peticos.Login;
import com.mobile.peticos.R;

public class CadastroProfissional extends AppCompatActivity {

    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_profissional);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(v -> salvarUsuario());

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
    private void salvarUsuario() {
        FirebaseAuth autenticator = FirebaseAuth.getInstance();

        // Capturando o texto dos campos de EditText aqui dentro
        String txtEmail = ((EditText) findViewById(R.id.email)).getText().toString();
        String txtSenha = ((EditText) findViewById(R.id.senha)).getText().toString();

        // Verificar se os campos estão vazios
        if (txtEmail.isEmpty() || txtSenha.isEmpty()) {
            Toast.makeText(CadastroProfissional.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criando o usuário com email e senha no Firebase
        autenticator.createUserWithEmailAndPassword(txtEmail, txtSenha)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroProfissional.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();

                            // Atualizar o perfil do usuário
                            FirebaseUser userLogin = autenticator.getCurrentUser();
                            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().build();

                            userLogin.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(CadastroProfissional.this, "Perfil atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CadastroProfissional.this, DesejaCadastrarUmPet.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(CadastroProfissional.this, "Erro ao cadastrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}