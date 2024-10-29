package com.mobile.peticos.Vakinhas;


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

public interface VakinhAPI {

    @POST("/api/vakinha/insert")
    Call<ModelRetorno> insertVakinha(@Body Vakinha vakinha);

    @GET("/api/vakinha/getall")
    Call<List<Vakinha>> getAll();
    @GET("/api/vakinha/getByUser/{id}")
    Call<List<Vakinha>> getByID(@Path("id") int id);
}
