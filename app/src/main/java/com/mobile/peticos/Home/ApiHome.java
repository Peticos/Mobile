package com.mobile.peticos.Home;

import com.mobile.peticos.Home.Feed.FeedPet;
import com.mobile.peticos.Home.HomeDica.DicasDoDia;
import com.mobile.peticos.Padrao.ModelRetorno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiHome {

    //mudar para alternado
    @GET("/api/posts/all")
    Call<List<FeedPet>> getAll();

    @GET("/api/dayhint/random")
    Call <List<DicasDoDia>> getDayHint();

    @PUT("/api/posts/{id}/like")
    Call<ModelRetorno> like(@Path("id") int id, @Body FeedPet feedPet);

    @PUT("/api/posts/{id}/dislike")
    Call<FeedPet> dislike(@Path("id") int id);

    @POST("/api/posts/insert")
    Call<FeedPet> insert(@Body FeedPet feedPet);



}
