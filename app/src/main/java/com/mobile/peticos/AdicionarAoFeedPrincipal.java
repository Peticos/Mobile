package com.mobile.peticos;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.peticos.Cadastros.CadastroTutor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdicionarAoFeedPrincipal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdicionarAoFeedPrincipal extends Fragment {



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
        View view = inflater.inflate(R.layout.fragment_adicionar_ao_feed_principal, container, false);

        btnUpload = view.findViewById(R.id.btnUpload);
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




        return view;
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