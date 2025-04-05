package com.example.hardwarehive.API;

import com.example.hardwarehive.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/userEmail/{userEmail}")
    Call<User> getUserByEmail(@Path("userEmail") String userEmail);

    @POST("users")
    Call<User> createUser(@Body User user);

    @PUT("users")
    Call<User> updateUser(@Body User user);
}
