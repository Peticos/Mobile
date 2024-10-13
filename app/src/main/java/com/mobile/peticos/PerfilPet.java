package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfilPet extends AppCompatActivity {
    TextView NomePet, sexoPet, idadePet, especiePet, racaPet, corPet, portePet, NomePet2;
    ImageView btneditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_pet);
        Bundle bundle = getIntent().getExtras();

        NomePet = findViewById(R.id.NomePetDois);
        NomePet2 = findViewById(R.id.NomePet);
        sexoPet = findViewById(R.id.sexoPet);
        idadePet = findViewById(R.id.IdadePet);
//        especiePet = findViewById(R.id.EspeciePet);
        racaPet = findViewById(R.id.RacaPet);
        corPet = findViewById(R.id.CorPet);
        portePet = findViewById(R.id.PortPet);
        idadePet = findViewById(R.id.IdadePet);
        btneditar = findViewById(R.id.btneditar);

        NomePet.setText(bundle.getString("nickname"));
        NomePet2.setText(bundle.getString("nickname"));
        sexoPet.setText(bundle.getString("genero"));
        idadePet.setText(String.valueOf(bundle.getInt("idade")));
        racaPet.setText(bundle.getString("raca"));
        corPet.setText(bundle.getString("cor"));
        portePet.setText(bundle.getString("porte"));

        btneditar.setOnClickListener(v -> {
            finish();
        });

    }
}