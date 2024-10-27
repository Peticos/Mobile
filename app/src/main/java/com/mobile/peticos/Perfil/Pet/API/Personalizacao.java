package com.mobile.peticos.Perfil.Pet.API;

public class Personalizacao {
    private int id;
    private String species;
    private int hatId;
    private int hairId;
    private int toyId;
    private int glassesId;

    public Personalizacao(int id, String species, int hatId, int hairId, int toyId, int glassesId) {
        this.id = id;
        this.species = species;
        this.hatId = hatId; //chapeu
        this.hairId = hairId; //cor
        this.toyId = toyId;
        this.glassesId = glassesId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getHatId() {
        return hatId;
    }

    public void setHatId(int hatId) {
        this.hatId = hatId;
    }

    public int getHairId() {
        return hairId;
    }

    public void setHairId(int hairId) {
        this.hairId = hairId;
    }

    public int getToyId() {
        return toyId;
    }

    public void setToyId(int toyId) {
        this.toyId = toyId;
    }

    public int getGlassesId() {
        return glassesId;
    }

    public void setGlassesId(int glassesId) {
        this.glassesId = glassesId;
    }
}
