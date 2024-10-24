package com.mobile.peticos.Perfil;


import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Perdidos.PetPerdido;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIPerfil {

    @GET("/api/posts/findbyuserid/{id}")
    Call<List<FeedPet>> getPostByid(@Path("id") String id);

    @GET("/api/rescuedlost/findbyid/{id}")
    Call<List<PetPerdido>> getRescuedLostByid(@Path("id") Integer id);
}
