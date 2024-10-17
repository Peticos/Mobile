package com.mobile.peticos.Home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 1001;
    public static final String[] REQUIRED_PERMISSIONS;

    static {
        List<String> requiredPermissions = new ArrayList<>();
        // Adicionando a permissão de notificação para Android 13 ou superior
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requiredPermissions.add(Manifest.permission.POST_NOTIFICATIONS);
        }
        REQUIRED_PERMISSIONS = requiredPermissions.toArray(new String[0]);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Verificar e solicitar permissão de notificação ao abrir a tela
        checkNotificationPermission();

        // Configuração do RecyclerView para dicas
        RecyclerView recyclerViewDicas = view.findViewById(R.id.RecyclerViewDicas);
        recyclerViewDicas.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Dados de exemplo para o RecyclerViewDicas
        List<String> dicasItems = Arrays.asList("Item 1", "Item 2", "Item 3", "Item 4");

        // Configuração do Adapter para o RecyclerViewDicas
        AdapterCuriosidadesDiarias dicasAdapter = new AdapterCuriosidadesDiarias(dicasItems);
        recyclerViewDicas.setAdapter(dicasAdapter);
        setupRetrofit();
        initRecyclerView(view);



        return view;
    }

    // Verificar e solicitar permissão de notificação
    private void checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS, REQUEST_NOTIFICATION_PERMISSION);
            }
        }
    }
    ApiHome apiHome;
    Retrofit retrofit;
    // Configuração do Retrofit
    private void setupRetrofit() {
        String API = "https://apimongo-ghjh.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiHome = retrofit.create(ApiHome.class);
    }
    // Inicializa o RecyclerView com todos os locais
    private void initRecyclerView(View v) {
        Call<List<FeedPet>> call = apiHome.getAll();
        call.enqueue(new Callback<List<FeedPet>>() {
            @Override
            public void onResponse(Call<List<FeedPet>> call, Response<List<FeedPet>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FeedPet> feedList = response.body();
                    updateRecyclerView(feedList, v);
                } else {
                    Toast.makeText(getContext(), "Nenhum Post encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FeedPet>> call, Throwable throwable) {
                Toast.makeText(getContext(), "Erro ao carregar posts", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void updateRecyclerView(List<FeedPet> feedList, View v) {
        List<FeedPet> postagens = new ArrayList<>();
        for (FeedPet postagem : feedList) {
            if(postagem.isIs_mei()){
                postagens.add(new FeedPet(
                        postagem.getUserId(),
                        postagem.getLikes(),
                        postagem.getShares(),
                        postagem.getPicture(),
                        postagem.getCaption(),
                        postagem.getPostDate(),
                        postagem.isIs_mei(),
                        postagem.getPrice(),
                        postagem.getTelephone(),
                        postagem.getProductName()
                        )
                );
            }else{
                postagens.add(new FeedPet(
                                postagem.getUserId(),
                                postagem.getLikes(),
                                postagem.getShares(),
                                postagem.getPicture(),
                                postagem.getCaption(),
                                postagem.getPets(),
                                postagem.getPostDate(),
                                postagem.isIs_mei()
                        )
                );
            }
        }
        // Configuração do RecyclerView para o feed de pets
        RecyclerView recyclerViewFeedPets = v.findViewById(R.id.RecyclerViewFeedPets);
        recyclerViewFeedPets.setLayoutManager(new LinearLayoutManager(getContext()));



        // Configuração do Adapter para o RecyclerViewFeedPets
        FeedPetsAdapter feedPetsAdapter = new FeedPetsAdapter(postagens);
        recyclerViewFeedPets.setAdapter(feedPetsAdapter);
    }
}
