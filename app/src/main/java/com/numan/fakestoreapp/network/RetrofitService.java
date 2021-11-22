package com.numan.fakestoreapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.numan.fakestoreapp.common.Constants;
import com.numan.fakestoreapp.common.dtos.Login;
import com.numan.fakestoreapp.common.dtos.Register;
import com.numan.fakestoreapp.common.responseDtos.LoginResponse;
import com.numan.fakestoreapp.common.responseDtos.RegisterResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public final class RetrofitService {

    private static RetrofitService sRetrofitService;
    private RetrofitApi retrofitApi;

    /**
     * Constructor with retrofit builder configurations and
     * okHttp client setup.
     */
    private RetrofitService() {

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

    public void loginUser(Login login, Callback<LoginResponse> callback) {
        retrofitApi.loginUser(login).enqueue(callback);
    }

    public void registerUser(Register signup, Callback<RegisterResponse> callback) {
        retrofitApi.registerUser(signup).enqueue(callback);
    }


}
