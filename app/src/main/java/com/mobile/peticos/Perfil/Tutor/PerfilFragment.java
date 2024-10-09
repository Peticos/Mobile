package com.mobile.peticos.Perfil.Tutor;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.mobile.peticos.Login;
import com.mobile.peticos.Perfil.Tutor.AdapterLembretes.CarouselAdapter;
import com.mobile.peticos.Perfil.Tutor.AdapterLembretes.Lembrete;
import com.mobile.peticos.Perfil.Tutor.Calendario.CalendarioFragment;
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
    TextView nome;
    TextView email;
    ImageView fotoPerfil;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        fotoPerfil = view.findViewById(R.id.fotoPerfil);
        nome = view.findViewById(R.id.NickName);
        email = view.findViewById(R.id.emailCampo);

        FirebaseAuth autenticator = FirebaseAuth.getInstance();

        nome.setText(autenticator.getCurrentUser().getDisplayName());
        email.setText(autenticator.getCurrentUser().getEmail());
        fotoPerfil.setImageURI(autenticator.getCurrentUser().getPhotoUrl());

        if(autenticator.getCurrentUser().getPhotoUrl() != null){
            RequestOptions options = new RequestOptions()
                    .centerCrop() // Garante que a imagem preencha o espaço
                    .transform(new RoundedCorners(30)); // Aplica a transformação de cantos arredondados

            Glide.with(this)
                    .load(autenticator.getCurrentUser().getPhotoUrl())
                    .apply(options)
                    .into(fotoPerfil);
        }
        if(autenticator.getCurrentUser().getDisplayName() != null){

            nome.setText(autenticator.getCurrentUser().getDisplayName());
        }
        if(autenticator.getCurrentUser().getEmail() != null){

            email.setText(autenticator.getCurrentUser().getEmail());
        }




        // Dentro da sua Activity ou Fragment
        ViewPager2 viewPager = view.findViewById(R.id.lembretesLista);

        // Exemplo de lista de itens para o carrossel
        List<Lembrete> items = new ArrayList<>();
        items.add(new Lembrete("Veterinário", "15:00 - 16:00"));
        items.add(new Lembrete("Vacinação", "10:00 - 11:00"));
        items.add(new Lembrete("Banho", "12:00 - 13:00"));

        CarouselAdapter adapter = new CarouselAdapter(items);
        viewPager.setAdapter(adapter);

        //botao logout
        Button btnLogout = view.findViewById(R.id.btnSair);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });


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

        LinearLayout layoutEditar = view.findViewById(R.id.layoutEditar);

        layoutEditar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openEditor(view);
            }
        });

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


    public void openEditor(View view) {
        Intent intent = new Intent(getActivity(), EditarPerfil.class);
        startActivity(intent);
    }

    public void logout(View view) {
        FirebaseAuth autenticator = FirebaseAuth.getInstance();

        autenticator.signOut();
        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);

    }



}