package com.mobile.peticos.AreaRestrita;

import com.google.gson.annotations.SerializedName;

public class ModelLoginRestrita {
//    {
//        "idAdmin": 0,
//            "email": "string",
//            "password": "string",
//            "name": "string"
//    }

    @SerializedName("idAdmin")
    public int idAdmin;
    @SerializedName("email")
    public String email;
    @SerializedName("password")
    public String password;
    @SerializedName("name")
    public String name;

    public ModelLoginRestrita(String email, String password) {

        this.email = email;
        this.password = password;

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
