package com.mobile.peticos;

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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.mobile.peticos.Cadastros.CadastroTutor;
import com.mobile.peticos.Home.HomeFragment;
import com.mobile.peticos.R;

public class Login extends AppCompatActivity {
    Button btnSalvar;
    EditText txtEmail, txtSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth autenticator = FirebaseAuth.getInstance();
        FirebaseUser userLogin = autenticator.getCurrentUser();

        btnSalvar = findViewById(R.id.btnentrar);

        if(userLogin != null){
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }


        btnSalvar.setOnClickListener(v ->{
            //Colocar controle para saber se está vazio os campos...
            txtEmail = findViewById(R.id.email);
            txtSenha = findViewById(R.id.senha);
            String email = txtEmail.getText().toString();
            String senha = txtSenha.getText().toString();

            if (email != null && senha != null) {
                //Autenticar usuário
                autenticator.signInWithEmailAndPassword(email, senha)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // abrir a tela principal
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // mostrar erro
                                    String msg = "Deu RED!!";
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthInvalidUserException e) {
                                        msg = "Usuário inválido!";
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        msg = "Senha inválida!";
                                    } catch (Exception e) {
                                        msg = e.getMessage();
                                    }
                                    Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
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