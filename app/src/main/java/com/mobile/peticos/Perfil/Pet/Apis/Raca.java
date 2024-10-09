package com.mobile.peticos.Perfil.Pet.Apis;

public class Raca {
    //[
    //  {
    //    "id_race": 0,
    //    "race": "string"
    //  }
    //]

    public int id_race;
    public String race;

    public int getId_race() {
        return id_race;
    }

    public void setId_race(int id_race) {
        this.id_race = id_race;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Raca(int id_race, String race) {
        this.id_race = id_race;
        this.race = race;
    }

    @Override
    public String toString() {
        return "Raca{" +
                "id_race=" + id_race +
                ", race='" + race + '\'' +
                '}';
    }
}
