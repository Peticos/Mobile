package com.mobile.peticos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.mobile.peticos.Cadastros.APIs.APIPerfil;
import com.mobile.peticos.Cadastros.APIs.ModelPerfil;
import com.mobile.peticos.Home.HomeFragment;
import com.mobile.peticos.Local.LocalFragment;
import com.mobile.peticos.Perdidos.PerdidoFragment;
import com.mobile.peticos.Perfil.Profissional.PerfilProfissional;
import com.mobile.peticos.Perfil.Tutor.PerfilFragment;

import com.mobile.peticos.Vakinhas.VakinhasFragment;
import com.mobile.peticos.databinding.ActivityMainBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    APIPerfil api;
    Boolean perfilbool = null;

<<<<<<< HEAD
=======
    int id;
>>>>>>> 0d6800ca02b26fccdb4ff1b695203b08d08597b1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

<<<<<<< HEAD
        FirebaseAuth auth = FirebaseAuth.getInstance();
=======
>>>>>>> 0d6800ca02b26fccdb4ff1b695203b08d08597b1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apipeticos.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(APIPerfil.class);

<<<<<<< HEAD
        Call<ModelPerfil> call = api.getById(auth.getCurrentUser().getDisplayName());
        Toast.makeText(this, auth.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
=======

//        Bundle bundle = getIntent().getExtras();
//        id = bundle.getInt("id");

        Call<ModelPerfil> call = api.getById(102);
>>>>>>> 0d6800ca02b26fccdb4ff1b695203b08d08597b1
        call.enqueue(new Callback<ModelPerfil>() {
            @Override
            public void onResponse(Call<ModelPerfil> call, Response<ModelPerfil> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelPerfil perfil = response.body();

                    // Aqui, continue com a lógica para abrir o fragment correto
<<<<<<< HEAD
                    if (perfil.getCnpj().equals("Tutor") || perfil.getCnpj().equals(null)) {
=======
                    if (perfil.getCnpj().equals("Tutor")) {
>>>>>>> 0d6800ca02b26fccdb4ff1b695203b08d08597b1
                        perfilbool = true;
                    } else {
                        perfilbool = false;

                    }
                }else{
                    perfilbool = true;
                }
            }

            @Override
            public void onFailure(Call<ModelPerfil> call, Throwable t) {
                // Trate a falha da chamada à API aqui
                perfilbool = true;

            }
        });

        openFragment(HomeFragment.newInstance());

        binding.bottomNavigationView.setOnItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.navHome) {
                        Fragment homeFragment = HomeFragment.newInstance();
                        openFragment(homeFragment);

                    } else if (item.getItemId() == R.id.navPerdidos) {
                        Fragment perdidosFragment = PerdidoFragment.newInstance();
                        openFragment(perdidosFragment);
                    } else if (item.getItemId() == R.id.navVakinhas) {
                        Fragment homeFragment = VakinhasFragment.newInstance();
                        openFragment(homeFragment);
                    } else if (item.getItemId() == R.id.navLocal) {
                        Fragment homeFragment = LocalFragment.newInstance();
                        openFragment(homeFragment);
                    } else if (item.getItemId() == R.id.navPerfil) {
<<<<<<< HEAD
                        openFragment(PerfilFragment.newInstance());
=======

>>>>>>> 0d6800ca02b26fccdb4ff1b695203b08d08597b1
                        if (perfilbool != null) {
                            if (perfilbool) {
                                openFragment(PerfilFragment.newInstance());
                            } else {
                                openFragment(PerfilProfissional.newInstance());
                            }
                        } else {
                            // Se o perfil ainda não foi carregado, exibe uma mensagem ou faz algo
                            showLoadingMessage();
                        }


                    }
                    return true;
                }
        );
    }


    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Você tem certeza que quer sair?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
        if (1==2){
            super.onBackPressed();

        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.commit();
    }

    private void showLoadingMessage() {
        new AlertDialog.Builder(this)
                .setMessage("Carregando perfil...")
                .setCancelable(false)
                .show();
        Toast.makeText(this, "Pera! Ta carregando", Toast.LENGTH_SHORT).show();
    }
}