package com.mobile.peticos.Local;

public class Local {
//    {
//        "idLocal": 0,
//            "idLocalType": 0,
//            "localName": "string",
//            "localPicture": "string",
//            "street": "string",
//            "streetNum": 0,
//            "description": "string",
//            "linkKnowMore": "string"
//    "street": "Av. dos Livros",
//            "city": "São Paulo",
//            "neighborhood": "Água Fria",
//            "streetNum": 234,
//    }

    private int idLocal;
    private int idLocalType;
    private String localName;
    private String localPicture;
    private String street;

    private int streetNum;
    private String city;
    private String neighborhood;
    private String description;
    private String linkKnowMore;
    private String phone;

    public Local(int idLocal, int idLocalType, String localName, String localPicture, String street, int streetNum, String description, String linkKnowMore, String phone, String neighborhood,String city) {
        this.idLocal = idLocal;
        this.idLocalType = idLocalType;
        this.localName = localName;
        this.localPicture = localPicture;
        this.street = street;
        this.streetNum = streetNum;
        this.description = description;
        this.linkKnowMore = linkKnowMore;
        this.phone = phone;
        this.neighborhood = neighborhood;
        this.city = city;
    }

    public Local(String description, String localPicture, String linkKnowMore, String localName,int streetNum, String street, String phone, String neighborhood,String city) {
        this.description = description;
        this.localPicture = localPicture;
        this.linkKnowMore = linkKnowMore;
        this.localName = localName;
        this.street = street;
        this.streetNum = streetNum;
        this.phone = phone;
        this.neighborhood = neighborhood;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdLocalType() {
        return idLocalType;
    }

    public void setIdLocalType(int idLocalType) {
        this.idLocalType = idLocalType;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalPicture() {
        return localPicture;
    }

    public void setLocalPicture(String localPicture) {
        this.localPicture = localPicture;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinkKnowMore() {
        return linkKnowMore;
    }

    public void setLinkKnowMore(String linkKnowMore) {
        this.linkKnowMore = linkKnowMore;
    }



}