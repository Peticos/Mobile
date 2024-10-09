package com.mobile.peticos.Perfil.Pet.Apis;

import com.google.gson.annotations.SerializedName;

public class ModelPet {
    //{
    //  "idPet": 0,
    //  "idUser": 0,
    //  "nickname": "string",
    //  "age": 0,
    //  "sex": "string",
    //  "description": "string",
    //  "specie": "string",
    //  "race": "string",
    //  "size": "string",
    //  "color": "string"
    //}


    private int idUser;
    private String nickname;
    private int age;
    private String sex;
    private String description;
    private String specie;
    private String race;
    private String size;
    private String color;



    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public ModelPet( int idUser, String nickname, int age, String sex, String description, String specie, String race, String size, String color) {

        this.idUser = idUser;
        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
        this.description = description;
        this.specie = specie;
        this.race = race;
        this.size = size;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ModelPet{" +
                "idUser=" + idUser +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", description='" + description + '\'' +
                ", specie='" + specie + '\'' +
                ", race='" + race + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
