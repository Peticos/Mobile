package com.mobile.peticos.Cadastros.ModelsFirebase;

public class Posts {
    private int IDUsuario;
    private int IDPost;
    private String url;

    public int getIDUsuario() {
        return IDUsuario;
    }

    public void setIDUsuario(int IDUsuario) {
        this.IDUsuario = IDUsuario;
    }

    public int getIDPost() {
        return IDPost;
    }

    public void setIDPost(int IDPost) {
        this.IDPost = IDPost;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Posts(int IDUsuario, int IDPost, String url) {
        this.IDUsuario = IDUsuario;
        this.IDPost = IDPost;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "IDUsuario=" + IDUsuario +
                ", IDPost=" + IDPost +
                ", url='" + url + '\'' +
                '}';
    }
}
