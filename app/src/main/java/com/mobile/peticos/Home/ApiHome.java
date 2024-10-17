package com.mobile.peticos.Home;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiHome {

    @GET("/api/posts/alternado")
    Call<List<FeedPet>> getAll();

    
}
