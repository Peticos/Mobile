package com.mobile.peticos.Perdidos;

import com.mobile.peticos.Padrao.ModelRetorno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiPerdidos {
    @GET("/api/rescuedlost/getall")
    Call<List<PetPerdido>> getPerdidos();

    @POST("/api/rescuedlost/insert")
    Call<ModelRetorno> isertPerdido(@Body PetPerdido perdido);
    @PUT("/api/rescuedlost/findpet/{id}")
    Call<ModelRetorno> acharPet(@Path("id") Integer id, @Query("Data") String data);

}
