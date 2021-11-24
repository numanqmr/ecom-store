package com.numan.fakestoreapp.common.interfaces;

import android.view.View;

import com.numan.fakestoreapp.common.dtos.Product;

public interface CustomClickListener {

    void onProductClick(Product product, int quantity);

}
