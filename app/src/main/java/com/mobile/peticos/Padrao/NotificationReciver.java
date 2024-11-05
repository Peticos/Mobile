package com.mobile.peticos.Padrao;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mobile.peticos.MainActivity;

public class NotificationReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Abre a MainActivity ao clicar na notificação
        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(mainIntent);
    }
}
