package com.mobile.peticos.Cadastros.APIs;

import com.mobile.peticos.Padrao.ModelRetorno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIPerfil {
    @GET("/getall")
    Call<List<ModelPerfil>> getAll();

    @GET("/api/user/getbyusername/{username}")
    Call<ModelPerfil> getByUsername(@Path("username") String username);

    @GET("/api/user/getbyusername/{username}")
    Call<ModelPerfil> getById(@Path("username") int id);

    @Headers("Content-Type: application/json")
    @POST("/api/user/inserttutor")
    Call<Integer> insertTutor(@Body ModelPerfil model);

    @POST("/api/user/insertprofissional")
    Call<Integer> insertProfissional(@Body ModelPerfil model);

    @PUT("/api/user/updateuser")
    Call<ModelRetorno> update(@Body ModelPerfil model);
    @PUT("/api/user/updateprofissional")
    Call<ModelRetorno> updateProfissional(@Body ModelPerfil model);

    @DELETE("/api/user/delete/{id}")
    Call<ModelPerfil> delete(@Path("id") int id);

    @GET("/api/user/findbyid/{id}")
    Call<ModelPerfil> findById(@Path("id") int id);

    @POST("/api/auth/register")
    Call<ModelRetorno> register(@Body ModelPerfilAuth model);

    @POST("/api/auth/login")
    Call<Integer> login(@Body ModelPerfilAuth model);







}