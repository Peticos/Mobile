package com.mobile.peticos.Perdidos;

public class PetPerdido {
    private final int userPhotoResId;
    private final int fotoPrincipalResId;
    private final String username;
    private final String days;
    private final String petsInPhoto;
    private final String textView31;
    private final String textView32;

    public PetPerdido(int userPhotoResId, int fotoPrincipalResId, String username, String days, String petsInPhoto, String textView31, String textView32) {
        this.userPhotoResId = userPhotoResId;
        this.fotoPrincipalResId = fotoPrincipalResId;
        this.username = username;
        this.days = days;
        this.petsInPhoto = petsInPhoto;
        this.textView31 = textView31;
        this.textView32 = textView32;
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

    public String getTextView31() {
        return textView31;
    }

    public String getTextView32() {
        return textView32;
    }
}
