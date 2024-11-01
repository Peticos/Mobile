package com.mobile.peticos.Perfil.Tutor.Posts;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.Perdidos.AdapterPerdidos;
import com.mobile.peticos.Perdidos.PetPerdido;
import com.mobile.peticos.Perfil.APIPerfil;
import com.mobile.peticos.Perfil.Tutor.PerfilFragment;
import com.mobile.peticos.R;
import com.mobile.peticos.Vakinhas.VakinhAPI;
import com.mobile.peticos.Vakinhas.Vakinha;
import com.mobile.peticos.Vakinhas.VakinhasAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VakinhasTutor extends Fragment {

    private static final String BASE_URL = "https://apipeticos-ltwk.onrender.com/";

    private VakinhAPI apiVakinha;
    private RecyclerView recyclerView;
    private CardView cardVakinhasSemPost, cardErroVakinhas;
    private ProgressBar progressBar;

    public static VakinhasTutor newInstance() {
        return new VakinhasTutor();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRetrofitFeed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vakinhas_tutor, container, false);
        recyclerView = view.findViewById(R.id.recycler);
        cardVakinhasSemPost = view.findViewById(R.id.cardVakinhasSemPost);
        cardErroVakinhas = view.findViewById(R.id.cardErroVakinhas);
        progressBar = view.findViewById(R.id.progressBar2);
        ImageButton voltar = view.findViewById(R.id.goBack);

        initRecyclerViewFeed();

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

    // Configuração do Retrofit
    private void setupRetrofitFeed() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiVakinha = retrofit.create(VakinhAPI.class);
    }

    // Inicializa o RecyclerView com todos os locais
    private void initRecyclerViewFeed() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Perfil", MODE_PRIVATE);
        int id = sharedPreferences.getInt("id",10);

        progressBar.setVisibility(View.VISIBLE);
        Call<List<Vakinha>> call = apiVakinha.getByID(id); // Use o ID do sharedPreferences
        call.enqueue(new Callback<List<Vakinha>>() {
            @Override
            public void onResponse(Call<List<Vakinha>> call, Response<List<Vakinha>> response) {
                Log.d("FeedDoPet", "Resposta da API recebida. Código: " + response.code()); // Logando o código de resposta

                if (response.isSuccessful() && response.body() != null && response.body().size() > 0) {
                    List<Vakinha> feedList = response.body();


                    progressBar.setVisibility(View.GONE);
                    Log.d("FeedDoPet", "Dados recebidos: " + feedList.toString()); // Logando os dados recebidos

                    updateRecyclerViewFeed(feedList);
                } else {
                    progressBar.setVisibility(View.GONE);
                    cardVakinhasSemPost.setVisibility(View.VISIBLE);
                    Log.e("FeedDoPet", "Erro na resposta: " + (response.errorBody() != null ? response.errorBody().toString() : "Resposta vazia"));
                    Toast.makeText(getContext(), "Nenhum Post encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Vakinha>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                cardErroVakinhas.setVisibility(View.VISIBLE);
                Log.e("FeedPet", "Erro: " + throwable.getMessage());
                Toast.makeText(getContext(), "Erro ao carregar posts: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerViewFeed(List<Vakinha> feedList) {
        // Configuração do RecyclerView para o feed de pets
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuração do Adapter para o RecyclerView
        VakinhasAdapter feedPetsAdapter = new VakinhasAdapter(feedList);
        recyclerView.setAdapter(feedPetsAdapter);  // Aqui, finalmente vinculando o Adapter
    }
}
