package com.mobile.peticos;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class AdicionarAoFeedTriste extends Fragment {

    // Constantes
    private static final String CHANNEL_ID = "channel_id";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_adicionar_ao_feed_triste, container, false);

        // Configurar botão e ações
        Button button = view.findViewById(R.id.btnSalvar); // substitua pelo ID real do botão
        button.setOnClickListener(v -> {
            // Ação "x" e notificação
            RegistrarPetPerdido(v);
        });

        return view;
    }

    // Função que executa a ação "x" e envia a notificação
    public void RegistrarPetPerdido(View view) {
        // Sua lógica de ação "x" aqui

        // Enviar notificação
        notificar();
    }

    // Função que executa a notificação
    public void notificar() {
        Context context = getContext(); // Obter o contexto do fragmento
        Intent intentAndroid = new Intent(context, NotificationReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentAndroid, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.attention)
                .setContentTitle("Pet Perdido!!!")
                .setContentText("Um pet foi perdido perto de você. Ajude a encontrá-lo e trazer segurança para ele!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Criar canal de notificação
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Notificar", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);

        // Mostrar a notificação
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // Pedir permissão se necessário
            return;
        }
        notificationManager.notify(1, builder.build());
    }
}
