package com.mobile.peticos.Cadastros.APIs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIPerfil {
    @GET("/getall")
    Call<List<ModelPerfil>> getAll();

    @GET("/getbyusername/{username}")
    Call<ModelPerfil> getById(@Path("username") String username);

    @POST("/api/user/insert")
    Call<ModelPerfil> insert(@Body ModelPerfil model);

    @PUT("/api/user/update/{id")
    Call<ModelPerfil> update(@Path("id") int id, @Body ModelPerfil model);

    @DELETE("/api/user/delete/{id}")
    Call<ModelPerfil> delete(@Path("id") int id);







}
