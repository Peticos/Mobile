package com.mobile.peticos.Perfil.Pet.API;

import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.Cor;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.Perfil.Pet.API.Raca;

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
   Call<ModelRetorno> personalizarPet(@Body Personalizacao personalizacao);

   @GET("/personalizations/getbyid/{id}")
   Call<Personalizacao> getPersonalizacao(@Path("id") int id);

   @POST("/personalizations/update/{id}")
   Call<ModelRetorno> updatePersonalizacao(@Path("id") int id, @Body Personalizacao personalizacao);





}