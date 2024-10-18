package com.mobile.peticos.Cadastros.APIs;

import com.google.gson.annotations.SerializedName;

public class ModelPerfilAuth {
//    {
//  "email": "string",
//  "senha": "string",
//  "id": "string"
//}

    @SerializedName("email")
    public String email;
    @SerializedName("senha")
    public String senha;
    @SerializedName("id")
    public String id;

    public ModelPerfilAuth(String email, String senha) {
        this.email = email;
        this.senha = senha;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
