package com.mobile.peticos.Perfil.Pet.API;

import com.google.gson.annotations.SerializedName;

public class ModelPetBanco {
    //{
    //  "user":"anabiaaaacruz",
    //  "nickname": "Bolt",
    //  "age": 3,
    //  "sex": "M",
    //  "specie": "Cachorro",
    //  "race": "Shih Tzu",
    //  "size": "Pequeno",
    //  "color": "Branco e Marrom"
    //}


    @SerializedName("user")
    public String user;
    @SerializedName("nickname")
    public String nickname;
    @SerializedName("age")
    public int age;
    @SerializedName("sex")
    public String sex;
    @SerializedName("specie")
    public String specie;
    @SerializedName("race")
    public String race;
    @SerializedName("size")
    public String size;
    @SerializedName("color")
    public String colorpet;

    @SerializedName("idPet")
    public int idPet;


    public ModelPetBanco(String user, String nickname, int age, String sex, String specie, String race, String size, String colorpet) {
        this.user = user;
        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
        this.specie = specie;
        this.race = race;
        this.size = size;
        this.colorpet = colorpet;
    }
    public ModelPetBanco(String user, String nickname, int age, String sex, String specie, String race, String size, String colorpet, int idPet) {
        this.user = user;
        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
        this.specie = specie;
        this.race = race;
        this.size = size;
        this.colorpet = colorpet;
        this.idPet = idPet;
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

    @Override
    public String toString() {
        return "ModelPet{" +
                "user='" + user + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", specie='" + specie + '\'' +
                ", race='" + race + '\'' +
                ", size='" + size + '\'' +
                ", colorpet='" + colorpet + '\'' +
                '}';
    }
}
