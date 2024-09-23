package com.mobile.peticos.Cadastros.Bairros;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIBairro {


    @GET("/api/address/getall")
    Call<List<ModelBairro>> getAll();




}
