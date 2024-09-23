package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.peticos.Perfil.Pet.PersonalizarPets;

public class EditarPerfilPet extends AppCompatActivity {
    ImageView btnEdit, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_pet);
        btnEdit = findViewById(R.id.btnEdit);
         btnEdit.setOnClickListener(v -> {
             Intent intent = new Intent(this, PersonalizarPets.class);
             startActivity(intent);
         });

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }
}