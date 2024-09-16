package com.mobile.peticos.Perfil.Profissional;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.peticos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdicionarProduto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdicionarProduto extends Fragment {



    public AdicionarProduto() {
        // Required empty public constructor
    }


    public static AdicionarProduto newInstance() {
        AdicionarProduto fragment = new AdicionarProduto();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adicionar_produto, container, false);
    }
}