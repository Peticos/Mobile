package com.mobile.peticos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobile.peticos.Home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdicionarAoFeedPrincipal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdicionarAoFeedPrincipal extends Fragment {

    Button btnPublicar, btnSair;
    ImageButton btn_voltar_publicacoes;
    TextView publicacoes;

    public AdicionarAoFeedPrincipal() {
        // Required empty public constructor
    }


    public static AdicionarAoFeedPrincipal newInstance() {
        AdicionarAoFeedPrincipal fragment = new AdicionarAoFeedPrincipal();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adicionar_ao_feed_principal, container, false);

        btnPublicar = view.findViewById(R.id.btnPublicar);
        btnSair = view.findViewById(R.id.btnSair);
        btn_voltar_publicacoes = view.findViewById(R.id.btn_voltar_publicacoes);
        publicacoes = view.findViewById(R.id.publicacoes);

        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publicar();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sair();
            }
        });

        btn_voltar_publicacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sair();
            }
        });

        publicacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sair();
            }
        });

        return view;
    }

    private void Publicar() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void Sair() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}