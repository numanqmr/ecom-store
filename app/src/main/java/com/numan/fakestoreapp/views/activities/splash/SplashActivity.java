package com.numan.fakestoreapp.views.activities.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.numan.fakestoreapp.R;
import com.numan.fakestoreapp.common.MySharedPreference;
import com.numan.fakestoreapp.common.responseDtos.LoginResponse;
import com.numan.fakestoreapp.views.activities.BaseActivity;
import com.numan.fakestoreapp.views.activities.auth.LoginActivity;
import com.numan.fakestoreapp.views.activities.home.MainActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CircularProgressIndicator indicator =  findViewById(R.id.progress_circular);
        indicator.setIndeterminate(true);

        new Handler(Looper.myLooper()).postDelayed(() -> {
            LoginResponse response = MySharedPreference.getUser(getApplicationContext());
            if(response!=null){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();

        },1500);

    }
}