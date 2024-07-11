package com.mobile.peticos;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.peticos.Home.HomeFragment;
import com.mobile.peticos.Local.LocalFragment;
import com.mobile.peticos.Perdidos.PerdidoFragment;
import com.mobile.peticos.Perfil.FeedDoPet;
import com.mobile.peticos.Perfil.PerfilFragment;
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
                        Fragment homeFragment = FeedDoPet.newInstance();
                        openFragment(homeFragment);
                    }
                    return true;
                }
        );
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}