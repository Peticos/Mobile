package com.mobile.peticos.Home;

import java.util.List;

public class FeedPet {
//    {
//  "userId": "102",
//  "likes": 120,
//  "shares": 10,
//  "picture": "https://example.com/image1.jpg",
//  "caption": "Um ótimo dia no parque com meus pets!",
//  "pets": [
//    "104",
//    "120"
//  ],
//  "postDate": "2024-10-16T12:34:56Z",
//  "is_mei": false
//}
    private int userId;
    private int likes;
    private int shares;
    private String picture;
    private String caption;
    private List<Integer> pets;
    private String postDate;
    private boolean is_mei;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<Integer> getPets() {
        return pets;
    }

    public void setPets(List<Integer> pets) {
        this.pets = pets;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public boolean isIs_mei() {
        return is_mei;
    }

    public void setIs_mei(boolean is_mei) {
        this.is_mei = is_mei;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    private double price;
    private String telephone;
    private String productName;


    public FeedPet(int userId, int likes, int shares, String picture, String caption, List<Integer> pets, String postDate, boolean isMei) {
        this.userId = userId;
        this.likes = likes;
        this.shares = shares;
        this.picture = picture;
        this.caption = caption;
        this.pets = pets;
        this.postDate = postDate;
        this.is_mei = isMei;
    }

    public FeedPet(int userId, int likes, int shares, String picture, String caption, String postDate, boolean isMei, double price, String telephone, String productName) {
        this.userId = userId;
        this.likes = likes;
        this.shares = shares;
        this.picture = picture;
        this.caption = caption;
        this.postDate = postDate;
        this.is_mei = isMei;
        this.price = price;
        this.telephone = telephone;
        this.productName = productName;
    }


}

