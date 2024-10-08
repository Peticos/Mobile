package com.mobile.peticos.Cadastros.APIs;

import com.google.gson.annotations.SerializedName;

public class ModelPerfil {
    //@Param("fullName") String fullName,
    //            @Param("username") String username,
    //            @Param("email") String email,
    //            @Param("bairro") String bairro,
    //            @Param("plan") String plan,
    //            @Param("phone") String phone,
    //            @Param("gender") String gender

    @SerializedName("id")
    public int id;
    @SerializedName("fullName")
    public String fullName;
    @SerializedName("username")
    public String username;
    @SerializedName("email")
    public String email;
    @SerializedName("bairro")
    public String bairro;
    @SerializedName("plan")
    public String plan;
    @SerializedName("phone")
    public String phone;
    @SerializedName("gender")
    public String gender;

    @SerializedName("cnpj")
    public String cnpj;

    public int getId() {
        return id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTelefone() {
        return phone;
    }

    public void setTelefone(String telefone) {
        this.phone = telefone;
    }

    public String getPlano() {
        return plan;
    }

    public void setPlano(String plano) {
        this.plan = plano;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public ModelPerfil(String fullName, String username, String email, String bairro, String plan, String phone, String gender, String cnpj) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.bairro = bairro;
        this.plan = plan;
        this.phone = phone;
        this.gender = gender;
        this.cnpj = cnpj;

    }


    @Override
    public String toString() {
        return "ModelPerfil{" +
                "bairro='" + bairro + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userName='" + username + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", telefone='" + phone + '\'' +
                ", plano='" + plan + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
