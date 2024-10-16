package com.mobile.peticos.Cadastros.APIs;

import com.mobile.peticos.ModelRetorno;

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
<<<<<<< HEAD
=======
    Call<ModelPerfil> getByUsername(@Path("username") String username);

    @GET("/getbyid/{id}")
    Call<ModelPerfil> getById(@Path("id") int id);
    @GET("/api/user/getbyusername/{username}")
>>>>>>> 51ba98e06cd7792c2f601d63dd2fc28d90e7fd8d
    Call<ModelPerfil> getById(@Path("username") String username);

    @Headers("Content-Type: application/json")
    @POST("/api/user/inserttutor")
    Call<ModelRetorno> insertTutor(@Body ModelPerfil model);
<<<<<<< HEAD
    @Headers("Content-Type: application/json")
    @POST("/api/user/insertprofissional")
    Call<ModelRetorno> insertProfissional(@Body ModelPerfil model);
=======
>>>>>>> 51ba98e06cd7792c2f601d63dd2fc28d90e7fd8d

    @Headers("Content-Type: application/json")
    @POST("/api/user/insertprofissional")
    Call<ModelRetorno> insertProfissional(@Body ModelPerfil model);

    @PUT("/api/user/update/{id}")
    Call<ModelPerfil> update(@Path("id") int id, @Body ModelPerfil model);

    @DELETE("/api/user/delete/{id}")
    Call<ModelPerfil> delete(@Path("id") int id);
}
