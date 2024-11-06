package com.mobile.peticos.Home.HomeDica;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.peticos.R;
import java.util.List;

public class AdapterCuriosidadesDiarias extends RecyclerView.Adapter<AdapterCuriosidadesDiarias.ViewHolder> {

    private List<String> itemList;
    private RecyclerView recyclerView;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int position = 0;

    public AdapterCuriosidadesDiarias(List<String> itemList, RecyclerView recyclerView) {
        this.itemList = itemList;
        this.recyclerView = recyclerView;
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

    public void startAutoScroll() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (position == getItemCount()) {
                    position = 0;
                }
                recyclerView.smoothScrollToPosition(position++);
                handler.postDelayed(runnable, 8000); // Intervalo de 3 segundos
            }
        };
        handler.postDelayed(runnable, 8000);
    }

    public void stopAutoScroll() {
        handler.removeCallbacks(runnable);
    }
}
