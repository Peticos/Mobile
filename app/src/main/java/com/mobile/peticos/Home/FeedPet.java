package com.mobile.peticos.Home;

public class FeedPet {

    private String username;
    private String petsInPhoto;
    private String daysAgo;
    private String likedBy;
    private String description;
    private int userPhotoResId;
    private int photoResId;

    // Construtor
    public FeedPet(String username, String petsInPhoto, String daysAgo, String likedBy, String description, int userPhotoResId, int photoResId) {
        this.username = username;
        this.petsInPhoto = petsInPhoto;
        this.daysAgo = daysAgo;
        this.likedBy = likedBy;
        this.description = description;
        this.userPhotoResId = userPhotoResId;
        this.photoResId = photoResId;
    }

    // Getters
    public String getUsername() { return username; }
    public String getPetsInPhoto() { return petsInPhoto; }
    public String getDaysAgo() { return daysAgo; }
    public String getLikedBy() { return likedBy; }
    public String getDescription() { return description; }
    public int getUserPhotoResId() { return userPhotoResId; }
    public int getPhotoResId() { return photoResId; }
}

