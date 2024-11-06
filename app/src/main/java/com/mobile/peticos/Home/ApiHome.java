package com.mobile.peticos.Home;

import com.mobile.peticos.Cadastros.Bairros.ModelBairro;
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
    @GET("/feed/newfeed/{user_id}")
    Call<List<FeedPet>> getAll( @Path("user_id") String user_id);

    @GET("/feed/newfeed/{user_id}")
    Call<List<FeedPet>> getNewFeed( @Path("user_id") String user_id);

    @GET("/api/dayhint/random")
    Call <List<DicasDoDia>> getDayHint();

    @PUT("/like/{post_id}/{username}")
    Call<String> like(@Path("id") String id, @Path("username")String username);

    @PUT("/dislike/{post_id}/{username}")
    Call<String> dislike(@Path("id") String id, @Path("username")String username);

    @PUT("/api/posts/{id}/share")
    Call<String> share(@Path("id") String id, @Query("username")String username);

    @POST("/api/posts/insert")
    Call<FeedPet> insert(@Body FeedPet feedPet);

    @GET("/api/petregister/nicknames")
    Call<List<String>> getPetNicknames( @Query("ids") List<Integer> ids);

    @GET("/dayhint/check")
    Call<ModelRetorno> checkDayHint();
    @GET("/dayhint")
    Call<List<DicasDoDia>> getDayHintRedis();
    @POST("/dayhint/insert")
    Call<ModelRetorno> insertDayHint(@Body List<DicasDoDia> dicasDoDia);


}
