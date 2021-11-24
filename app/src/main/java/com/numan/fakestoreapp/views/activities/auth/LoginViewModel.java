package com.numan.fakestoreapp.views.activities.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.numan.fakestoreapp.common.responseDtos.LoginResponse;
import com.numan.fakestoreapp.network.RetrofitService;
import com.numan.fakestoreapp.viewModels.BaseViewModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends BaseViewModel {

    private static final String TAG = LoginViewModel.class.getSimpleName();
    private MutableLiveData<LoginResponse> mLoginResponse = new MutableLiveData<>();

    /**
     * login api
     */
    public void loginUser(String email, String password) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("username", email);
        map.put("password", password);

        RetrofitService.getInstance().loginUser(map, new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                Log.d(TAG, "Response Code= " + response.code());

                if (response.code() == 200) {
                    if (response.body() != null) {

                            Log.i(TAG, "Login ->  " + response.body().toString());
                            mLoginResponse.setValue(response.body());

                    } else {
                        mLoginResponse.setValue(null);

                    }

                } else {
                    mLoginResponse.setValue(null);
                }

            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "login error= " + t.getLocalizedMessage());
                //TODO: send error here
            }
        });
    }

    /**
     * getters of liveData..
     */
    public LiveData<LoginResponse> getLoginResponse() {
        return mLoginResponse;
    }

}
