package com.mobile.peticos.Perfil.Pet.Apis;

import com.mobile.peticos.ModelRetorno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIPets {

   @GET("/api/haircolor/getall")
   Call<List<Cor>> getAllColors();

   @GET("/api/race/getall")
   Call<List<Raca>> getAllRaces();

   @POST("/api/petregister/insert")
   Call<ModelRetorno> insertPet(@Body ModelPet pet);



}
