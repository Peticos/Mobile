package com.mobile.peticos.Perfil.Pet.API;

public class Cor {
//    [
//    {
//        "id_color": 0,
//            "color": "string"
//    }
//]

    public int id_color;
    public String color;

    public int getId_color() {
        return id_color;
    }

    public void setId_color(int id_color) {
        this.id_color = id_color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Cor(int id_color, String color) {
        this.id_color = id_color;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Cor{" +
                "id_color=" + id_color +
                ", color='" + color + '\'' +
                '}';
    }
}
