package com.mobile.peticos.Local;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
                .error(R.drawable.fotogenerica)
                .into(holder.imageView);
        // Configura o clique do botão "Saiba mais"
        holder.btnSaibaMais.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), WebViewActivity.class);
            intent.putExtra("url", local.getLinkKnowMore());
            intent.putExtra("titulo", "Locais");
            holder.itemView.getContext().startActivity(intent);
        });
        //Configurar localizacao
        int streetNum = local.getStreetNum();

        String localizacao = local.getStreet() + " " + streetNum + " " + local.getNeighborhood() + " " + local.getCity();
        holder.btnLocalizacao.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
            intent.putExtra("local", localizacao);
            holder.itemView.getContext().startActivity(intent);
        });
        // Configurar o telefone
        String numero = local.getPhone(); // Substitua com o número do pet
        holder.ic_telefone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + numero));
            v.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return locaisList.size();
    }

    public static class LocalViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewDescricao;
        ImageView imageView, btnLocalizacao, ic_telefone;
        TextView btnSaibaMais;

        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNome = itemView.findViewById(R.id.nome_local);
            textViewDescricao = itemView.findViewById(R.id.descricao);
            imageView = itemView.findViewById(R.id.fotoLocal);
            btnSaibaMais = itemView.findViewById(R.id.btnSaibaMais);
            btnLocalizacao = itemView.findViewById(R.id.btnLocalizacao);
            ic_telefone =  itemView.findViewById(R.id.ic_telefone);



        }
    }
}
