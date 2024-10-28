package com.mobile.peticos.Perfil.Pet.API;

import com.mobile.peticos.Padrao.ModelRetorno;
import com.mobile.peticos.Perfil.Pet.API.Cor;
import com.mobile.peticos.Perfil.Pet.API.ModelPetBanco;
import com.mobile.peticos.Perfil.Pet.API.Personalizacao;
import com.mobile.peticos.Perfil.Pet.API.Raca;
import com.mobile.peticos.Perfil.Pet.Vacinas.ModelDose;
import com.mobile.peticos.Perfil.Pet.Vacinas.ModelVacina;
import com.mobile.peticos.Perfil.Pet.Vacinas.VacinasPets;

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
   Call<Integer> insertPet(@Body ModelPetBanco pet);

   @GET("/api/petregister/getbyusername/{username}")
   Call<List<ModelPetBanco>> getPets(@Path("username") String username);

   @POST("/personalizations/insert")
   Call<ModelRetorno> personalizarPet(@Body Personalizacao personalizacao);

   @GET("/personalizations/getbyid/{id}")
   Call<Personalizacao> getPersonalizacao(@Path("id") int id);

   @POST("/api/petregister/update")
   Call<ModelRetorno> updatePet(@Body ModelPetBanco modelPetBanco);

   @GET("/api/vaccine/findbyid/{id}")
   Call <List<ModelVacina>> getallVacina(@Path("id") int id);

   @POST("/api/vaccine/insert")
   Call<Integer> insertVacina (@Body ModelVacina vacina);

   @POST("/api/doses/insert")
   Call<ModelRetorno> insertDose (@Body ModelDose dose);

   @GET ("/api/doses/getbyvaccine/{id}")
   Call<List<ModelDose>> getDoses(@Path("id") int id);

   @POST("/api/weight/insert")
   Call<ModelRetorno> insertWeight (@Body ModelPeso peso);








}