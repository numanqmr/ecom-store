package com.numan.fakestoreapp.common.interfaces;

import com.numan.fakestoreapp.common.dtos.Product;

public interface OnQuantityUpdateListener {

    void onUpdate(Product product, int quantity);

}
