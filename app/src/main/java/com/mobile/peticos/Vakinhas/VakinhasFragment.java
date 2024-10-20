package com.mobile.peticos.Vakinhas;

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
import androidx.cardview.widget.CardView;

import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

public class VakinhasFragment extends Fragment {
    private RecyclerView recyclerView;
    private VakinhasAdapter adapter;

    ImageButton btAdicionar;
    private List<Vakinha> vakinhaList;

    // Novo: Definindo as referências para infoVakinha, fechar e cardInfoVakinha
    private ImageView infoVakinha, fechar;
    private CardView cardInfoVakinha;

    public VakinhasFragment() {
        // Required empty public constructor
    }

    public static VakinhasFragment newInstance() {
        return new VakinhasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vakinhas, container, false);

        recyclerView = view.findViewById(R.id.RecyclerViewVakinhas);
        btAdicionar = view.findViewById(R.id.btnAdicionar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializando o card como GONE inicialmente
        cardInfoVakinha = view.findViewById(R.id.cardInfoVakinha);
        cardInfoVakinha.setVisibility(View.GONE); // Esconde o card inicialmente

        // Referenciando os botões
        infoVakinha = view.findViewById(R.id.infoVakinha);
        fechar = view.findViewById(R.id.fechar);

        // Adicione o listener para mostrar o card ao clicar no infoVakinha
        infoVakinha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInfoVakinha.setVisibility(View.VISIBLE); // Mostrar o card
            }
        });

        // Adicione o listener para esconder o card ao clicar no fechar
        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInfoVakinha.setVisibility(View.GONE); // Esconder o card
            }
        });

        // Inicialize a lista de vakinhas
        vakinhaList = new ArrayList<>();

        btAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, AdicionarVaquinha.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Adicione exemplos de vakinhas à lista (imagens locais)
        vakinhaList.add(new Vakinha(
                R.drawable.user1, // Exemplo de imagem do usuário
                R.drawable.pet_vakinha, // Exemplo de imagem principal
                "geogeo43", // Username
                "Há 2 dias", // Days
                "Nutela", // Pets in Photo
                "Lorem ipsum dolor sit amet consectetur adipisicing elit." // Descrição
        ));

        vakinhaList.add(new Vakinha(
                R.drawable.user1, // Exemplo de imagem do usuário
                R.drawable.pet_vakinha, // Exemplo de imagem principal
                "john_doe", // Username
                "Há 5 dias", // Days
                "Bella", // Pets in Photo
                "Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." // Descrição
        ));

        // Adicione mais vakinhas conforme necessário

        adapter = new VakinhasAdapter(getContext(), vakinhaList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
