package com.mobile.peticos.Perdidos.Adcionar;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.peticos.Padrao.Upload.Camera;
import com.mobile.peticos.Perdidos.ApiPerdidos;
import com.mobile.peticos.Perdidos.PerdidoFragment;

import com.mobile.peticos.Padrao.NotificationReciver;
import com.mobile.peticos.Perdidos.PetPerdido;
import com.mobile.peticos.Perfil.Pet.API.APIPets;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.R;

import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.mobile.peticos.Padrao.ModelRetorno;

public class AdicionarAoFeedTriste extends Fragment {
    Button bntSair;
    ImageButton btn_voltar_publicacoes;
    EditText descricao, bairro, data, referencia;

    ImageView upload;
    TextView publicacoes;
    RecyclerView amiguinhos;
    private ActivityResultLauncher<Intent> cameraLauncher;

    private static final String CHANNEL_ID = "channel_id";
    String url;

    public static AdicionarAoFeedTriste newInstance() {
        return new AdicionarAoFeedTriste();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adicionar_ao_feed_triste, container, false);

        Button btnPublicar = view.findViewById(R.id.btnPublicar);
        btnPublicar.setOnClickListener(v -> {
            RegistrarPetPerdido(v);
        });

        upload = view.findViewById(R.id.upload);
        descricao = view.findViewById(R.id.descricao);
        bairro = view.findViewById(R.id.bairro);
        data = view.findViewById(R.id.data);
        amiguinhos = view.findViewById(R.id.amiguinhos);
        referencia = view.findViewById(R.id.referencia);

        SharedPreferences limparCache = getActivity().getSharedPreferences("PetTriste", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = limparCache.edit();
        editor.putString("selectedPet", null); // Corrigido: usar 'selectedPet' como chave
        editor.putString("nome",null);
        editor.apply();
        bntSair = view.findViewById(R.id.btnSair);
        btn_voltar_publicacoes = view.findViewById(R.id.btn_voltar_publicacoes);
        publicacoes = view.findViewById(R.id.publicacoes);

        btn_voltar_publicacoes.setOnClickListener(v -> {
            navigateToPerdidoFragment();
        });

        publicacoes.setOnClickListener(v -> {
            navigateToPerdidoFragment();
        });

        // Criar canal de notificação
        createNotificationChannel();
        setupRetrofit();
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("Perfil", MODE_PRIVATE);

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


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        amiguinhos.setLayoutManager(layoutManager);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apipeticos-ltwk.onrender.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIPets apiPets = retrofit.create(APIPets.class);

        Call<List<ModelPetBanco>> call = apiPets.getPets(sharedPreferences.getString("nome_usuario", "modolo"));
        call.enqueue(new Callback<List<ModelPetBanco>>() {
            @Override
            public void onResponse(Call<List<ModelPetBanco>> call, Response<List<ModelPetBanco>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ModelPetBanco> listaPets = response.body();
                    AdapterPetFeedTriste adapterPet = new AdapterPetFeedTriste(listaPets);

                    amiguinhos.setAdapter(adapterPet);
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


        return view;
    }

    private ApiPerdidos apiPerdidos;
    private void setupRetrofit() {
        String API = "https://apipeticos-ltwk.onrender.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiPerdidos = retrofit.create(ApiPerdidos.class);
    }


    private void navigateToPerdidoFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainerView, PerdidoFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void RegistrarPetPerdido(View view) {
                Date dataAtual = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        String dataFormatada = formato.format(dataAtual); // postTime formatada
        // Tratamento da data de perda
        String dataa = data.getText().toString(); // Entrada do campo de texto
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()); // Formato de entrada MM-dd-yyyy
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

        Date lostDate = null;
        try {
            lostDate = inputFormat.parse(dataa); // Convertendo a entrada de texto para Date
        } catch (ParseException e) {
            e.printStackTrace(); // Tratando erro de parsing
        }

        String dataPerdaFormatada = lostDate != null ? outputFormat.format(lostDate) : dataFormatada; // Se a data de perda for válida, formatar


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Perfil", Context.MODE_PRIVATE);

        SharedPreferences pet = getActivity().getSharedPreferences("PetTriste", Context.MODE_PRIVATE);


        String idPet = pet.getString("selectedPet", "112");
        int idPetInt = Integer.parseInt(idPet);

        PetPerdido petPerdido = new PetPerdido(
                idPetInt,
                sharedPreferences.getInt("id", 278),
                bairro.getText().toString(),
                pet.getString("nome", "pet perdido"),
                descricao.getText().toString(),
                dataFormatada,
                url,
                referencia.getText().toString(),
                dataPerdaFormatada
        );


        Call<ModelRetorno> call = apiPerdidos.isertPerdido(petPerdido);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelRetorno perdido = response.body();
                    Log.d("Perfil", "perdido: " + perdido);

                    Toast.makeText(getContext(), "Post publicado", Toast.LENGTH_SHORT).show();
                    notificar();
                    SharedPreferences.Editor editor = pet.edit();
                    editor.putString("selectedPet", "0"); // Corrigido: limpar 'selectedPet'
                    editor.apply();



                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, PerdidoFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();


                } else {
                    Log.e("FeedPet", "Erro: " + response.errorBody().toString());
                    Log.e("FeedPet", "Erro código: " + response.code());
                    Log.e("FeedPet", "Erro mensagem: " + response.message());

                    Toast.makeText(getContext(), "Erro ao publicar o post", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Toast.makeText(getContext(), "Erro ao carregar posts", Toast.LENGTH_SHORT).show();
                Log.e("FeedPet", "Erro: " + throwable.getMessage());

            }
        });





    }

    private void notificar() {
        Context context = getContext();
        if (context == null) return; // Verifica se o contexto é válido

        Intent intentAndroid = new Intent(context, NotificationReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentAndroid, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_app_logo)
                .setContentTitle("Pet Perdido!!!")
                .setContentText("Um pet foi perdido perto de você. Ajude a encontrá-lo e trazer segurança para ele!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            notificationManager.notify(1, builder.build());
        } else {
            // Solicitar permissão se não tiver
            Log.e("AdicionarAoFeedTriste", "Permissão de notificações não concedida.");
        }
    }

    private void createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notificar", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }


}