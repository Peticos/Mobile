package com.mobile.peticos.Perdidos;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ImageView;

import com.mobile.peticos.Local.ApiLocais;
import com.mobile.peticos.Local.LocaisAdapter;
import com.mobile.peticos.Local.Local;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerdidoFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterPerdidos adapter;
    private List<PetPerdido> petList;
    private ImageButton btAdicionar, btnSos;
    private ImageView infoPerdidos, fechar;
    private View cardInfoPerdido;
    CardView cardErroPerdidos;

    public PerdidoFragment() {
        // Required empty public constructor
    }

    public static PerdidoFragment newInstance() {
        return new PerdidoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perdido, container, false);

        recyclerView = view.findViewById(R.id.RecyclerViewPetsPerdidos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cardErroPerdidos = view.findViewById(R.id.cardErroPerdidos);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Perfil", Context.MODE_PRIVATE);
        Boolean mei = sharedPreferences.getBoolean("mei", true);
        btAdicionar = view.findViewById(R.id.btnAdicionar);
        if( mei ) {
            btAdicionar.setVisibility(View.INVISIBLE);
        }
        btAdicionar.setOnClickListener(v -> {
            abrirAdicionar();
        });
        btnSos = view.findViewById(R.id.btnSos);
        btnSos.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PrimeirosCuidados.class);
            startActivity(intent);
        });
        setupRetrofit();
        initRecyclerView();


        // Configurações para mostrar e esconder o card de informação
        infoPerdidos = view.findViewById(R.id.infoPerdidos);
        fechar = view.findViewById(R.id.fechar);
        cardInfoPerdido = view.findViewById(R.id.cardInfoPerdido);

        infoPerdidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInfoPerdido.setVisibility(View.VISIBLE);
            }
        });

        fechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardInfoPerdido.setVisibility(View.GONE);
            }
        });

        return view;
    }
    ApiPerdidos apiPerdidos;
    private void setupRetrofit() {
        String API = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPerdidos = retrofit.create(ApiPerdidos.class);
    }

    private void initRecyclerView() {
        Call<List<PetPerdido>> call = apiPerdidos.getPerdidos();
        call.enqueue(new Callback<List<PetPerdido>>() {
            @Override
            public void onResponse(Call<List<PetPerdido>> call, Response<List<PetPerdido>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PetPerdido> PerdidosList = response.body();
                    updateRecyclerView(PerdidosList);
                } else {
                    Toast.makeText( getActivity(), "Nenhum perdido perdido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PetPerdido>> call, Throwable throwable) {
                cardErroPerdidos.setVisibility(View.VISIBLE);
            }
        });
    }
    private void updateRecyclerView(List<PetPerdido> PetlList) {
        List<PetPerdido> PerdidosModels = new ArrayList<>();
        for (PetPerdido pet : PetlList) {

            PerdidosModels.add(new PetPerdido(
                    pet.getIdPet(),
                    pet.getIdUser(),
                    pet.getBairro(),
                    pet.getTitle(),
                    pet.getDescription(),
                    pet.getPostTime(),
                    pet.getPicture(),
                    pet.getStreet(),
                    pet.getStreetNum(),
                    pet.getLostDate()

            ));
        }
        recyclerView.setAdapter(new AdapterPerdidos(PerdidosModels));
    }

    private void abrirAdicionar() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, AdicionarAoFeedTriste.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
