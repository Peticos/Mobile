package com.mobile.peticos.Perdidos;

public class PetPerdido {
//    {
//  "idPet": 0,
//  "idUser": 0,
//  "bairro": "string",
//  "title": "string",
//  "description": "string",
//  "postTime": "2024-10-19T21:26:22.091Z",
//  "picture": "string",
//  "street": "string",
//  "streetNum": 0,
//  "lostDate": "2024-10-19"
//}

    private int idPet;
    private int idUser;
    private String bairro;
    private String title;
    private String description;
    private String postTime;
    private String picture;
    private String street;
    private int streetNum;
    private String lostDate;



    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }


    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLostDate() {
        return lostDate;
    }

    public void setLostDate(String lostDate) {
        this.lostDate = lostDate;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public PetPerdido(int idPet, int idUser, String bairro, String title, String description, String postTime, String picture, String street, int streetNum, String lostDate) {
        this.idPet = idPet;
        this.idUser = idUser;
        this.bairro = bairro;
        this.title = title;
        this.description = description;
        this.postTime = postTime;
        this.picture = picture;
        this.street = street;
        this.streetNum = streetNum;
        this.lostDate = lostDate;
    }

}
