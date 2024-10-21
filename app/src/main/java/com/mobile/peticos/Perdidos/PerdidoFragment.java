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
import android.widget.ImageView;

import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

public class PerdidoFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterPerdidos adapter;
    private List<PetPerdido> petList;
    private ImageButton btAdicionar, btnSos;
    private ImageView infoPerdidos, fechar;
    private View cardInfoPerdido;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perdido, container, false);

        recyclerView = view.findViewById(R.id.RecyclerViewPetsPerdidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicialize a lista de pets perdidos
        petList = new ArrayList<>();

        // Adicione exemplos de pets na lista
        petList.add(new PetPerdido(
                R.drawable.user1,
                R.drawable.pet_perdido1,
                "geogeo43",
                "Há 2 dias",
                "Nutela",
                "Texto 31",
                "Lorem ipsum dolor sit amet consectetur adipisicing elit."
        ));

        petList.add(new PetPerdido(
                R.drawable.user1,
                R.drawable.pet_perdido1,
                "john_doe",
                "Há 5 dias",
                "Bella",
                "Texto 31",
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
        ));

        // Adicione mais pets conforme necessário

        adapter = new AdapterPerdidos(getContext(), petList);
        recyclerView.setAdapter(adapter);

        // Configurações do botão de adicionar e SOS
        btAdicionar = view.findViewById(R.id.btnAdicionar);
        btnSos = view.findViewById(R.id.btnSos);

        btAdicionar.setOnClickListener(v -> abrirAdicionar());
        btnSos.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PrimeirosCuidados.class);
            startActivity(intent);
        });

        // Configurações para mostrar e esconder o card de informação
        infoPerdidos = view.findViewById(R.id.infoPerdidos);
        fechar = view.findViewById(R.id.fechar);
        cardInfoPerdido = view.findViewById(R.id.cardInfoPerdido);

        infoPerdidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInfoPerdido.setVisibility(View.VISIBLE);
            }
        });

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInfoPerdido.setVisibility(View.GONE);
            }
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
