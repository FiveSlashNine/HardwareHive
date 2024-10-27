package com.example.hardwarehive.API;

import com.example.hardwarehive.User.ShoppingCart.HardwareOrder;
import com.example.hardwarehive.User.ShoppingCart.ShoppingCart;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShoppingCartService {
    @GET("shopping_cart/{userId}")
    Call<ShoppingCart> getCart(@Path("userId") String userId);

    @POST("shopping_cart/{userId}/add")
    Call<Void> addItemToCart(@Path("userId") String userId, @Body HardwareOrder order);

    @POST("shopping_cart/{userId}/remove")
    Call<Void> removeItemFromCart(@Path("userId") String userId, @Body HardwareOrder order);

    @POST("shopping_cart/{userId}/purchase")
    Call<String> purchaseCart(@Path("userId") String userId);

    @PUT("shopping_cart/{userId}/update")
    Call<Void> updateItemFromCart(@Path("userId") String userId, @Body HardwareOrder order);
}
