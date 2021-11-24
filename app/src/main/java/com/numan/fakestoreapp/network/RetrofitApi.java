package com.numan.fakestoreapp.network;

import com.google.gson.JsonArray;
import com.numan.fakestoreapp.common.dtos.Login;
import com.numan.fakestoreapp.common.dtos.Register;
import com.numan.fakestoreapp.common.responseDtos.LoginResponse;
import com.numan.fakestoreapp.common.responseDtos.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface RetrofitApi {

    @POST("auth/register")
    Call<RegisterResponse> registerUser(@Body Register object);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body Login object);

    @GET("products/categories")
    Call<JsonArray> getCategories();

    @GET("products")
    Call<JsonArray> getProducts();

    @GET("products/category/{categoryName}")
    Call<JsonArray> getProductsInCategory(@Path("categoryName") String catName);
//
//    @GET("carts/user/{userId}")
//    Call<JsonArray> getUserCart(@Path("userId") String userId);


}