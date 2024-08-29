package com.mobile.peticos.feedFotoPet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mobile.peticos.R;

import java.util.ArrayList;
import java.util.List;

public class feedFotoPet extends AppCompatActivity {
    RecyclerView recycle;
    List<Post> listaPost = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_foto_pet);
        recycle = findViewById(R.id.recycleView);

        //Configurar o adapter
        AdapterFeedPrincipal adapter = new AdapterFeedPrincipal(listaPost);
        recycle.setAdapter(adapter);
        //Configurar apresentação do recycle
        recycle.setLayoutManager(new LinearLayoutManager(this));
    }
}