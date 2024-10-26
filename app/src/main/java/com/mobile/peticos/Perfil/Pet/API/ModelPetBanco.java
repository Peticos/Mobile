package com.mobile.peticos.Perfil.Pet.API;

import com.google.gson.annotations.SerializedName;

public class ModelPetBanco {
    //{
    //  "idUser": 0,
    //  "nickname": "string",
    //  "age": 0,
    //  "sex": "string",
    //  "specie": "string",
    //  "race": "string",
    //  "size": "string",
    //  "color": "string",
    //  "user": "string"
    //}



    @SerializedName("idPet")
    private int idPet;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("age")
    private int age;
    @SerializedName("sex")
    private String sex;
    @SerializedName("specie")
    private String specie;
    @SerializedName("race")
    private String race;
    @SerializedName("size")
    private String size;
    @SerializedName("color")
    private String colorpet;

    @SerializedName("user")
    private String user;

    public ModelPetBanco( String nickname, int age, String sex, String specie, String race, String size, String colorpet, String user) {

        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
        this.specie = specie;
        this.race = race;
        this.size = size;
        this.colorpet = colorpet;
        this.user = user;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColorpet() {
        return colorpet;
    }

    public void setColorpet(String colorpet) {
        this.colorpet = colorpet;
    }
}
