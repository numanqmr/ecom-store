package com.numan.fakestoreapp.views.fragments.cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.numan.fakestoreapp.viewModels.BaseViewModel;

public class CartViewModel extends BaseViewModel {

    private MutableLiveData<String> mText;

    public CartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}