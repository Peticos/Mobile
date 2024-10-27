package com.mobile.peticos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mobile.peticos.Local.LocalFragment;
import com.mobile.peticos.Perdidos.PerdidoFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrimeiroCuidadosAchados#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrimeiroCuidadosAchados extends Fragment {
    Button btnPerdido, btnOng;

    public PrimeiroCuidadosAchados() {
        // Required empty public constructor
    }

    public static PrimeiroCuidadosAchados newInstance() {
        PrimeiroCuidadosAchados fragment = new PrimeiroCuidadosAchados();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_primeiro_cuidados_achados, container, false);
        btnPerdido = view.findViewById(R.id.btnPerdidos);
        btnOng = view.findViewById(R.id.btnOng);

        if (btnPerdido != null && btnOng != null) {
            btnPerdido.setOnClickListener(v -> {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, PerdidoFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            });

            btnOng.setOnClickListener(v -> {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, LocalFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }

        return view;
    }
}