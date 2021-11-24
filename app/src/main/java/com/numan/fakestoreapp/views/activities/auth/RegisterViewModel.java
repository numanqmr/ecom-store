package com.numan.fakestoreapp.views.activities.auth;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.numan.fakestoreapp.common.dtos.Register;
import com.numan.fakestoreapp.common.responseDtos.RegisterResponse;
import com.numan.fakestoreapp.network.RetrofitService;
import com.numan.fakestoreapp.viewModels.BaseViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends BaseViewModel {

    private static final String TAG = RegisterViewModel.class.getSimpleName();
    private MutableLiveData<RegisterResponse> mRegisterResponse = new MutableLiveData<>();

    /**
     * register api
     */
    public void registerUser(Register register) {

        RetrofitService.getInstance().registerUser(register, new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                Log.d(TAG, "Response Code= " + response.code());

                if (response.code() == 200) {
                    if (response.body() != null) {

                        if (!response.body().equals("Fail")) {
                            Log.e(TAG, "error register ->  " + response.body().toString());
                            //TODO: send error here

                        } else {
                            mRegisterResponse.setValue(response.body());
                            //Save response to shared preferences.
                            //MySharedPreference.setLoginResponse(getApplication().getApplicationContext(), response.body());
                        }
                    }

                } else {
                    //TODO: send error here
                }

            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                Log.d(TAG, "reegister error= " + t.getLocalizedMessage());
                //TODO: send error here
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
