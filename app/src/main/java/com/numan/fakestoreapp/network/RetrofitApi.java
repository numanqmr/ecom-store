package com.numan.fakestoreapp.network;

import com.google.gson.JsonArray;
import com.numan.fakestoreapp.common.responseDtos.LoginResponse;
import com.numan.fakestoreapp.common.responseDtos.RegisterResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

interface RetrofitApi {

    @POST("users")
    @FormUrlEncoded
    Call<RegisterResponse> registerUser(@FieldMap HashMap<String, Object> loginMap);

    @POST("auth/login")
    @FormUrlEncoded
    Call<LoginResponse> loginUser(@FieldMap HashMap<String, Object> loginMap);

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