package com.mobile.peticos.PerfilPetPackage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mobile.peticos.EditarPerfilPet;
import com.mobile.peticos.Perdidos.PerdidoFragment;
import com.mobile.peticos.R;


public class PerfilPets extends Fragment {
    ImageView btn_editar;

    public static Fragment newInstance() {

        PerfilPets fragment = new PerfilPets();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_pets, container, false);
        btn_editar = view.findViewById(R.id.btn_editar);
        btn_editar.setOnClickListener(v -> {
            gotToEdit(view);
        });
        return view;
    }
    public void gotToEdit(View view) {
        Intent intent = new Intent(getActivity(), EditarPerfilPet.class);
        startActivity(intent);
    }

}
