package com.mobile.peticos.Perfil.Pet.Vacinas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.Vacinas.ModelDose;
import com.mobile.peticos.Perfil.Pet.Vacinas.ModelVacina;
import com.mobile.peticos.Perfil.Pet.Vacinas.VacinasPets;
import com.mobile.peticos.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class cadastrar_dose extends AppCompatActivity {
    TextView titulo;
    EditText data;
    Button sair, salvar;
    APIPets apiPets;
    int id, dose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_dose);
        titulo = findViewById(R.id.titulo);
        data = findViewById(R.id.data_dose);
        sair = findViewById(R.id.btn_sair_dose);
        salvar = findViewById(R.id.btn_salvar_dose);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}