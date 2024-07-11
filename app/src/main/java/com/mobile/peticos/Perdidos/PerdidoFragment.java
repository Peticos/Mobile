package com.mobile.peticos.Perdidos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.peticos.R;


public class PerdidoFragment extends Fragment {

    public PerdidoFragment() {
        // Required empty public constructor
    }


    public static PerdidoFragment newInstance() {
        PerdidoFragment fragment = new PerdidoFragment();

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
        return inflater.inflate(R.layout.fragment_perdido, container, false);
    }
}