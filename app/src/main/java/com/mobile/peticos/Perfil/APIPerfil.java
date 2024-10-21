package com.mobile.peticos.Perfil;

import com.mobile.peticos.Home.FeedPet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIPerfil {
    @GET("/api/posts/findbyuserid/{id}")
    Call<List<FeedPet>> getPostByid();
}
