package com.mobile.peticos.Perdidos;

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

public class AdapterPerdidos extends RecyclerView.Adapter<AdapterPerdidos.ViewHolder> {
    private final Context context;
    private final List<PetPerdido> petList;

    public AdapterPerdidos(Context context, List<PetPerdido> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_perdidos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetPerdido pet = petList.get(position);

        // Definindo as variáveis
        holder.userPhoto.setImageResource(pet.getUserPhotoResId());
        holder.fotoPrincipal.setImageResource(pet.getFotoPrincipalResId());
        holder.username.setText(pet.getUsername());
        holder.days.setText(pet.getDays());
        holder.petsInPhoto.setText(pet.getPetsInPhoto());
        holder.textView31.setText(pet.getTextView31());
        holder.textView32.setText(pet.getTextView32());
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView userPhoto;
        final ImageView fotoPrincipal;
        final TextView username;
        final TextView days;
        final TextView petsInPhoto;
        final TextView textView31;
        final TextView textView32;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userPhoto = itemView.findViewById(R.id.userPhoto);
            fotoPrincipal = itemView.findViewById(R.id.fotoPrincipal);
            username = itemView.findViewById(R.id.username);
            days = itemView.findViewById(R.id.days);
            petsInPhoto = itemView.findViewById(R.id.petsInPhoto);
            textView31 = itemView.findViewById(R.id.textView31);
            textView32 = itemView.findViewById(R.id.textView32);
        }
    }
}
