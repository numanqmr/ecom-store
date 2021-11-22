package com.numan.fakestoreapp.network;

import com.numan.fakestoreapp.common.dtos.Login;
import com.numan.fakestoreapp.common.dtos.Register;
import com.numan.fakestoreapp.common.responseDtos.LoginResponse;
import com.numan.fakestoreapp.common.responseDtos.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface RetrofitApi {

    @POST("auth/register")
    Call<RegisterResponse> registerUser(@Body Register object);

    @POST("auth/login")
    Call<LoginResponse> loginUser(@Body Login object);


}