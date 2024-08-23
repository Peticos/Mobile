package com.mobile.peticos.Perfil;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.peticos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedDoPet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedDoPet extends Fragment {


    public FeedDoPet() {
        // Required empty public constructor
    }


    public static FeedDoPet newInstance() {
        FeedDoPet fragment = new FeedDoPet();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, PerfilFragment.newInstance());
        transaction.commit();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed_do_pet, container, false);
    }


}