package com.mobile.peticos.Cadastros.ModelsFirebase;

public class PetPerdido {
    private int IDUsuario;
    private int IDpet;
    private int ID;
    private String url;

    public int getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public int getIDpet() {
        return IDpet;
    }

    public void setIDpet(int IDpet) {
        this.IDpet = IDpet;
    }

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

    public PetPerdido(int IDUsuario, int IDpet, int ID, String url) {
        this.IDUsuario = IDUsuario;
        this.IDpet = IDpet;
        this.ID = ID;
        this.url = url;
    }

    @Override
    public String toString() {
        return "PetPerdido{" +
                "IDUsuario=" + IDUsuario +
                ", IDpet=" + IDpet +
                ", ID=" + ID +
                ", url='" + url + '\'' +
                '}';
    }

}
