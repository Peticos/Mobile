package com.mobile.peticos.Local;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiLocais {

    @GET ("/api/locations/getall")
    Call<List<Local>> getAll();

    @GET ("/api/locations/getbytype/{id}")
    Call<List<Local>> getByType(@Path("id") int id);

}
