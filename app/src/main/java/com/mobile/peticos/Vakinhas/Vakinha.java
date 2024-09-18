package com.mobile.peticos.Vakinhas;

public class Vakinha {
    private final int userPhotoResId;
    private final int fotoPrincipalResId;
    private final String username;
    private final String days;
    private final String petsInPhoto;
    private final String descricao;

    public Vakinha(int userPhotoResId, int fotoPrincipalResId, String username, String days, String petsInPhoto, String descricao) {
        this.userPhotoResId = userPhotoResId;
        this.fotoPrincipalResId = fotoPrincipalResId;
        this.username = username;
        this.days = days;
        this.petsInPhoto = petsInPhoto;
        this.descricao = descricao;
    }

    public int getUserPhotoResId() {
        return userPhotoResId;
    }

    public int getFotoPrincipalResId() {
        return fotoPrincipalResId;
    }

    public String getUsername() {
        return username;
    }

    public String getDays() {
        return days;
    }

    public String getPetsInPhoto() {
        return petsInPhoto;
    }

    public String getDescricao() {
        return descricao;
    }
}
