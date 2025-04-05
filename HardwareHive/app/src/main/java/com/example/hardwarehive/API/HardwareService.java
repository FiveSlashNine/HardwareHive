package com.example.hardwarehive.API;

import com.example.hardwarehive.model.Hardware;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HardwareService {
    @GET("hardware")
    Call<List<Hardware>> getHardware();

    @POST("hardware")
    Call<Hardware> createHardware(@Body Hardware hardware);

    @PUT("hardware")
    Call<Hardware> update(@Body Hardware hardware);

    @DELETE("hardware/{id}")
    Call<Void> delete(@Path("id") long id);

    @GET("hardware/search")
    Call<List<Hardware>> getSimilarHardware(@Query("name") String name);

    @GET("hardware/getHardwareByCategory")
    Call<List<Hardware>> getHardwareByCategory(@Query("category") String category);

}
