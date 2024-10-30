package com.mobile.peticos.Local;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobile.peticos.R;

import org.w3c.dom.Text;

public class WebViewActivity extends AppCompatActivity {
    WebView webView;
    TextView titulo;
    ImageButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        titulo = findViewById(R.id.titulo);
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(v -> {
            finish();
        });

        titulo.setText(getIntent().getStringExtra("titulo"));

        webView.getSettings().setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");
        if (url != null) {
            webView.loadUrl(url);
        }
    }
}