package com.mobile.peticos.Perfil.Pet.Vacinas;

public class ModelDose {
    //{
    //  "idDose": 0,
    //  "idVaccine": 0,
    //  "dateDose": "2024-10-27T19:42:52.914Z",
    //  "dose": 0
    //}

    private int idDose;
    private int idVaccine;
    private String dateDose;
    private int dose;

    // Construtor
    public ModelDose( int idVaccine, String dateDose, int dose) {
        this.idVaccine = idVaccine;
        this.dateDose = dateDose;
        this.dose = dose;
    }

    public int getIdDose() {
        return idDose;
    }

    public void setIdDose(int idDose) {
        this.idDose = idDose;
    }

    public int getIdVaccine() {
        return idVaccine;
    }

    public void setIdVaccine(int idVaccine) {
        this.idVaccine = idVaccine;
    }

    public String getDateDose() {
        return dateDose;
    }

    public void setDateDose(String dateDose) {
        this.dateDose = dateDose;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }
}
