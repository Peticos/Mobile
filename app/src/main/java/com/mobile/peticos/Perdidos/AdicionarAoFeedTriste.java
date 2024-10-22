package com.mobile.peticos.Perdidos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobile.peticos.Perdidos.PerdidoFragment;

import com.mobile.peticos.Padrao.NotificationReciver;
import com.mobile.peticos.R;

import android.widget.Toast;

import com.mobile.peticos.R;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    TextView publicacoes;

    private static final String CHANNEL_ID = "channel_id";

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

        return view;
    }

    private ApiPerdidos apiPerdidos;
    private void setupRetrofit() {
        String API = "https://apipeticos.onrender.com";
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
        String dataFormatada = formato.format(dataAtual);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Perfil", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", 278);


        PetPerdido petPerdido = new PetPerdido(
                18,
                id,
                "Lapa",
                "OIIIIIIII",
                "teste.",
                dataFormatada,
                "https://firebasestorage.googleapis.com/v0/b/peticos-b4633.appspot.com/o/17589433.548cad5c026f1.jpg?alt=media&token=59237338-3f7b-4d0d-b656-f1d0f27d150a",
                "Rua das Flores",
                789,
                "2024-10-21"
        );


        Call<ModelRetorno> call = apiPerdidos.isertPerdido(petPerdido);
        call.enqueue(new Callback<ModelRetorno>() {
            @Override
            public void onResponse(Call<ModelRetorno> call, Response<ModelRetorno> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ModelRetorno perdido = response.body();
                    Log.d("Perfil", "perdido: " + perdido);

                    Toast.makeText(getContext(), "Post publicado", Toast.LENGTH_SHORT).show();



                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragmentContainerView, PerdidoFragment.newInstance());
                    transaction.addToBackStack(null);
                    transaction.commit();


                } else {
                    Log.e("FeedPet", "Erro: " + response.errorBody().toString());
                    Toast.makeText(getContext(), "Erro ao publicar o post", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelRetorno> call, Throwable throwable) {
                Toast.makeText(getContext(), "Erro ao carregar posts", Toast.LENGTH_SHORT).show();
                Log.e("FeedPet", "Erro: " + throwable.getMessage());

            }
        });




        notificar();
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
<<<<<<< HEAD
}
=======

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
>>>>>>> 76312e3696b5ddca060882ef5836125117b90647
