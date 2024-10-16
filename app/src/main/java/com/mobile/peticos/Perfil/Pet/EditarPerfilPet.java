package com.mobile.peticos.Perfil.Pet;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.mobile.peticos.R;

public class EditarPerfilPet extends AppCompatActivity {
    ImageView btnEdit, btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_pet);
        Bundle bundle = getIntent().getExtras();

        btnEdit = findViewById(R.id.btnEdit);
         btnEdit.setOnClickListener(v -> {

             Intent intent = new Intent(v.getContext(), EditarPerfilPet.class);
             Bundle bundlePersonalizar = new Bundle();
             bundlePersonalizar.putInt("id", bundle.getInt("id"));
             intent.putExtras(bundlePersonalizar);
             v.getContext().startActivity(intent);
             Intent intentPersonalizar = new Intent(this, PersonalizarPets.class);
             startActivity(intentPersonalizar);
         });

        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            finish();
        });
    }
}