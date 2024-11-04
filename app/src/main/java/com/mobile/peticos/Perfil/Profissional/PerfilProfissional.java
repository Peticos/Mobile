
package com.mobile.peticos.Perfil.Profissional;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


import com.mobile.peticos.Home.AdicionarProduto;
import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.Feed.FeedPetsAdapter;
import com.mobile.peticos.Login;
import com.mobile.peticos.Perfil.APIPerfil;
import com.mobile.peticos.Perfil.Profissional.Graficos.GraficoFragment;
import com.mobile.peticos.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PerfilProfissional extends Fragment {


    private static final String BASE_URL = "https://api-mongo-i1jq.onrender.com";
    private static final String PREFS_NAME = "Perfil";
    private static final String KEY_ID = "id";
    private static final int DEFAULT_ID = 2;



    public PerfilProfissional() {
        // Required empty public constructor
    }

    public static PerfilProfissional newInstance() {
        PerfilProfissional fragment = new PerfilProfissional();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



    }

    ImageView fotoPerfil;
    TextView nome;
    TextView email;
    Button novo_produto_button;
    private APIPerfil apiPerfil;
    private RecyclerView recyclerView;
    private CardView cardFeedErro, cardFeedSemPost;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_perfil_profissional, container, false);

        Button graphic = view.findViewById(R.id.area_restrita_button);
        Button novoProduto = view.findViewById(R.id.novo_produto_button);
        LinearLayout editar;
        editar = view.findViewById(R.id.layoutEditar);
        Button btnLogout = view.findViewById(R.id.btnSair);

        fotoPerfil = view.findViewById(R.id.fotoPerfil);
        nome = view.findViewById(R.id.NickName);
        email = view.findViewById(R.id.email);
        progressBar = view.findViewById(R.id.progressBar2);
        recyclerView = view.findViewById(R.id.recycler);


        // Acesso ao SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Perfil", MODE_PRIVATE);

        // Recuperar os dados

        int id = sharedPreferences.getInt("id", 278);
        String url = sharedPreferences.getString("url", "https://firebasestorage.googleapis.com/v0/b/apipeticos.appspot.com/o/Imagens%2Fdefault.png?alt=media&token=5d7a6aaf-0d4f-4b3e-9f4b-0e2e9f1c9a0c");

        RequestOptions options = new RequestOptions()
                    .centerCrop() // Garante que a imagem preencha o espaço
                    .transform(new RoundedCorners(30)); // Aplica a transformação de cantos arredondados

        Glide.with(this)
                    .load(url)
                    .apply(options)
                    .error(R.drawable.fotogenerica) // Imagem que aparece em caso de erro
                    .into(fotoPerfil);

        String username = sharedPreferences.getString("nome_usuario", "nome_usuario");
        nome.setText(username);

        String emailUser = sharedPreferences.getString("email", "email");
        email.setText(emailUser);

        setupRetrofitFeed();
        initRecyclerViewFeed();


        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditProfile(view);
            }
        });
        graphic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToGraphic(view);
            }
        });


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

        novoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNewProduct(view);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void goToEditProfile(View view) {
        Intent intent = new Intent(getActivity(), EditarPerfilProfissional.class);
        startActivity(intent);
    }

    private void goToNewProduct(View view) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, AdicionarProduto.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void goToGraphic(View view) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, GraficoFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void logout(View view) {

        // Recupera o SharedPreferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Perfil", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("nome", "");
        editor.putString("nome_usuario", "");
        editor.putString("email","");
        editor.putString("bairro", "");
        editor.putBoolean("mei", false);
        editor.putString("telefone", "");
        editor.putString("url", "");
        editor.putString("genero", "");
        editor.putInt("id", 0);
        editor.apply(); // Aplica as alterações



        Intent intent = new Intent(getActivity(), Login.class);
        startActivity(intent);

    }
    // Configuração do Retrofit
    private void setupRetrofitFeed() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPerfil = retrofit.create(APIPerfil.class);
    }

    // Inicializa o RecyclerView com todos os locais
    private void initRecyclerViewFeed() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String id = String.valueOf(sharedPreferences.getInt(KEY_ID, DEFAULT_ID));

        progressBar.setVisibility(View.VISIBLE);
        Call<List<FeedPet>> call = apiPerfil.getPostByid(id); // Use o ID do sharedPreferences
        call.enqueue(new Callback<List<FeedPet>>() {
            @Override
            public void onResponse(Call<List<FeedPet>> call, Response<List<FeedPet>> response) {
                Log.d("FeedDoPet", "Resposta da API recebida. Código: " + response.code()); // Logando o código de resposta

                if (response.isSuccessful() && response.body() != null) {
                    List<FeedPet> feedList = response.body();

                    progressBar.setVisibility(View.GONE);
                    Log.d("FeedDoPet", "Dados recebidos: " + feedList.toString()); // Logando os dados recebidos

                    updateRecyclerViewFeed(feedList);
                } else {
                    progressBar.setVisibility(View.GONE);
                    cardFeedSemPost.setVisibility(View.VISIBLE);
                    Log.e("FeedDoPet", "Erro na resposta: " + (response.errorBody() != null ? response.errorBody().toString() : "Resposta vazia"));
                    Toast.makeText(getContext(), "Nenhum Post encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FeedPet>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                cardFeedErro.setVisibility(View.VISIBLE);
                Log.e("FeedPet", "Erro: " + throwable.getMessage());
                Toast.makeText(getContext(), "Erro ao carregar posts: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRecyclerViewFeed(List<FeedPet> feedList) {
        // Configuração do RecyclerView para o feed de pets
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Configuração do Adapter para o RecyclerView
        FeedPetsAdapter feedPetsAdapter = new FeedPetsAdapter(feedList);
        recyclerView.setAdapter(feedPetsAdapter);  // Aqui, finalmente vinculando o Adapter
    }
}
