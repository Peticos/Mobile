package com.mobile.peticos.Local;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
import com.mobile.peticos.Cadastros.CadastroProfissional;
import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalFragment extends Fragment {

    public LocalFragment() {
        // Required empty public constructor
    }

    public static LocalFragment newInstance() {
        return new LocalFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewLocais);  // Certifique-se de que o ID está correto
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //Chamada API
        String API = "https://apipeticos.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiLocais apiLocais = retrofit.create(ApiLocais.class);
        Call<List<Local>> call = apiLocais.getAll();

        call.enqueue(new Callback<List<Local>>() {
            @Override
            public void onResponse(Call<List<Local>> call, Response<List<Local>> response) {
                List<Local> localList = response.body();
                List<Local> localModels = new ArrayList<>();

                if (localList != null) {
                    for (Local local : localList) {
                        localModels.add(new Local(local.getDescription(), local.getLocalPicture(), local.getLinkKnowMore(),local.getLocalName(), local.getStreet()));
                    }

                    recyclerView.setAdapter(new LocaisAdapter(localModels));
                }
            }


            @Override
            public void onFailure(Call<List<Local>> call, Throwable throwable) {
                Toast.makeText(view.getContext(), "Erro ao carregar Locais", Toast.LENGTH_SHORT).show();
            }
        });
        return view;

    }

}
