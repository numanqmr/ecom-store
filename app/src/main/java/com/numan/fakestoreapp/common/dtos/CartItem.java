package com.numan.fakestoreapp.common.dtos;

import java.io.Serializable;

public class CartItem implements Serializable {

    private String quantity;
    private String productId;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
