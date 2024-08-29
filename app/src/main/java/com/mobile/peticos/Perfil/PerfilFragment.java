package com.mobile.peticos.Perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mobile.peticos.Perfil.AdapterLembretes.CarouselAdapter;
import com.mobile.peticos.Perfil.AdapterLembretes.Lembrete;
import com.mobile.peticos.Perfil.Calendario.CalendarioFragment;
import com.mobile.peticos.Perfil.Graficos.GraficoFragment;
import com.mobile.peticos.Perfil.Posts.FeedDoPet;
import com.mobile.peticos.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


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




        // Dentro da sua Activity ou Fragment
        ViewPager2 viewPager = view.findViewById(R.id.lembretesLista);

        // Exemplo de lista de itens para o carrossel
        List<Lembrete> items = new ArrayList<>();
        items.add(new Lembrete("Veterinário", "15:00 - 16:00"));
        items.add(new Lembrete("Vacinação", "10:00 - 11:00"));
        items.add(new Lembrete("Banho", "12:00 - 13:00"));

        CarouselAdapter adapter = new CarouselAdapter(items);
        viewPager.setAdapter(adapter);


        // Inflar e adicionar os cartões
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());


        LinearLayout linearLayoutPets = view.findViewById(R.id.linearLayoutPets);


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


        CardView cardPost = view.findViewById(R.id.cardPost);
        CardView cardCalendar = view.findViewById(R.id.cardCalendar);

        cardPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToPost(view);
            }
        }    );
        cardCalendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToCalendar(view);
            }
        }    );

        return view;
    }

    public void goToPost(View view) {
        Log.d("FragmentNavigation", "Navigating to FeedDoPet fragment");

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, FeedDoPet.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void goToCalendar(View view) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, CalendarioFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();

    }





}