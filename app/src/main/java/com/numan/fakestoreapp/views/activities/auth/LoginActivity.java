package com.numan.fakestoreapp.views.activities.auth;

import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.material.snackbar.Snackbar;
import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.CloseSoftKeyBoardOnTouchOutside;
import com.numan.fakestoreapp.common.ConnectionManager;
import com.numan.fakestoreapp.common.InputValidator;
import com.numan.fakestoreapp.common.MySharedPreference;
import com.numan.fakestoreapp.common.dtos.Login;
import com.numan.fakestoreapp.databinding.ActivityLoginBinding;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.activities.home.MainActivity;

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
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//            finish();
            if (!InputValidator.isValidEmail(binding.etEmail.getText().toString())) {
                showToast("Please enter a valid Email!");
            }else if(!InputValidator.isValidPassword(binding.etPassword.getText().toString())) {
                showToast("Please enter a valid password, password must be min of 8 characters!");
            } else {
            if (ConnectionManager.isOnline(getApplicationContext())) {
                showProgress(this);
                Login login = new Login();
                login.setEmail(binding.etEmail.getText().toString());
                login.setPassword(binding.etPassword.getText().toString());
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

                MySharedPreference.setUser(getApplicationContext(), loginResponse);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();

            } else {
                showToast("Unable to login, please try again later.");
            }
        });


    }
}