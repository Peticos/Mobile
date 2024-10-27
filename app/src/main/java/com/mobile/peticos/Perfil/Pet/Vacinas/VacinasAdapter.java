package com.mobile.peticos.Perfil.Pet.Vacinas;

import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobile.peticos.Local.Local;
import com.mobile.peticos.Local.MapsActivity;
import com.mobile.peticos.Local.WebViewActivity;
import com.mobile.peticos.R;

import org.w3c.dom.Text;

import java.util.List;

public class VacinasAdapter extends RecyclerView.Adapter<VacinasAdapter.VacinaViewHolder> {

    private List<ModelVacina> VacinasList;

    public VacinasAdapter(List<ModelVacina> VacinasList) {
        this.VacinasList = VacinasList;
    }

    @NonNull
    @Override
    public VacinaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_item_vacina, parent, false);
        return new VacinaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VacinaViewHolder holder, int position) {
        ModelVacina vacina = VacinasList.get(position);

        holder.nomeVacina.setText(vacina.getName());

        if(vacina.getNumDoses() == 1){
            holder.circulo5.setVisibility(View.VISIBLE);
            holder.data5.setVisibility(View.VISIBLE);
        }
        else if(vacina.getNumDoses() == 2){
            holder.circulo3.setVisibility(View.VISIBLE);
            holder.circulo7.setVisibility(View.VISIBLE);
            holder.data3.setVisibility(View.VISIBLE);
            holder.data7.setVisibility(View.VISIBLE);
        }
        else if(vacina.getNumDoses() == 3){
            holder.circulo1.setVisibility(View.VISIBLE);
            holder.circulo5.setVisibility(View.VISIBLE);
            holder.circulo9.setVisibility(View.VISIBLE);
            holder.data1.setVisibility(View.VISIBLE);
            holder.data5.setVisibility(View.VISIBLE);
            holder.data9.setVisibility(View.VISIBLE);
        }
        else if(vacina.getNumDoses() == 4){
            holder.circulo2.setVisibility(View.VISIBLE);
            holder.circulo4.setVisibility(View.VISIBLE);
            holder.circulo6.setVisibility(View.VISIBLE);
            holder.circulo8.setVisibility(View.VISIBLE);
            holder.data2.setVisibility(View.VISIBLE);
            holder.data4.setVisibility(View.VISIBLE);
            holder.data6.setVisibility(View.VISIBLE);
            holder.data8.setVisibility(View.VISIBLE);
        }
        else if(vacina.getNumDoses() == 5){
            holder.circulo1.setVisibility(View.VISIBLE);
            holder.circulo3.setVisibility(View.VISIBLE);
            holder.circulo5.setVisibility(View.VISIBLE);
            holder.circulo7.setVisibility(View.VISIBLE);
            holder.circulo9.setVisibility(View.VISIBLE);
            holder.data1.setVisibility(View.VISIBLE);
            holder.data5.setVisibility(View.VISIBLE);
            holder.data3.setVisibility(View.VISIBLE);
            holder.data7.setVisibility(View.VISIBLE);
            holder.data9.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return VacinasList.size();
    }

    public static class VacinaViewHolder extends RecyclerView.ViewHolder {
        TextView nomeVacina;
        ImageView circulo1, circulo2, circulo3, circulo4,circulo5, circulo6, circulo7, circulo8, circulo9;
        TextView data1,data2, data3, data4, data5, data6, data7,data8, data9;


        public VacinaViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeVacina = itemView.findViewById(R.id.nomeVacina);
            circulo1 = itemView.findViewById(R.id.circulo1);
            circulo2 = itemView.findViewById(R.id.circulo2);
            circulo3 = itemView.findViewById(R.id.circulo3);
            circulo4 = itemView.findViewById(R.id.circulo4);
            circulo5 = itemView.findViewById(R.id.circulo5);
            circulo6 = itemView.findViewById(R.id.circulo6);
            circulo7 = itemView.findViewById(R.id.circulo7);
            circulo8 = itemView.findViewById(R.id.circulo8);
            circulo9 = itemView.findViewById(R.id.circulo9);
            data1 = itemView.findViewById(R.id.data1);
            data2 = itemView.findViewById(R.id.data2);
            data3 = itemView.findViewById(R.id.data3);
            data4 = itemView.findViewById(R.id.data4);
            data5 = itemView.findViewById(R.id.data5);
            data6 = itemView.findViewById(R.id.data6);
            data7 = itemView.findViewById(R.id.data7);
            data8 = itemView.findViewById(R.id.data8);
            data9 = itemView.findViewById(R.id.data9);




        }
    }
}
