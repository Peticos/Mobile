package com.mobile.peticos.Vakinhas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.peticos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VakinhasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VakinhasFragment extends Fragment {

    public VakinhasFragment() {
        // Required empty public constructor
    }

    public static VakinhasFragment newInstance() {
        VakinhasFragment fragment = new VakinhasFragment();

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
        return inflater.inflate(R.layout.fragment_vakinhas, container, false);
    }
}