package com.mobile.peticos.Perfil.Tutor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.mobile.peticos.R;

public class EditarPerfil extends AppCompatActivity {

    ImageView voltar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        voltar = findViewById(R.id.imageView22);

        voltar.setOnClickListener(v -> {
            finish();
        });


    }
}