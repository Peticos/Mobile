package com.mobile.peticos;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mobile.peticos.Home.HomeFragment;
import com.mobile.peticos.Local.LocalFragment;
import com.mobile.peticos.Perdidos.PerdidoFragment;
import com.mobile.peticos.Perfil.Profissional.PerfilProfissional;
import com.mobile.peticos.Perfil.Tutor.PerfilFragment;
import com.mobile.peticos.Vakinhas.VakinhasFragment;
import com.mobile.peticos.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                        Fragment homeFragment = PerfilProfissional.newInstance();
                        openFragment(homeFragment);
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
}