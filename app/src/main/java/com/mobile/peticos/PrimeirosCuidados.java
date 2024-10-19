package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.mobile.peticos.Local.LocalFragment;
import com.mobile.peticos.Perdidos.PerdidoFragment;

public class PrimeirosCuidados extends AppCompatActivity {
    Button btnPerdido, btnOng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primeiros_cuidados);

        btnPerdido = findViewById(R.id.btnPerdidos);
        btnOng = findViewById(R.id.btnOng);

        if (btnPerdido != null && btnOng != null) {
            btnPerdido.setOnClickListener(v -> {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, PerdidoFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            });

            btnOng.setOnClickListener(v -> {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, LocalFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }

    }
}