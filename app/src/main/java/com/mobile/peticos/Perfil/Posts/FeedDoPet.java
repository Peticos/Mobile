package com.mobile.peticos.Perfil.Posts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.mobile.peticos.Perfil.PerfilFragment;
import com.mobile.peticos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedDoPet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedDoPet extends Fragment {


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

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feed_do_pet, container, false);

        ImageButton voltar = view.findViewById(R.id.goBack);

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, PerfilFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }



}