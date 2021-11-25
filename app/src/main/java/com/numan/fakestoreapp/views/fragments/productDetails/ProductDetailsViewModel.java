package com.numan.fakestoreapp.views.fragments.productDetails;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.numan.fakestoreapp.common.MySharedPreference;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.network.RetrofitService;
import com.numan.fakestoreapp.viewModels.BaseViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsViewModel extends BaseViewModel {

    private static final String TAG = ProductDetailsViewModel.class.getSimpleName();
    private MutableLiveData<JsonObject> mCartResponse = new MutableLiveData<>();

    /**
     * add items to cart api
     */
    public void addProductToCard(HashMap<String, Object> map) {

        RetrofitService.getInstance().addProductToCart(map , new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.d(TAG, "Response Code= " + response.code());
                Log.d(TAG, "Response body add to cart= " + response.body());

                if (response.code() == 200) {
                    mCartResponse.setValue(response.body());
                } else {
                    mCartResponse.setValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.d(TAG, "add to carrrt error= " + t.getLocalizedMessage());
                mCartResponse.setValue(null);
            }
        });
    }


    /**
     * getters of liveData..
     */

    public LiveData<JsonObject> getAddToCartResult() {
        return mCartResponse;
    }
}