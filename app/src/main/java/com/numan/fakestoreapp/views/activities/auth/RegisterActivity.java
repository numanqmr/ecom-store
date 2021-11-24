package com.numan.fakestoreapp.views.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.CloseSoftKeyBoardOnTouchOutside;
import com.numan.fakestoreapp.common.ConnectionManager;
import com.numan.fakestoreapp.common.InputValidator;
import com.numan.fakestoreapp.common.MySharedPreference;
import com.numan.fakestoreapp.common.dtos.Name;
import com.numan.fakestoreapp.common.responseDtos.LoginResponse;
import com.numan.fakestoreapp.databinding.ActivityRegisterBinding;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.activities.home.MainActivity;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity {

    private RegisterViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new RegisterViewModel();

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setViewModel(mViewModel);
        binding.executePendingBindings();

        /*To hide keyboard upon touch*/
        CloseSoftKeyBoardOnTouchOutside close = new CloseSoftKeyBoardOnTouchOutside();
        close.setupUI(binding.rlContainer, RegisterActivity.this);

        //inflate views.
        initViews(binding);

        observeViewModel(binding);
    }

    private void initViews(ActivityRegisterBinding binding) {

        binding.btnRegister.setOnClickListener(v -> {

            if (!TextUtils.isEmpty(binding.etEmail.getText())
                    && InputValidator.isValidPassword(binding.etPassword.getText().toString())
                    && InputValidator.isValidEmail(binding.etEmail.getText().toString())
                    && !TextUtils.isEmpty(binding.etFullName.getText())
                    && !TextUtils.isEmpty(binding.etNumber.getText())
            ) {

                if (ConnectionManager.isOnline(getApplicationContext())) {
                    showProgress(this);
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("username", binding.etUsername.getText().toString());
                    map.put("password", binding.etPassword.getText().toString());
                    Name name = new Name();
                    name.setFirstname(binding.etFullName.getText().toString());
                    name.setLastname(""); // it wont be saved anyway :l
                    map.put("fullName", name);
                    //and the rest.

                    mViewModel.registerUser(map);

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
                showToast("Please enter complete and valid credentials.");
            }
        });

    }

    /**
     * Observe view model LiveData mutable variables.
     */
    private void observeViewModel(ActivityRegisterBinding binding) {

        mViewModel.getRegisterResponse().observe(this, loginResponse -> {

            hideProgress();
            MySharedPreference.setUser(getApplicationContext(), new LoginResponse());//hardcoding cause it doesn't register anyway
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();

        });


    }
}