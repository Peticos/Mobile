package com.mobile.peticos.Perfil.Tutor;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.peticos.Cadastros.CadastrarPet;
import com.mobile.peticos.Login;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;

import com.mobile.peticos.Perfil.Tutor.Posts.FeedDoPet;
import com.mobile.peticos.R;

import org.jetbrains.annotations.Nullable;

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

        // Acesso ao SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Perfil", Context.MODE_PRIVATE);


        // Recuperar os dados
        String url = sharedPreferences.getString("url", "https://firebasestorage.googleapis.com/v0/b/apipeticos.appspot.com/o/Imagens%2Fdefault.png?alt=media&token=5d7a6aaf-0d4f-4b3e-9f4b-0e2e9f1c9a0c");
        RequestOptions options = new RequestOptions()
                .centerCrop() // Garante que a imagem preencha o espaço
                .transform(new RoundedCorners(30)); // Aplica a transformação de cantos arredondados

        Glide.with(this)
                .load(url)
                .apply(options)
                .error(R.drawable.fotogenerica) // Imagem que aparece em caso de erro
                .into(fotoPerfil);


        nome.setText(sharedPreferences.getString("nome", "nome do tutor"));
        email.setText(sharedPreferences.getString("email", "email do tutor"));



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

        Call<List<ModelPetBanco>> call = apiPets.getPets(sharedPreferences.getString("nome_usuario", "modolo"));
        call.enqueue(new Callback<List<ModelPetBanco>>() {
            @Override
            public void onResponse(Call<List<ModelPetBanco>> call, Response<List<ModelPetBanco>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ModelPetBanco> listaPets = response.body();
                    AdapterPet adapterPet = new AdapterPet(listaPets);
                    recyclerPets.setAdapter(adapterPet);
                } else {
                    Log.e("API_ERROR", "Erro: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ModelPetBanco>> call, Throwable t) {
                Log.e("API_ERROR", "Falha na chamada", t);
                Toast.makeText(getContext(), "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });




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



}