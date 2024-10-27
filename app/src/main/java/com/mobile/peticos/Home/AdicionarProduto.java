package com.mobile.peticos.Home;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Padrao.Upload.Camera;
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
    LinearLayout voltar;
    EditText legenda, nomeProduto, valor, telefone;
    private ActivityResultLauncher<Intent> cameraLauncher;
    String url;

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

        voltar = view.findViewById(R.id.voltar);
        btnSair = view.findViewById(R.id.btnSair);
        legenda = view.findViewById(R.id.legenda);
        nomeProduto = view.findViewById(R.id.NomeProduto);
        valor = view.findViewById(R.id.valor);
        telefone = view.findViewById(R.id.telefone);
        btnPublicar = view.findViewById(R.id.btnPublicar);
        upload = view.findViewById(R.id.upload);

        configurarMascaraTelefone(telefone);

        upload.setOnClickListener(new View.OnClickListener() {
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
                                        .into(upload);

                            }

                        }
                    }
                }
        );


        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PublicarProduto();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });

        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerView, HomeFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        });



        return view;
    }
    private void PublicarProduto() {

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Perfil", MODE_PRIVATE);


        // Obter a data atual formatada
        String postDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        List<String> likes = Arrays.asList();
        List<String> shares = Arrays.asList();

        String telefoneFormatado = telefone.getText().toString().replaceAll("[^\\d]", "");

        if (url == null) {
            Toast.makeText(getContext(), "Imagem Obrigatória", Toast.LENGTH_SHORT).show();
            RequestOptions options = new RequestOptions()
                    .centerCrop();
            Glide.with(this)
                    .load(R.drawable.adicionar_imagem_vermelho)
                    .apply(options)
                    .into(upload);
            return;
        }
        if (legenda.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Legenda Obrigatória", Toast.LENGTH_SHORT).show();
            legenda.setError("Legenda é obrigatória");
            return;
        }
        if (nomeProduto.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Nome do Produto Obrigatório", Toast.LENGTH_SHORT).show();
            nomeProduto.setError("Nome do Produto é obrigatório");
            return;
        }
        if (valor.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Valor Obrigatório", Toast.LENGTH_SHORT).show();
            valor.setError("Valor é obrigatório");
            return;
        }
        if (telefone.getText().toString().isEmpty() || telefoneFormatado.length() != 11) {
            Toast.makeText(getContext(), "Telefone Invalido", Toast.LENGTH_SHORT).show();
            telefone.setError("Telefone Invalido");
            return;
        }
        // Criar uma nova instância de FeedPet
        FeedPet post = new FeedPet(
                sharedPreferences.getInt("id", 284), // userId
                likes,
                shares,
                url, // picture
                legenda.getText().toString(), // caption
                postDate, // postDate
                true, // isMeis
                Double.parseDouble(valor.getText().toString()), // price
                telefoneFormatado, // telephone
                nomeProduto.getText().toString() // productName
        );

        String API = "https://api-mongo-i1jq.onrender.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

<<<<<<< HEAD
        AdicionarAoFeedPrincipal.APIHome api = retrofit.create(AdicionarAoFeedPrincipal.APIHome.class);
=======
        ApiHome api = retrofit.create(ApiHome.class);
>>>>>>> 34a2e41f4c9c68318314c9ff57d7dde46d29b033


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

    private void configurarMascaraTelefone(EditText telefone) {
        telefone.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating = false;
            private final String mask = "(##) #####-####";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String unmasked = s.toString().replaceAll("[^\\d]", "");
                StringBuilder formatted = new StringBuilder();

                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m == '#') {
                        if (i < unmasked.length()) {
                            formatted.append(unmasked.charAt(i));
                            i++;
                        } else {
                            break;
                        }
                    } else {
                        formatted.append(m);
                    }
                }

                isUpdating = true;
                telefone.setText(formatted.toString());
                telefone.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}