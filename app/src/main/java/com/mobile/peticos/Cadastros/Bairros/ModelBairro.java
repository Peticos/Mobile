package com.mobile.peticos.Cadastros.Bairros;

public class ModelBairro {
//    {
//        "idAddress": 150,
//            "city": "São Paulo",
//            "state": "São Paulo",
//            "neighborhood": "Aclimação"
//    },

    private int idAddress;
    private String city;
    private String state;
    private String neighborhood;

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public ModelBairro(int idAddress, String city, String state, String neighborhood) {
        this.idAddress = idAddress;
        this.city = city;
        this.state = state;
        this.neighborhood = neighborhood;
    }

    @Override
    public String toString() {
        return "ModelBairro{" +
                "idAddress=" + idAddress +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                '}';
    }
}
