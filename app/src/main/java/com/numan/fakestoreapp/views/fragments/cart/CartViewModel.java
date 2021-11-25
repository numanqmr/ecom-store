package com.numan.fakestoreapp.views.fragments.cart;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.numan.fakestoreapp.common.MySharedPreference;
import com.numan.fakestoreapp.common.dtos.CartItem;
import com.numan.fakestoreapp.common.dtos.Product;
import com.numan.fakestoreapp.network.RetrofitService;
import com.numan.fakestoreapp.viewModels.BaseViewModel;
import com.numan.fakestoreapp.views.fragments.productDetails.ProductDetailsViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends BaseViewModel {

    private static final String TAG = ProductDetailsViewModel.class.getSimpleName();
    private MutableLiveData<ArrayList<CartItem>> mCartResponse = new MutableLiveData<>();
    private MutableLiveData<JsonObject> mUpdateCartResponse = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Product>> mProductsList = new MutableLiveData<>();

    /**
     * add items to cart api
     */
    public void updateCart(HashMap<String, Object> map) {

        RetrofitService.getInstance().updateCart(map, "1" , new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.d(TAG, "Response Code= " + response.code());
                Log.d(TAG, "Response body update cart= " + response.body());

                if (response.code() == 200) {
                    mUpdateCartResponse.setValue(response.body());
                } else {
                    mUpdateCartResponse.setValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e(TAG, "update cart error= " + t.getLocalizedMessage());
                mUpdateCartResponse.setValue(null);
            }
        });
    }

    /**
     * get user cart
     */
    public void getUserCart() {

        RetrofitService.getInstance().getUserCart("1",new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.d(TAG, "Response Code= " + response.code());
                Log.d(TAG, "Response body get cart= " + response.body());

                if (response.code() == 200) {
                    JsonObject object = response.body();
                    JsonArray cartItems = object.getAsJsonArray("products");

                    ArrayList<CartItem> cartItemsList = MySharedPreference.getGson().fromJson(cartItems,
                            new TypeToken<ArrayList<CartItem>>() {
                            }.getType());
                    mCartResponse.setValue(cartItemsList);
                } else {
                    mCartResponse.setValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e(TAG, "get cart error= " + t.getLocalizedMessage());
                mCartResponse.setValue(null);
            }
        });
    }

    /**
     * get products
     */
    public void getAllProducts() {

        RetrofitService.getInstance().getAllProducts(new Callback<JsonArray>() {
            @Override
            public void onResponse(@NonNull Call<JsonArray> call, @NonNull Response<JsonArray> response) {
                Log.d(TAG, "Response Code= " + response.code());
                Log.d(TAG, "Response body all products= " + response.body());

                if (response.code() == 200) {
                    //convert to array list of products.
                    ArrayList<Product> productsList = MySharedPreference.getGson().fromJson(response.body(),
                            new TypeToken<ArrayList<Product>>() {
                            }.getType());
                    mProductsList.setValue(productsList);
                } else {
                    mProductsList.setValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonArray> call, @NonNull Throwable t) {
                Log.e(TAG, "all prods error= " + t.getLocalizedMessage());
                mProductsList.setValue(null);
            }
        });
    }


    /**
     * getters of liveData..
     */

    public LiveData<JsonObject> getUpdateCartResponse() {
        return mUpdateCartResponse;
    }
    public LiveData<ArrayList<CartItem>> getUserCartResponse() {
        return mCartResponse;
    }
    public LiveData<ArrayList<Product>> getAllProductsResponse() {
        return mProductsList;
    }
}