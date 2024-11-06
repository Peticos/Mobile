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

    //postDate -> post_date
    //isMei -> is_mei
    //userId -> user_id
    //productName -> product_name

    public String _id;
    public int user_id;
    public List<String> likes;
    public List<String> shares;
    public String picture;
    public String caption;
    public List<Integer> pets;
    public String post_date;
    public boolean is_mei;
    public double price;
    public String telephone;
    public String product_name;


    public FeedPet(int userId,List<String> likes, List<String> shares, String picture, String caption, List<Integer> pets, String postDate, boolean isMei) {
        this.user_id = userId;
        this.likes = likes;
        this.shares = shares;
        this.picture = picture;
        this.caption = caption;
        this.pets = pets;
        this.post_date = postDate;
        this.is_mei = isMei;
    }


    public FeedPet(int userId,List<String> likes, List<String> shares, String picture, String caption, String postDate, boolean isMei, double price, String telephone, String productName) {
        this.user_id = userId;
        this.likes = likes;
        this.shares = shares;
        this.picture = picture;
        this.caption = caption;
        this.post_date = postDate;
        this.is_mei = isMei;
        this.price = price;
        this.telephone = telephone;
        this.product_name = productName;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int userId) {
        this.user_id = userId;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<String> getShares() {
        return shares;
    }

    public void setShares(List<String> shares) {
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
        return post_date;
    }

    public void setPostDate(String postDate) {
        this.post_date = postDate;
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
        return product_name;
    }

    public void setProductName(String productName) {
        this.product_name = productName;
    }

    public FeedPet(String id, int userId, List<String> likes, List<String> shares, String picture, String caption, List<Integer> pets, String postDate, boolean is_mei, double price, String telephone, String productName) {
        this._id = id;
        this.user_id = userId;
        this.likes = likes;
        this.shares = shares;
        this.picture = picture;
        this.caption = caption;
        this.pets = pets;
        this.post_date = postDate;
        this.is_mei = is_mei;
        this.price = price;
        this.telephone = telephone;
        this.product_name = productName;
    }
}

