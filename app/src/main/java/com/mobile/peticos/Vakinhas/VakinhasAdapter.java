package com.mobile.peticos.Vakinhas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.peticos.R;
import java.util.List;

public class VakinhasAdapter extends RecyclerView.Adapter<VakinhasAdapter.ViewHolder> {
    private final Context context;
    private final List<Vakinha> vakinhaList;

    public VakinhasAdapter(Context context, List<Vakinha> vakinhaList) {
        this.context = context;
        this.vakinhaList = vakinhaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_vakinhas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Vakinha vakinha = vakinhaList.get(position);

        // Definindo as variáveis com as imagens locais
        holder.userPhoto.setImageResource(vakinha.getUserPhotoResId());
        holder.fotoPrincipal.setImageResource(vakinha.getFotoPrincipalResId());
        holder.username.setText(vakinha.getUsername());
        holder.days.setText(vakinha.getDays());
        holder.petsInPhoto.setText(vakinha.getPetsInPhoto());
        holder.descricao.setText(vakinha.getDescricao());
    }

    @Override
    public int getItemCount() {
        return vakinhaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView userPhoto;
        final ImageView fotoPrincipal;
        final TextView username;
        final TextView days;
        final TextView petsInPhoto;
        final TextView descricao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userPhoto = itemView.findViewById(R.id.userPhoto);
            fotoPrincipal = itemView.findViewById(R.id.fotoPrincipal);
            username = itemView.findViewById(R.id.username);
            days = itemView.findViewById(R.id.days);
            petsInPhoto = itemView.findViewById(R.id.petsInPhoto);
            descricao = itemView.findViewById(R.id.descricao);
        }
    }
}
