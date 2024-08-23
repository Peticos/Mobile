package com.mobile.peticos.Perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mobile.peticos.R;

import org.jetbrains.annotations.Nullable;


public class PerfilFragment extends Fragment {


    public PerfilFragment() {
        // Required empty public constructor
    }

    public static PerfilFragment newInstance() {
        PerfilFragment fragment = new PerfilFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Obter referência ao LinearLayout
        LinearLayout linearLayoutLembretes = view.findViewById(R.id.linearLayoutLembretes);
        LinearLayout linearLayoutPets = view.findViewById(R.id.linearLayoutPets);

        // Inflar e adicionar os cartões
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        for (int i = 0; i < 5; i++) {  // Adicione quantos cards você precisar
            View cardView = layoutInflater.inflate(R.layout.lembrete, linearLayoutLembretes, false);
            linearLayoutLembretes.addView(cardView);
        }


        for (int i = 0; i < 10; i++) {  // Adicione quantos cards você precisar
            View cardView = layoutInflater.inflate(R.layout.pet, linearLayoutPets, false);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lógica para lidar com o clique no card
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, FeedDoPet.newInstance());
                    transaction.commit();


                }
            });
            linearLayoutPets.addView(cardView);
        }
        return view;
    }

}