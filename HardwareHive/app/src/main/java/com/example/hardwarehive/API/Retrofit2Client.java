package com.example.hardwarehive.API;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;


public class Retrofit2Client {
    public static final String BASE_URL = "http://10.0.2.2:8080/api/v1/";
    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create());

    private static final Retrofit retrofit = builder.build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
