package com.mobile.peticos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class AreaRestrita extends AppCompatActivity {
    WebView webViewAreaRestrita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_restrita);
        webViewAreaRestrita = findViewById(R.id.webViewAreaRestrita);
        webViewAreaRestrita.loadUrl("https://area-restrita-dados-3.onrender.com/previsao-user");
    }
}