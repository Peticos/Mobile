package com.mobile.peticos.Home.Feed;

import java.util.List;

public class FeedPet {
 //{
    //    {
    //    "id": "67126838e1d62b337917a8cb",
    //    "userId": 2,
    //    "likes": [],
    //    "shares": 15,
    //    "picture": "https://firebasestorage.googleapis.com/v0/b/peticos-5c769.appspot.com/o/ImagensLocais%2Fmulheres-negras-1.jpg?alt=media&token=3c69f19b-bb22-4432-b466-f95e2edaaccf",
    //    "caption": "Produto especial para pets! Aproveite o desconto!",
    //    "pets": null,
    //    "postDate": "2024-10-16T14:20:00.000+00:00",
    //    "price": 99.99,
    //    "telephone": "+5511999999999",
    //    "productName": "Brinquedo para cachorro",
    //    "is_mei": false
    //  },
    //},


    public String id;
    public int userId;
    public List<String> likes;
    public int shares;
    public String picture;
    public String caption;
    public List<Integer> pets;
    public String postDate;
    public boolean is_mei;
    public double price;
    public String telephone;
    public String productName;


    public FeedPet(int userId, String picture, String caption, List<Integer> pets, String postDate, boolean isMei) {
        this.userId = userId;
        this.picture = picture;
        this.caption = caption;
        this.pets = pets;
        this.postDate = postDate;
        this.is_mei = isMei;
    }


    public FeedPet(int userId, String picture, String caption, String postDate, boolean isMei, double price, String telephone, String productName) {
        this.userId = userId;
        this.picture = picture;
        this.caption = caption;
        this.postDate = postDate;
        this.is_mei = isMei;
        this.price = price;
        this.telephone = telephone;
        this.productName = productName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
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

    public FeedPet(String id, int userId, List<String> likes, int shares, String picture, String caption, List<Integer> pets, String postDate, boolean is_mei, double price, String telephone, String productName) {
        this.id = id;
        this.userId = userId;
        this.likes = likes;
        this.shares = shares;
        this.picture = picture;
        this.caption = caption;
        this.pets = pets;
        this.postDate = postDate;
        this.is_mei = is_mei;
        this.price = price;
        this.telephone = telephone;
        this.productName = productName;
    }
}

