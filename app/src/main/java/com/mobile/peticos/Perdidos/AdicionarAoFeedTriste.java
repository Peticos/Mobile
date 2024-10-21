package com.mobile.peticos.Perdidos;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

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

<<<<<<< HEAD:app/src/main/java/com/mobile/peticos/AdicionarAoFeedTriste.java
=======
import com.mobile.peticos.Padrao.NotificationReciver;
import com.mobile.peticos.R;

>>>>>>> 7e7a0039b99430db2598f138b7589b2ac225f26e:app/src/main/java/com/mobile/peticos/Perdidos/AdicionarAoFeedTriste.java
public class AdicionarAoFeedTriste extends Fragment {
    Button bntSair;
    ImageButton btn_voltar_publicacoes;
    TextView publicacoes;

    private static final String CHANNEL_ID = "channel_id";

    public static AdicionarAoFeedTriste newInstance() {
        return new AdicionarAoFeedTriste();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_adicionar_ao_feed_triste, container, false);

        Button btnPublicar = view.findViewById(R.id.btnPublicar);
        btnPublicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificar();  // Chama a função notificar corretamente.
            }
        });

        bntSair = view.findViewById(R.id.btnSair);
        btn_voltar_publicacoes = view.findViewById(R.id.btn_voltar_publicacoes);
        publicacoes = view.findViewById(R.id.publicacoes);

        btn_voltar_publicacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, PerdidoFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        publicacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, PerdidoFragment.newInstance());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;  // Adicione o retorno do view aqui.
    }

    // Método notificar deve ser fora do onCreateView.
    public void notificar() {
        Context context = getContext();
        Intent intentAndroid = new Intent(context, NotificationReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentAndroid, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon_app_logo)
                .setContentTitle("Pet Perdido!!!")
                .setContentText("Um pet foi perdido perto de você. Ajude a encontrá-lo e trazer segurança para ele!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notificar", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}