package com.mobile.peticos.Perfil.Pet.API;

public class ModelPeso {
//    {
//        "idWeight": 0,
//            "idPet": 0,
//            "weight": 0,
//            "weightDate": "2024-10-28T00:03:46.752Z"
//    }

    private int idWeight;
    private int idPet;
    private double weight;
    private String weightDate;

    public ModelPeso( int idPet, double weight, String weightDate) {

        this.idPet = idPet;
        this.weight = weight;
        this.weightDate = weightDate;
    }

    public int getIdWeight() {
        return idWeight;
    }

    public void setIdWeight(int idWeight) {
        this.idWeight = idWeight;
    }

    public int getIdPet() {
        return idPet;
    }

    public void setIdPet(int idPet) {
        this.idPet = idPet;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getWeightDate() {
        return weightDate;
    }

    public void setWeightDate(String weightDate) {
        this.weightDate = weightDate;
    }
}
