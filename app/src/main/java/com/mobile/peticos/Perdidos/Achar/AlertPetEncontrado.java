package com.mobile.peticos.Perdidos.Achar;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.mobile.peticos.R;

public class AlertPetEncontrado extends AppCompatActivity {
    Button publicar, sair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_pet_encontrado);

        // Chama o método para mostrar o dialog
        showPetFoundDialog();
    }

    private void showPetFoundDialog() {
        PetFoundDialogFragment dialog = new PetFoundDialogFragment();
        dialog.show(getSupportFragmentManager(), "PetFoundDialog");
    }


}
