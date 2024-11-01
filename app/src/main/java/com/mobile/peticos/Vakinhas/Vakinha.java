package com.mobile.peticos.Vakinhas;

public class Vakinha {
//     "idVakinha": 3,
//    "idPet": 146,
//    "idUser": 281,
//    "title": " AJUDEM A MIMI A OPERAR ",
//    "link": "https://www.vakinha.com.br/",
//    "totalDonated": 143.7,
//    "goalAmount": 500,
//    "supportersAmount": 3,
//    "totalPercentage": 25,
//    "description": "Ajude na cirurgia da Gatinha MIMI",
//    "image": "http://link-imagem-gatinho",
//    "initialDate": "2024-10-29T15:02:52.631Z",
//    "endDate": "2024-10-29T15:02:52.631Z"


    public int idVakinha;
    public int idPet;
    public int idUser;
    public String title;
    public String link;
    public double totalDonated;
    public double goalAmount;
    public int supportersAmount;
    public int totalPercentage;
    public String description;
    public String image;
    public String initialDate;
    public String endDate;

    //model retorno


    public Vakinha(int idVakinha, int idPet, int idUser, String title, String link, double totalDonated, double goalAmount, int supportersAmount, int totalPercentage, String description, String image, String initialDate, String endDate) {
        this.idVakinha = idVakinha;
        this.idPet = idPet;
        this.idUser = idUser;
        this.title = title;
        this.link = link;
        this.totalDonated = totalDonated;
        this.goalAmount = goalAmount;
        this.supportersAmount = supportersAmount;
        this.totalPercentage = totalPercentage;
        this.description = description;
        this.image = image;
        this.initialDate = initialDate;
        this.endDate = endDate;
    }
    //modelInserir


    public Vakinha(int idPet, int idUser, String link) {
        this.idPet = idPet;
        this.idUser = idUser;
        this.link = link;
    }

    public int getIdVakinha() {
        return idVakinha;
    }

    public void setIdVakinha(int idVakinha) {
        this.idVakinha = idVakinha;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getTotalDonated() {
        return totalDonated;
    }

    public void setTotalDonated(double totalDonated) {
        this.totalDonated = totalDonated;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public int getSupportersAmount() {
        return supportersAmount;
    }

    public void setSupportersAmount(int supportersAmount) {
        this.supportersAmount = supportersAmount;
    }

    public int getTotalPercentage() {
        return totalPercentage;
    }

    public void setTotalPercentage(int totalPercentage) {
        this.totalPercentage = totalPercentage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
