package com.example.hardwarehive.API;

import com.example.hardwarehive.User.ShoppingCart.HardwareOrder;
import com.example.hardwarehive.User.ShoppingCart.ShoppingCart;
import com.example.hardwarehive.model.Hardware;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ShoppingCartService {
    @GET("shopping_cart/{userEmail}")
    Call<ShoppingCart> getCart(@Path("userEmail") String userEmail);

    @GET("shopping_cart/{userEmail}/items")
    Call<List<HardwareOrder>> getCartItems(@Path("userEmail") String userEmail);

    @POST("shopping_cart/{userEmail}/add")
    Call<HardwareOrder> addItemToCart(@Path("userEmail") String userEmail, @Body HardwareOrder order);

    @DELETE("shopping_cart/{userEmail}/{hardwareOrderId}/remove")
    Call<Void> removeItemFromCart(@Path("userEmail") String userEmail, @Path("hardwareOrderId") Long hardwareOrderId);

    @DELETE("shopping_cart/{userEmail}/{hardwareId}/removeUsingHardwareId")
    Call<Void> removeItemFromCartUsingHardwareId(@Path("userEmail") String userEmail, @Path("hardwareId") Long hardwareId);

    @PUT("shopping_cart/{userEmail}/update")
    Call<HardwareOrder> updateItemInCart(@Path("userEmail") String userEmail, @Body HardwareOrder order);

    @GET("shopping_cart/{userEmail}/purchase")
    Call<Void> purchase(@Path("userEmail") String userEmail);

    @GET("shopping_cart/{userEmail}/cost")
    Call<Double> getTotalCost(@Path("userEmail") String userEmail);

    @GET("shopping_cart/{userEmail}/hardwareItems")
    Call<List<Hardware>> getHardwareOrderItem(@Path("userEmail") String userEmail);
}
