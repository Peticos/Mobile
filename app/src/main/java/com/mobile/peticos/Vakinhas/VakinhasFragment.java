package com.mobile.peticos.Vakinhas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

public class VakinhasFragment extends Fragment {
    private RecyclerView recyclerView;
    private VakinhasAdapter adapter;
    private List<Vakinha> vakinhaList;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicialize a lista de vakinhas
        vakinhaList = new ArrayList<>();

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
