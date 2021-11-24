package com.numan.fakestoreapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.numan.fakestoreapp.common.Constants;
import com.numan.fakestoreapp.common.responseDtos.LoginResponse;
import com.numan.fakestoreapp.common.responseDtos.RegisterResponse;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {

    private static RetrofitService sRetrofitService;
    public RetrofitApi retrofitApi;

    /**
     * Constructor with retrofit builder configurations and
     * okHttp client setup.
     */
    public RetrofitService() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        retrofitApi = retrofit.create(RetrofitApi.class);
    }

    /**
     * Create or get an instance of retrofit service for api calling.
     *
     * @return instance of RetrofitService.
     */
    public synchronized static RetrofitService getInstance() {
        if (sRetrofitService == null)
            sRetrofitService = new RetrofitService();
        return sRetrofitService;
    }

    public void loginUser(HashMap map, Callback<LoginResponse> callback) {
        retrofitApi.loginUser(map).enqueue(callback);
    }

    public void registerUser(HashMap map, Callback<RegisterResponse> callback) {
        retrofitApi.registerUser(map).enqueue(callback);
    }

    public void getAllCategories(Callback<JsonArray> callback) {
        retrofitApi.getCategories().enqueue(callback);
    }

    public void getAllProducts(Callback<JsonArray> callback) {
        retrofitApi.getProducts().enqueue(callback);
    }

    public void getProductsInCategory(String catName, Callback<JsonArray> callback) {
        retrofitApi.getProductsInCategory(catName).enqueue(callback);
    }

    public void addProductToCard(HashMap map, Callback<JsonObject> callback) {
        retrofitApi.addProductToCart(map).enqueue(callback);
    }

}
