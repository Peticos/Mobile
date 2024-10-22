package com.mobile.peticos.Perfil.Tutor.Posts;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.Feed.FeedPetsAdapter;
import com.mobile.peticos.Perfil.APIPerfil;
import com.mobile.peticos.Perfil.Tutor.PerfilFragment;
import com.mobile.peticos.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feed_do_pet, container, false);
        recyclerView = view.findViewById(R.id.recycler);

        ImageButton voltar = view.findViewById(R.id.goBack);
        setupRetrofit();
        initRecyclerView();

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, PerfilFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        APIPerfil api;





        return view;
    }
    Retrofit retrofit;
    APIPerfil apiPerfil;
    private void setupRetrofit() {
        String API = "https://apimongo-ghjh.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPerfil = retrofit.create(APIPerfil.class);
    }
    // Inicializa o RecyclerView com todos os locais

    private void initRecyclerView() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Perfil", Context.MODE_PRIVATE);
        String id = String.valueOf(sharedPreferences.getInt("id", 2));
        Toast.makeText(getContext(), id, Toast.LENGTH_SHORT).show();
        Call<List<FeedPet>> call = apiPerfil.getPostByid(id);;
        call.enqueue(new Callback<List<FeedPet>>() {
            @Override
            public void onResponse(Call<List<FeedPet>> call, Response<List<FeedPet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FeedPet> feedList = response.body();
                    recyclerView.setAdapter(new FeedPetsAdapter(feedList));

                } else {
                    Toast.makeText(getActivity(), "Nenhum post encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FeedPet>> call, Throwable throwable) {
                Toast.makeText(getActivity(), "Erro ao buscar", Toast.LENGTH_SHORT).show();

            }
        });
    }





}