package com.mobile.peticos.Local;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.peticos.R;
import java.util.List;

public class LocaisAdapter extends RecyclerView.Adapter<LocaisAdapter.LocalViewHolder> {

    private List<Local> locaisList;

    public LocaisAdapter(List<Local> locaisList) {
        this.locaisList = locaisList;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_locais, parent, false);
        return new LocalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        Local local = locaisList.get(position);
        holder.textViewNome.setText(local.getNome());
        holder.textViewDescricao.setText(local.getDescricao());
        holder.imageView.setImageResource(local.getImagem());
    }

    @Override
    public int getItemCount() {
        return locaisList.size();
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewDescricao;
        ImageView imageView;

        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textView27);
            textViewDescricao = itemView.findViewById(R.id.textView32);
            imageView = itemView.findViewById(R.id.fotoPerfil);
        }
    }
}

