package com.example.hardwarehive.API;

import com.example.hardwarehive.model.Hardware;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HardwareService {
    @GET("hardware/{type}")
    Call<List<Hardware>> findAll(@Path("type") String type);

    @GET("hardware/{type}/default")
    Call<Hardware> createDefault(@Path("type") String type);

    @PUT("hardware/{type}")
    Call<Void> update(@Path("type") String type, @Body Hardware hardware);

    @DELETE("hardware/{type}/{id}")
    Call<Void> delete(@Path("id") long id, @Path("type") String type);

    @GET("hardware/search")
    Call<List<Hardware>> findSimilar(@Query("name") String name);
}
