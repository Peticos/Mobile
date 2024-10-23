package com.mobile.peticos.Perdidos;

import com.mobile.peticos.Padrao.ModelRetorno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiPerdidos {
    @GET("/api/rescuedlost/getall")
    Call<List<PetPerdido>> getPerdidos();

    @POST("/api/rescuedlost/insert")
    Call<ModelRetorno> isertPerdido(@Body PetPerdido perdido);

}
