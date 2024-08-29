package com.mobile.peticos.feedFotoPet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.R;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class AdapterFeedPrincipal extends RecyclerView.Adapter <AdapterFeedPrincipal.MeuViewHolder> {
    private final List<Post> listaPost;

    public AdapterFeedPrincipal(List<Post> listaPost) {
        this.listaPost = listaPost;
    }
    @NonNull
    @Override
    public AdapterFeedPrincipal.MeuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Carregando templete de visualização
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.postagem_feed, parent, false);
        //Chamar o MeuViewHolder para carregar os dados
        return new AdapterFeedPrincipal.MeuViewHolder((ViewGroup) viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeedPrincipal.MeuViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return listaPost.size();
    }

    public static class MeuViewHolder extends RecyclerView.ViewHolder{
        public MeuViewHolder(@NonNull ViewGroup parent) {
            super(parent);
        }
    }
}
