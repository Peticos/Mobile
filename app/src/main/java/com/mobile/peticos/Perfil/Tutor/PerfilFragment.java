package com.mobile.peticos.Perfil.Tutor;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobile.peticos.Cadastros.CadastrarPet;
import com.mobile.peticos.Login;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;

import com.mobile.peticos.Perfil.Tutor.AdapterPet;
import com.mobile.peticos.Perfil.Tutor.Calendario.CalendarioFragment;
import com.mobile.peticos.Perfil.Posts.FeedDoPet;
import com.mobile.peticos.R;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
    ImageView fotoPerfil, btn_cadastrarpet;
    RecyclerView recyclerPets;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        fotoPerfil = view.findViewById(R.id.fotoPerfil);
        nome = view.findViewById(R.id.NickName);
        email = view.findViewById(R.id.emailCampo);
        recyclerPets = view.findViewById(R.id.amiguinhos);
        recyclerPets.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        btn_cadastrarpet = view.findViewById(R.id.btn_cadastrarpet);


        //botao logout
        Button btnLogout = view.findViewById(R.id.btnSair);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

        btn_cadastrarpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CadastrarPet.class));
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apipeticos.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIPets apiPets = retrofit.create(APIPets.class);


//        Call<List<ModelPetBanco>> call = apiPets.getPets();
//
//        call.enqueue(new Callback<List<ModelPetBanco>>() {
//            @Override
//            public void onResponse(Call<List<ModelPetBanco>> call, Response<List<ModelPetBanco>> response) {
//                if (response.isSuccessful()) {
//                    List<ModelPetBanco> ListaPets = response.body(); // Lista recebida da API
//                    if (ListaPets != null) {
//                        AdapterPet adapterPet = new AdapterPet(ListaPets); // Passa a lista para o Adapter
//                        recyclerPets.setAdapter(adapterPet); // Configura o RecyclerView
//                        adapterPet.notifyDataSetChanged();
//
//                    }
//                } else {
//                    // Tratar caso não seja bem-sucedido
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ModelPetBanco>> call, Throwable t) {
//                // Tratar falha
//            }
//        });
//
//

        CardView cardPost = view.findViewById(R.id.cardPost);
        CardView cardVakinhas = view.findViewById(R.id.cardVakinhas);
        CardView cardPerdidos = view.findViewById(R.id.cardPerdidos);

        cardPost.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToPost(view);
            }
        }    );
        cardVakinhas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goToPost(view);
            }
        }    );
        cardPerdidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPost(view);
            }
        });

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


    public void openEditor(View view) {
        Intent intent = new Intent(getActivity(), EditarPerfil.class);
        startActivity(intent);
    }

    public void logout(View view) {

        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);

    }



}