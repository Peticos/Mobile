package com.mobile.peticos.Perfil.Profissional;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mobile.peticos.Perfil.Profissional.Graficos.GraficoFragment;
import com.mobile.peticos.Perfil.Tutor.EditarPerfil;
import com.mobile.peticos.R;

public class PerfilProfissional extends Fragment {




    public PerfilProfissional() {
        // Required empty public constructor
    }

    public static PerfilProfissional newInstance() {
        PerfilProfissional fragment = new PerfilProfissional();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_perfil_profissional, container, false);

        Button graphic = view.findViewById(R.id.area_restrita_button);
        Button novoProduto = view.findViewById(R.id.novo_produto_button);


        ImageView editar = view.findViewById(R.id.imageView24);


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditProfile(view);
            }
        });
        graphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGraphic(view);
            }
        });

        novoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewProduct(view);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void goToEditProfile(View view) {
        Intent intent = new Intent(getActivity(), EditarPerfilProfissional.class);
        startActivity(intent);
    }

    private void goToNewProduct(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, AdicionarProduto.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void goToGraphic(View view) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, GraficoFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}