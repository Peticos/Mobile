package com.mobile.peticos.Home;

import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.HomeDica.DicasDoDia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiHome {

    @GET("/api/posts/alternado")
    Call<List<FeedPet>> getAll();

    @GET("/api/dayhint/random")
    Call <List<DicasDoDia>> getDayHint();

    
}
