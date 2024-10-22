package com.mobile.peticos.Perfil.Profissional;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.mobile.peticos.Home.ApiHome;
import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.HomeFragment;
import com.mobile.peticos.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdicionarProduto#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdicionarProduto extends Fragment {
    Button btnPublicar, btnSair;

    EditText legenda, nomeProduto, valor, telefone;
    ImageView upload;


    public AdicionarProduto() {
        // Required empty public constructor
    }


    public static AdicionarProduto newInstance() {
        AdicionarProduto fragment = new AdicionarProduto();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_adicionar_produto, container, false);

        legenda = view.findViewById(R.id.legenda);
        nomeProduto = view.findViewById(R.id.NomeProduto);
        valor = view.findViewById(R.id.valor);
        telefone = view.findViewById(R.id.telefone);
        btnPublicar = view.findViewById(R.id.btnPublicar);
        upload = view.findViewById(R.id.upload);
        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicarProduto();
            }
        });



        return view;
    }
    private void PublicarProduto() {
        //mudar quando a camera tiver funcionando
        String url = "https://firebasestorage.googleapis.com/v0/b/peticos-b4633.appspot.com/o/720403.png?alt=media&token=2429fb00-920b-4979-a004-9d6c2034bda9"; // Exemplo de URL da imagem

        //mudar quando o cache funcionar
        int userId = 2;

        // Obter a data atual formatada
        String postDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        // Criar uma nova instância de FeedPet
        FeedPet post = new FeedPet(
                userId, // userId
                url, // picture
                legenda.getText().toString(), // caption
                postDate, // postDate
                true, // isMei
                Double.parseDouble(valor.getText().toString()), // price
                telefone.getText().toString(), // telephone
                nomeProduto.getText().toString() // productName
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
                    Log.d("Produto", "Produto: " + post);

                    Toast.makeText(getContext(), "Produto publicado", Toast.LENGTH_SHORT).show();



                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, HomeFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();


                } else {
                    Log.e("FeedPet", "Erro: " + response.errorBody().toString());
                    Toast.makeText(getContext(), "Erro ao publicar o Produto", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<FeedPet> call, Throwable throwable) {
                Toast.makeText(getContext(), "Erro ao carregar posts", Toast.LENGTH_SHORT).show();
                Log.e("FeedPet", "Erro: " + throwable.getMessage());

            }
        });


    }

}