package com.mobile.peticos.Cadastros.APIs;

public class ModelPerfil {
//    {
//        "fullName": "Nome Exemplo",
//            "username": "usuarioExemplo",
//            "email": "usuario@gmail.com",
//            "gender": "Masculino",
//            "plan": "Sem Plano",
//            "bairro":"Aclimação",
//            "phone":"01234567890"
//    }

    public String bairro;
    public String fullName;
    public String userName;
    public String email;
    public String gender;
    public String telefone;
    public String plano;
    public String cnpj;

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
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public ModelPerfil(String bairro, String fullName, String userName, String email, String gender, String telefone, String plano) {
        this.bairro = bairro;
        this.fullName = fullName;
        this.userName = userName;
        this.email = email;
        this.gender = gender;
        this.telefone = telefone;
        this.plano = plano;
    }

    @Override
    public String toString() {
        return "ModelPerfil{" +
                "bairro='" + bairro + '\'' +
                ", fullName='" + fullName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", telefone='" + telefone + '\'' +
                ", plano='" + plano + '\'' +
                ", cnpj='" + cnpj + '\'' +
                '}';
    }
}
