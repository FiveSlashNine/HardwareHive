package com.example.hardwarehive.API;

import com.example.hardwarehive.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("users/{userId}")
    Call<User> getUserById(@Path("userId") String userId);

    @POST("users")
    Call<Void> createUser(@Body User user);

    @PUT("users")
    Call<Void> updateUser(@Body User user);

    @DELETE("users")
    Call<Void> deleteUser(@Body User user);
}
