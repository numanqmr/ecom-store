package com.numan.fakestoreapp.views.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.CloseSoftKeyBoardOnTouchOutside;
import com.numan.fakestoreapp.common.ConnectionManager;
import com.numan.fakestoreapp.common.InputValidator;
import com.numan.fakestoreapp.common.MySharedPreference;
import com.numan.fakestoreapp.common.dtos.Login;
import com.numan.fakestoreapp.databinding.ActivityLoginBinding;
import com.numan.fakestoreapp.viewModels.LoginViewModel;

import org.json.JSONException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends BaseActivity {

    private LoginViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new LoginViewModel();

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setViewModel(mViewModel);
        binding.executePendingBindings();

        /*To hide keyboard upon touch*/
        CloseSoftKeyBoardOnTouchOutside close = new CloseSoftKeyBoardOnTouchOutside();
        close.setupUI(binding.rlContainer, LoginActivity.this);

        //inflate views.
        initViews(binding);

        observeViewModel(binding);
    }

    private void initViews(ActivityLoginBinding binding) {

        binding.btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            //TODO: use input validators
            if (!TextUtils.isEmpty(binding.etEmail.getText()) && !TextUtils.isEmpty(binding.etPassword.getText())) {

                if (ConnectionManager.isOnline(getApplicationContext())) {
                    showProgress(this);
                    Login login = new Login();
                    mViewModel.loginUser(login);
                } else {
                    try {
                        Snackbar.make(findViewById(android.R.id.content), "You're offline. Please connect to an internet connection.", Snackbar.LENGTH_INDEFINITE)
                                .setAction("CLOSE", view -> {

                                })
                                .setActionTextColor(getResources().getColor(android.R.color.white))
                                .show();
                    } catch (NullPointerException | IllegalStateException e) {
                        e.printStackTrace();
                    }
                }

            } else {
                //TODO error handling
            }
        });

    }

    /**
     * Observe view model LiveData mutable variables.
     */
    private void observeViewModel(ActivityLoginBinding binding) {

        mViewModel.getLoginResponse().observe(this, loginResponse -> {

            hideProgress();

            if (loginResponse != null) {

                //MySharedPreference.setUser(getApplicationContext(), loginResponse);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();

            } else {
                //TODO error handling
            }
        });


    }
}