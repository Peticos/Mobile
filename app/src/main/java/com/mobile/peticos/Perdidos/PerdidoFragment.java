package com.mobile.peticos.Perdidos;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mobile.peticos.AdicionarAoFeedTriste;
import com.mobile.peticos.Perfil.Profissional.Graficos.GraficoFragment;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

public class PerdidoFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterPerdidos adapter;
    private List<PetPerdido> petList;

    private ImageButton bt_adicionar;

    public PerdidoFragment() {
        // Required empty public constructor
    }

    public static PerdidoFragment newInstance() {
        return new PerdidoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perdido, container, false);

        recyclerView = view.findViewById(R.id.RecyclerViewPetsPerdidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicialize a lista de pets perdidos
        petList = new ArrayList<>();

        // Adicione exemplos de pets na lista
        petList.add(new PetPerdido(
                R.drawable.user1, // Exemplo de imagem do usuário
                R.drawable.pet_perdido1, // Exemplo de imagem principal
                "geogeo43", // Username
                "Há 2 dias", // Days
                "Nutela", // Pets in Photo
                "Texto 31", // TextView31
                "Lorem ipsum dolor sit amet consectetur adipisicing elit." // TextView32
        ));

        petList.add(new PetPerdido(
                R.drawable.user1, // Exemplo de imagem do usuário
                R.drawable.pet_perdido1, // Exemplo de imagem principal
                "john_doe", // Username
                "Há 5 dias", // Days
                "Bella", // Pets in Photo
                "Texto 31", // TextView31
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." // TextView32
        ));

        // Adicione mais pets conforme necessário

        adapter = new AdapterPerdidos(getContext(), petList);
        recyclerView.setAdapter(adapter);

        bt_adicionar = view.findViewById(R.id.btnAdicionar);
        bt_adicionar.setOnClickListener(v -> {
            abrirAdicionar();
        });

        return view;
    }

    private void abrirAdicionar() {


            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, AdicionarAoFeedTriste.newInstance());
            transaction.addToBackStack(null);
            transaction.commit();


    }

}
