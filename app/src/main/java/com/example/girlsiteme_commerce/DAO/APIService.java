package com.example.girlsiteme_commerce.DAO;

import com.example.girlsiteme_commerce.Model.ProductModel;
import com.example.girlsiteme_commerce.Model.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface APIService {

    @GET("products")
    Call<ProductResponse> getAllProducts();

    @GET("products/{id}")
    Call<ProductModel> getProductById(@Path("id") int id);
}
