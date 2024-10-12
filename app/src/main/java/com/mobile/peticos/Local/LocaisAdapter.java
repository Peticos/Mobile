package com.mobile.peticos.Local;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        holder.textViewNome.setText(local.getLocalName());
        holder.textViewDescricao.setText(local.getDescription());
        // Carrega a imagem a partir da URL usando Glide
        Glide.with(holder.imageView.getContext())
                .load(local.getLocalPicture()) // Certifique-se de que 'local.getLocalPicture()' retorna uma URL válida
                .into(holder.imageView);
        // Configura o clique do botão "Saiba mais"
        holder.btnSaibaMais.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), WebViewActivity.class);
            intent.putExtra("url", local.getLinkKnowMore());
            holder.itemView.getContext().startActivity(intent);
        });
        //holder.imageView.setImageResource(local.getLocalPicture());
    }

    @Override
    public int getItemCount() {
        return locaisList.size();
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewDescricao;
        ImageView imageView;
        TextView btnSaibaMais;

        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.nome_local);
            textViewDescricao = itemView.findViewById(R.id.descricao);
            imageView = itemView.findViewById(R.id.fotoLocal);
            btnSaibaMais = itemView.findViewById(R.id.btnSaibaMais);



        }
    }
}

