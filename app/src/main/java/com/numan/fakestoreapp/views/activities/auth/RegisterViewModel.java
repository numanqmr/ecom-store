package com.numan.fakestoreapp.views.activities.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.numan.fakestoreapp.common.responseDtos.RegisterResponse;
import com.numan.fakestoreapp.network.RetrofitService;
import com.numan.fakestoreapp.viewModels.BaseViewModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends BaseViewModel {

    private static final String TAG = RegisterViewModel.class.getSimpleName();
    private MutableLiveData<RegisterResponse> mRegisterResponse = new MutableLiveData<>();

    /**
     * register api
     */
    public void registerUser(HashMap map) {

        RetrofitService.getInstance().registerUser(map, new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                Log.d(TAG, "Response Code= " + response.code());

                if (response.code() == 200) {
                    if (response.body() != null) {
                        mRegisterResponse.setValue(new RegisterResponse());
                    } else {
                        mRegisterResponse.setValue(null);
                    }

                } else {
                    mRegisterResponse.setValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "register error= " + t.getLocalizedMessage());
                mRegisterResponse.setValue(null);
            }
        });
    }

    /**
     * getters of liveData ..
     */
    public LiveData<RegisterResponse> getRegisterResponse() {
        return mRegisterResponse;
    }

}
