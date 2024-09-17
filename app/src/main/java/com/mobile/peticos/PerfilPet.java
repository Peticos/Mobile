package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

public class PerfilPet extends AppCompatActivity {
    ImageView btn_editar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_pet);
        btn_editar = findViewById(R.id.btn_editar);
        btn_editar.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarPerfilPet.class);
            startActivity(intent);
        });
    }
}