package com.example.girlsiteme_commerce.Model;

import java.util.List;

public class ProductResponse {
    private List<ProductModel> products;

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }
}