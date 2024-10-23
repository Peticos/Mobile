package com.mobile.peticos.Home.HomeDica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.mobile.peticos.R; // Certifique-se de que este é o pacote correto

public class AdapterCuriosidadesDiarias extends RecyclerView.Adapter<AdapterCuriosidadesDiarias.ViewHolder> {

    private List<String> itemList;

    public AdapterCuriosidadesDiarias(List<String> itemList) {
        this.itemList = itemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView textViewItem;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.CardViewCuriosidade);
            textViewItem = itemView.findViewById(R.id.TextCuriosidade);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_curiosidades, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = itemList.get(position);
        holder.textViewItem.setText(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
