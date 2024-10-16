package com.mobile.peticos.Perfil.Pet.Apis;

import com.mobile.peticos.ModelRetorno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIPets {

   @GET("/api/haircolor/getall")
   Call<List<Cor>> getAllColors();

   @GET("/api/race/getall")
   Call<List<Raca>> getAllRaces();

   @POST("/api/petregister/insert")
   Call<ModelRetorno> insertPet(@Body ModelPetBanco pet);

   @GET("/api/petregister/getbyusername/{username}")
   Call<List<ModelPetBanco>> getPets(@Path("username") String username);

   @POST("/personalizations/insert")
   Call<ModelRetorno> personalizarPet(@Path ("fotoPet") Personalizacao pet);




}
