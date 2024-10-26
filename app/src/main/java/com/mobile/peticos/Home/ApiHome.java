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
import retrofit2.http.Query;

public interface ApiHome {

    //mudar para alternado
    @GET("/api/posts/all")
    Call<List<FeedPet>> getAll();

    @GET("/api/dayhint/random")
    Call <List<DicasDoDia>> getDayHint();

    @PUT("/api/posts/{id}/like")
    Call<String> like(@Path("id") String id, @Query("username")String username);

    @PUT("/api/posts/{id}/dislike")
    Call<String> dislike(@Path("id") String id, @Query("username")String username);

    @PUT("/api/posts/{id}/share")
    Call<String> share(@Path("id") String id, @Query("username")String username);

    @POST("/api/posts/insert")
    Call<FeedPet> insert(@Body FeedPet feedPet);

    @GET("/api/petregister/nicknames")
    Call<List<String>> getPetNicknames( @Query("ids") List<Integer> ids);




}
