package com.mobile.peticos;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReciver {

    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Notificação recebida!", Toast.LENGTH_SHORT).show();
    }
}
