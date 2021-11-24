package com.numan.fakestoreapp.views.activities.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;

import com.google.android.material.snackbar.Snackbar;
import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.CloseSoftKeyBoardOnTouchOutside;
import com.numan.fakestoreapp.common.ConnectionManager;
import com.numan.fakestoreapp.common.dtos.Register;
import com.numan.fakestoreapp.databinding.ActivityRegisterBinding;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.activities.home.MainActivity;

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

            //TODO: use input validators
            if (!TextUtils.isEmpty(binding.etEmail.getText())
                    && !TextUtils.isEmpty(binding.etPassword.getText())
                    && !TextUtils.isEmpty(binding.etFullName.getText())
                    && !TextUtils.isEmpty(binding.etNumber.getText())
            ) {

                if (ConnectionManager.isOnline(getApplicationContext())) {
                    showProgress(this);
                    Register register = new Register();
                    mViewModel.registerUser(register);
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
    private void observeViewModel(ActivityRegisterBinding binding) {

        mViewModel.getRegisterResponse().observe(this, loginResponse -> {

            hideProgress();

            if (loginResponse != null) {

                //MySharedPreference.setUser(getApplicationContext(), loginResponse);
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();

            } else {
                //TODO error handling
            }
        });


    }
}