package com.numan.fakestoreapp.views.fragments.productDetails;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.numan.fakestoreapp.common.MySharedPreference;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.network.RetrofitService;
import com.numan.fakestoreapp.viewModels.BaseViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsViewModel extends BaseViewModel {

    private static final String TAG = ProductDetailsViewModel.class.getSimpleName();
    private MutableLiveData<ArrayList<Product>> mProducts = new MutableLiveData<>();

    /**
     * get all categories api
     */
    public void getAllCategories() {

        RetrofitService.getInstance().getAllCategories(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                Log.d(TAG, "Response Code= " + response.code());
                Log.d(TAG, "Response body categories= " + response.body());

                if (response.code() == 200) {
                    ArrayList<String> categoriesList = MySharedPreference.getGson().fromJson(response.body(),
                            new TypeToken<ArrayList<String>>() {
                            }.getType());
                    //mcategories.setValue(categoriesList);
                } else {
                    //TODO: send error here
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                Log.d(TAG, "login error= " + t.getLocalizedMessage());
                //TODO: send error here
            }
        });
    }


    /**
     * getters of liveData..
     */

    public LiveData<ArrayList<Product>> getProductsList() {
        return mProducts;
    }
}