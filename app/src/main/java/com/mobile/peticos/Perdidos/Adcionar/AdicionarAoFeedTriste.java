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
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.mobile.peticos.Cadastros.Bairros.APIBairro;
import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.mobile.peticos.Padrao.ModelRetorno;

public class AdicionarAoFeedTriste extends Fragment {
    Button bntSair, btnPublicar;
    ImageButton btn_voltar_publicacoes;
    EditText descricao, data, referencia;
    AutoCompleteTextView bairro;
    private Retrofit retrofit;
    ImageView upload;
    TextView publicacoes, petsInvalidos;
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

        btnPublicar = view.findViewById(R.id.btnPublicar);
        btnPublicar.setOnClickListener(v -> {
            RegistrarPetPerdido(v);
        });

        upload = view.findViewById(R.id.upload);
        descricao = view.findViewById(R.id.descricao);
        bairro = view.findViewById(R.id.bairro);
        data = view.findViewById(R.id.data);
        amiguinhos = view.findViewById(R.id.amiguinhos);
        referencia = view.findViewById(R.id.referencia);
        petsInvalidos = view.findViewById(R.id.petsInvalidos);

        carregarBairros();
        formatarData(data);


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
        String dataa = data.getText().toString().replaceAll("[^\\d]", ""); // Entrada do campo de texto
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()); // Formato de entrada MM-dd-yyyy
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

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

        //Validando campos
        if(url == null){
            Toast.makeText(getContext(), "Imagem Obrigatória", Toast.LENGTH_SHORT).show();
            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .transform(new RoundedCorners(30));
            Glide.with(this)
                    .load(R.drawable.adicionar_imagem_amarelo)
                    .apply(options)
                    .into(upload);
            return;
        }
        if(descricao.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Legenda Obrigatória", Toast.LENGTH_SHORT).show();
            descricao.setError("Descrição é obrigatória");
            return;
        }
        if(bairro.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Bairro Obrigatório", Toast.LENGTH_SHORT).show();
            bairro.setError("Bairro é obrigatório");
            return;
        }
        if(dataa.isEmpty()){
            Toast.makeText(getContext(), dataa, Toast.LENGTH_SHORT).show();
            Toast.makeText(getContext(), "Data Obrigatória", Toast.LENGTH_SHORT).show();
            data.setError("Data é obrigatória");
            return;
        }
        if(referencia.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Referência Obrigatória", Toast.LENGTH_SHORT).show();
            referencia.setError("Referência é obrigatória");
            return;
        }
        if(idPetInt == 0){
            Toast.makeText(getContext(), "Selecione pelo menos um pet!", Toast.LENGTH_SHORT).show();
            petsInvalidos.setVisibility(View.VISIBLE);
            return;
        }

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

    // Carrega os bairros usando a API
    private void carregarBairros() {
        String API = "https://apipeticos-ltwk.onrender.com";
        retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIBairro apiBairro = retrofit.create(APIBairro.class);
        Call<List<ModelBairro>> call = apiBairro.getAll();

        // Executar chamada para pegar os bairros
        call.enqueue(new Callback<List<ModelBairro>>() {
            @Override
            public void onResponse(Call<List<ModelBairro>> call, Response<List<ModelBairro>> response) {
                List<ModelBairro> bairrosList = response.body();
                List<String> bairrosNomes = new ArrayList<>();
                if (bairrosList != null) {
                    for (ModelBairro bairro : bairrosList) {
                        bairrosNomes.add(bairro.getNeighborhood());
                    }
                    ArrayAdapter<String> adapterBairro = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_dropdown_item_1line,
                            bairrosNomes
                    );
                    bairro.setAdapter(adapterBairro);
                    bairro.setThreshold(1);
                }
            }

            @Override
            public void onFailure(Call<List<ModelBairro>> call, Throwable throwable) {
                Toast.makeText(AdicionarAoFeedTriste.newInstance().getContext(), "Erro ao carregar bairros", Toast.LENGTH_SHORT).show();
            }
        });

        btnPublicar.setOnClickListener(this::RegistrarPetPerdido);
    }

    public static void formatarData(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating; // Para evitar chamadas recursivas
            private String old = ""; // Para armazenar o texto anterior
            private final String format = "##/##/####"; // Formato da máscara (dd/MM/yyyy)

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Não precisamos de implementação aqui
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Não precisamos de implementação aqui
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) {
                    return; // Evita chamadas recursivas
                }

                String input = s.toString();
                StringBuilder formatted = new StringBuilder();

                // Removendo caracteres não numéricos
                input = input.replaceAll("[^\\d]", "");

                int j = 0;
                for (int i = 0; i < format.length(); i++) {
                    if (format.charAt(i) == '#') {
                        if (j < input.length()) {
                            formatted.append(input.charAt(j));
                            j++;
                        } else {
                            break; // Para quando não há mais dígitos
                        }
                    } else {
                        formatted.append(format.charAt(i)); // Adiciona caracteres da máscara
                    }
                }

                isUpdating = true;
                editText.setText(formatted.toString());
                editText.setSelection(formatted.length()); // Manter o cursor no final
                isUpdating = false;
            }
        });
    }
}