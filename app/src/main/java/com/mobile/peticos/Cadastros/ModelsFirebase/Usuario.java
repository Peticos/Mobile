package com.mobile.peticos.Cadastros.ModelsFirebase;

import java.util.List;

public class Usuario {
    private int ID;
    private String url;

    private List<Posts> Posts;
    private List<PetPerdido> PetPerdido;
    private List<PerfilPet> PerfilPet;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<com.mobile.peticos.Cadastros.ModelsFirebase.Posts> getPosts() {
        return Posts;
    }

    public void setPosts(List<com.mobile.peticos.Cadastros.ModelsFirebase.Posts> posts) {
        Posts = posts;
    }

    public List<com.mobile.peticos.Cadastros.ModelsFirebase.PetPerdido> getPetPerdido() {
        return PetPerdido;
    }

    public void setPetPerdido(List<com.mobile.peticos.Cadastros.ModelsFirebase.PetPerdido> petPerdido) {
        PetPerdido = petPerdido;
    }

    public List<com.mobile.peticos.Cadastros.ModelsFirebase.PerfilPet> getPerfilPet() {
        return PerfilPet;
    }

    public void setPerfilPet(List<com.mobile.peticos.Cadastros.ModelsFirebase.PerfilPet> perfilPet) {
        PerfilPet = perfilPet;
    }

    public Usuario(int ID, String url, List<com.mobile.peticos.Cadastros.ModelsFirebase.Posts> posts, List<com.mobile.peticos.Cadastros.ModelsFirebase.PetPerdido> petPerdido, List<com.mobile.peticos.Cadastros.ModelsFirebase.PerfilPet> perfilPet) {
        this.ID = ID;
        this.url = url;
        Posts = posts;
        PetPerdido = petPerdido;
        PerfilPet = perfilPet;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "ID=" + ID +
                ", url='" + url + '\'' +
                ", Posts=" + Posts +
                ", PetPerdido=" + PetPerdido +
                ", PerfilPet=" + PerfilPet +
                '}';
    }
}
