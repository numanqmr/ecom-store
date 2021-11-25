package com.numan.fakestoreapp.views.fragments.profile;

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
import com.numan.fakestoreapp.common.dtos.User;
import com.numan.fakestoreapp.network.RetrofitService;
import com.numan.fakestoreapp.viewModels.BaseViewModel;
import com.numan.fakestoreapp.views.fragments.productDetails.ProductDetailsViewModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileViewModel extends BaseViewModel {

    private static final String TAG = ProductDetailsViewModel.class.getSimpleName();
    private MutableLiveData<User> mUserResponse = new MutableLiveData<>();

    /**
     * get user cart
     */
    public void fetchUserData() {

        RetrofitService.getInstance().getUserData("1", new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.d(TAG, "Response Code= " + response.code());
                Log.d(TAG, "Response user data= " + response.body());

                if (response.code() == 200) {


                    User user = MySharedPreference.getGson().fromJson(response.body(),
                            new TypeToken<User>() {
                            }.getType());
                    mUserResponse.setValue(user);
                } else {
                    mUserResponse.setValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                Log.e(TAG, "user data error= " + t.getLocalizedMessage());
                mUserResponse.setValue(null);
            }
        });
    }


    /**
     * getters of liveData..
     */

    public LiveData<User> getUserDataResponse() {
        return mUserResponse;
    }
}