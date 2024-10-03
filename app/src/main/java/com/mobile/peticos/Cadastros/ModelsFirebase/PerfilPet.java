package com.mobile.peticos.Cadastros.ModelsFirebase;

public class PerfilPet {
    private int IDUsuario;
    private int IDpet;
    private String acessoriourl;
    private String corpourl;
    private String brinquedourl;
    private String oculosurl;

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

    public String getAcessoriourl() {
        return acessoriourl;
    }

    public void setAcessoriourl(String acessoriourl) {
        this.acessoriourl = acessoriourl;
    }

    public String getCorpourl() {
        return corpourl;
    }

    public void setCorpourl(String corpourl) {
        this.corpourl = corpourl;
    }

    public String getBrinquedourl() {
        return brinquedourl;
    }

    public void setBrinquedourl(String brinquedourl) {
        this.brinquedourl = brinquedourl;
    }

    public String getOculosurl() {
        return oculosurl;
    }

    public void setOculosurl(String oculosurl) {
        this.oculosurl = oculosurl;
    }

    public PerfilPet(int IDUsuario, int IDpet, String acessoriourl, String corpourl, String brinquedourl, String oculosurl) {
        this.IDUsuario = IDUsuario;
        this.IDpet = IDpet;
        this.acessoriourl = acessoriourl;
        this.corpourl = corpourl;
        this.brinquedourl = brinquedourl;
        this.oculosurl = oculosurl;
    }

    @Override
    public String toString() {
        return "PerfilPet{" +
                "IDUsuario=" + IDUsuario +
                ", IDpet=" + IDpet +
                ", acessoriourl='" + acessoriourl + '\'' +
                ", corpourl='" + corpourl + '\'' +
                ", brinquedourl='" + brinquedourl + '\'' +
                ", oculosurl='" + oculosurl + '\'' +
                '}';
    }
}
