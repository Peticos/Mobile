package com.mobile.peticos.Perdidos;

public class PetPerdido {
//    {

//
//  "idPet": 0,
//  "idUser": 0,
//  "bairro": "string",
//  "title": "string",
//  "description": "string",
//  "postTime": "2024-10-24T01:15:48.851Z",
//  "picture": "string",
//  "location": "string",
//  "lostDate": "2024-10-24",
//  "phone": "string",
//  "rescuedDate": "2024-10-24"
//}

    int idPet;
    int idUser;
    String bairro;
    String title;
    String description;
    String postTime;
    String picture;
    String location;
    String lostDate;
    String phone;
    String rescuedDate;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLostDate() {
        return lostDate;
    }

    public void setLostDate(String lostDate) {
        this.lostDate = lostDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRescuedDate() {
        return rescuedDate;
    }

    public void setRescuedDate(String rescuedDate) {
        this.rescuedDate = rescuedDate;
    }

    public PetPerdido(int idPet, int idUser, String bairro, String title, String description, String postTime, String picture, String location, String lostDate, String phone, String rescuedDate) {
        this.idPet = idPet;
        this.idUser = idUser;
        this.bairro = bairro;
        this.title = title;
        this.description = description;
        this.postTime = postTime;
        this.picture = picture;
        this.location = location;
        this.lostDate = lostDate;
        this.phone = phone;
        this.rescuedDate = rescuedDate;
    }
    public PetPerdido(int idPet, int idUser, String bairro, String title, String description, String postTime, String picture, String location, String lostDate) {
        this.idPet = idPet;
        this.idUser = idUser;
        this.bairro = bairro;
        this.title = title;
        this.description = description;
        this.postTime = postTime;
        this.picture = picture;
        this.location = location;
        this.lostDate = lostDate;

    }
    //
}

