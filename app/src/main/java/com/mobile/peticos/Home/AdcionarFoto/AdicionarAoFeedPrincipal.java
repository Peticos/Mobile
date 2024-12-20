package com.mobile.peticos.Home.AdcionarFoto;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobile.peticos.Home.ApiHome;
import com.mobile.peticos.Home.Feed.FeedPet;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.peticos.Home.HomeDica.DicasDoDia;
import com.mobile.peticos.Home.HomeFragment;
import com.mobile.peticos.Padrao.Upload.Camera;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdicionarAoFeedPrincipal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdicionarAoFeedPrincipal extends Fragment {

    Button btnPublicar, btnSair;
    ImageButton btn_voltar_publicacoes;
    TextView publicacoes, petsInvalidos;
    ProgressBar progressBar;

    public AdicionarAoFeedPrincipal() {
        // Required empty public constructor
    }


    public static AdicionarAoFeedPrincipal newInstance() {
        AdicionarAoFeedPrincipal fragment = new AdicionarAoFeedPrincipal();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    ImageView btnUpload;
    private ActivityResultLauncher<Intent> cameraLauncher;
    String url;
    EditText legenda;
    RecyclerView recyclerPets;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adicionar_ao_feed_principal, container, false);

        btnPublicar = view.findViewById(R.id.btnPublicar);
        btnSair = view.findViewById(R.id.btnSair);
        btn_voltar_publicacoes = view.findViewById(R.id.btn_voltar_publicacoes);
        publicacoes = view.findViewById(R.id.publicacoes);
        btnUpload = view.findViewById(R.id.upload);
        legenda = view.findViewById(R.id.legenda);
        recyclerPets = view.findViewById(R.id.amiguinhos);
        petsInvalidos = view.findViewById(R.id.petsInvalidos);
        progressBar = view.findViewById(R.id.progressBar2);
        // Remove o campo "selectedPets"
        SharedPreferences sharedPreferences2 = getContext().getSharedPreferences("PetCache", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences2.edit();
        editor.remove("selectedPets");
        editor.apply(); // Aplica as alterações

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), Camera.class);
                Bundle bundle = new Bundle();
                bundle.putString("tipo", "tutor");
                intent.putExtras(bundle);
                cameraLauncher.launch(intent); // Apenas lance o Intent sem o código de solicitação

            }
        });
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            url = data.getStringExtra("url"); // Obter a URL do Intent
                            if(url != null){
                                url.replace("\"", "");
                                url.replace(" ", "");
                                RequestOptions options = new RequestOptions()
                                        .centerCrop() // Garante que a imagem preencha o espaço
                                        .transform(new RoundedCorners(30)); // Aplica a transformação de cantos arredondados

                                Glide.with(this)
                                        .load(url)
                                        .apply(options)
                                        .into(btnUpload);

                            }

                        }
                    }
                }
        );


        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validar();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sair();
            }
        });

        btn_voltar_publicacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sair();
            }
        });

        publicacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sair();
            }
        });




        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Perfil", MODE_PRIVATE);




        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerPets.setLayoutManager(layoutManager);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apipeticos.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPets apiPets = retrofit.create(APIPets.class);

        progressBar.setVisibility(View.VISIBLE);
        Call<List<ModelPetBanco>> call = apiPets.getPets(sharedPreferences.getString("nome_usuario", "modolo"));
        call.enqueue(new Callback<List<ModelPetBanco>>() {
            @Override
            public void onResponse(Call<List<ModelPetBanco>> call, Response<List<ModelPetBanco>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ModelPetBanco> listaPets = response.body();
                    AdapterPetFeedPrincipal adapterPet = new AdapterPetFeedPrincipal(listaPets);

                    recyclerPets.setAdapter(adapterPet);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.e("API_ERROR", "Erro: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<ModelPetBanco>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("API_ERROR", "Falha na chamada", t);
                Toast.makeText(getContext(), "Erro de conexão", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    Boolean erro = false;
    private void validar(){
        SharedPreferences sharedPreferencesPet = getActivity().getSharedPreferences("PetCache", Context.MODE_PRIVATE);
        Set<String> selectedPets = sharedPreferencesPet.getStringSet("selectedPets", new HashSet<>());

        List<Integer> selectedPetsList = new ArrayList<>();

        // Convertendo os valores de String para int e adicionando à lista
        for (String petId : selectedPets) {
            try {
                // Tenta converter o ID do pet para int
                int id = Integer.parseInt(petId);
                selectedPetsList.add(id);
            } catch (NumberFormatException e) {
                // Trate a exceção se a conversão falhar
                e.printStackTrace(); // Ou log a falha
            }
        }



        if(url == null){
            RequestOptions options = new RequestOptions()
                    .centerCrop();
            Glide.with(this)
                    .load(R.drawable.adicionar_imagem_vermelho)
                    .apply(options)
                    .into(btnUpload);
            erro = true;
        }
        if(legenda.getText().toString().isEmpty()){
            legenda.setError("Legenda é obrigatória");
            erro = true;
        }
        if(selectedPetsList.size() == 0){
            petsInvalidos.setVisibility(View.VISIBLE);
            erro = true;
        }else{
            petsInvalidos.setVisibility(View.GONE);

        }
        if(!erro){
            PublicarPost();
        }
    }
    private void PublicarPost() {
        SharedPreferences sharedPreferencesPet = getActivity().getSharedPreferences("PetCache", Context.MODE_PRIVATE);
        Set<String> selectedPets = sharedPreferencesPet.getStringSet("selectedPets", new HashSet<>());

        List<Integer> selectedPetsList = new ArrayList<>();

        // Convertendo os valores de String para int e adicionando à lista
        for (String petId : selectedPets) {
            try {
                // Tenta converter o ID do pet para int
                int id = Integer.parseInt(petId);
                selectedPetsList.add(id);
            } catch (NumberFormatException e) {
                // Trate a exceção se a conversão falhar
                e.printStackTrace(); // Ou log a falha
            }
        }

        //mudar para o id do user quando funcionar
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Perfil", MODE_PRIVATE);



        List<String> likes = Arrays.asList();
        List<String> shares = Arrays.asList();


        // Obter a data atual formatada
        String postDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // mudar para os pets


        // Criar uma nova instância de FeedPet
        FeedPet post = new FeedPet(
                sharedPreferences.getInt("id", 284), // userId
                likes,
                shares,// likes
                url, // picture
                legenda.getText().toString(), // caption
                selectedPetsList, // pets
                postDate, // postDate
                false // isMei
        );

        String API = "https://apimongo-ghjh.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofit.create(ApiHome.class);


        progressBar.setVisibility(View.VISIBLE);
        Call<FeedPet> call = api.insert(post);

        call.enqueue(new Callback<FeedPet>() {
            @Override
            public void onResponse(Call<FeedPet> call, Response<FeedPet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FeedPet post = response.body();
                    Log.d("Perfil", "Perfil: " + post);

                    Toast.makeText(getContext(), "Post publicado", Toast.LENGTH_SHORT).show();
                    // Recupera o SharedPreferences
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("PetCache", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Remove o campo "selectedPets"
                    editor.remove("selectedPets");
                    editor.apply(); // Aplica as alterações




                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Log.e("FeedPet", "Erro: " + response.errorBody().toString());
                    Toast.makeText(getContext(), "Erro ao publicar o post", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FeedPet> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Erro ao carregar posts", Toast.LENGTH_SHORT).show();
                Log.e("FeedPet", "Erro: " + throwable.getMessage());

            }
        });


    }

    private void Sair() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }



}