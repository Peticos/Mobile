package com.mobile.peticos.Perfil.Tutor.AdapterLembretes;

import com.mobile.peticos.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    private List<Lembrete> items;

    public CarouselAdapter(List<Lembrete> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView time;


        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tituloLembrete);
            time = itemView.findViewById(R.id.horarioLembrete);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lembrete, parent, false); // Substitua `your_item_layout` pelo layout do seu item
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lembrete item = items.get(position);
        holder.title.setText(item.getTitle());
        holder.time.setText(item.getTime());
        // Lógica para configurar o ícone
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
