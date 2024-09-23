package com.mobile.peticos.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.peticos.R;
import java.util.List;

public class FeedPetsAdapter extends RecyclerView.Adapter<FeedPetsAdapter.FeedPetsViewHolder> {

    private List<FeedPet> feedList;
    private OnItemClickListener onItemClickListener;

    // Interface para tratar os cliques nos itens
    public interface OnItemClickListener {
        void onItemClick(FeedPet feedPet);
    }

    public FeedPetsAdapter(List<FeedPet> feedList, OnItemClickListener onItemClickListener) {
        this.feedList = feedList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public FeedPetsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_feed_pets, parent, false);
        return new FeedPetsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedPetsViewHolder holder, int position) {
        FeedPet feedPet = feedList.get(position);

        // Bind data to views
        holder.username.setText(feedPet.getUsername());
        holder.petsInPhoto.setText(feedPet.getPetsInPhoto());
        holder.days.setText(feedPet.getDaysAgo());
        holder.likedBy.setText(feedPet.getLikedBy());
        holder.description.setText(feedPet.getDescription());

        // Set the image resource (you can replace this with image loading libraries like Glide or Picasso)
        holder.userPhoto.setImageResource(feedPet.getUserPhotoResId());
        holder.photo.setImageResource(feedPet.getPhotoResId());

        // Handle item clicks
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(feedPet));
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    // ViewHolder class to represent each item
    public static class FeedPetsViewHolder extends RecyclerView.ViewHolder {

        ImageView userPhoto;
        TextView username;
        TextView petsInPhoto;
        TextView days;
        ImageView photo;
        ImageButton shareButton, commentButton, likeButton;
        TextView likedBy;
        TextView description;

        public FeedPetsViewHolder(@NonNull View itemView) {
            super(itemView);

            userPhoto = itemView.findViewById(R.id.userPhoto);
            username = itemView.findViewById(R.id.username);
            petsInPhoto = itemView.findViewById(R.id.petsInPhoto);
            days = itemView.findViewById(R.id.days);
            photo = itemView.findViewById(R.id.photo);
            commentButton = itemView.findViewById(R.id.commentButton);
            likeButton = itemView.findViewById(R.id.likeButton);
            likedBy = itemView.findViewById(R.id.liked_by);
            description = itemView.findViewById(R.id.decription);
        }
    }
}
