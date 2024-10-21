package com.mobile.peticos.Home;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobile.peticos.Home.ApiHome;
import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.HomeFragment;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.mobile.peticos.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdicionarAoFeedPrincipal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdicionarAoFeedPrincipal extends Fragment {

    Button btnPublicar, btnSair;
    ImageButton btn_voltar_publicacoes;
    TextView publicacoes;

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
    RecyclerView amiguinhos;

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
        amiguinhos = view.findViewById(R.id.amiguinhos);
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
                PublicarPost();
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

        return view;
    }

    private void PublicarPost() {
        // mudar para url quando funcionar
        String url = "https://firebasestorage.googleapis.com/v0/b/peticos-b4633.appspot.com/o/720403.png?alt=media&token=2429fb00-920b-4979-a004-9d6c2034bda9"; // Exemplo de URL da imagem

        // Obter a data atual formatada
        String postDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // mudar para os pets
        List<Integer> pets = Arrays.asList(1,3,6); // Usando Arrays.asList para criar a lista

        //mudar para o id do user quando funcionar
        int id = 2;

        // Criar uma nova instância de FeedPet
        FeedPet post = new FeedPet(
                id, // userId
                url, // picture
                legenda.getText().toString(), // caption
                pets, // pets
                postDate, // postDate
                false // isMei
        );

        String API = "https://apimongo-ghjh.onrender.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiHome api = retrofit.create(ApiHome.class);


        Call<FeedPet> call = api.insert(post);

        call.enqueue(new Callback<FeedPet>() {
            @Override
            public void onResponse(Call<FeedPet> call, Response<FeedPet> response) {
                if (response.isSuccessful() && response.body() != null) {
                    FeedPet post = response.body();
                    Log.d("Perfil", "Perfil: " + post);

                    Toast.makeText(getContext(), "Post publicado", Toast.LENGTH_SHORT).show();



                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();


                } else {
                    Log.e("FeedPet", "Erro: " + response.errorBody().toString());
                    Toast.makeText(getContext(), "Erro ao publicar o post", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FeedPet> call, Throwable throwable) {
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



    public void configurarPets(){
        //Call<List<ModelPetBanco>> call = apiPets.getPets();

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
    }

}