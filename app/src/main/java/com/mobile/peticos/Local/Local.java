package com.mobile.peticos.Local;

public class Local {
    private String nome;
    private String descricao;
    private int imagem;

    public Local(String nome, String descricao, int imagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getImagem() {
        return imagem;
    }
}

