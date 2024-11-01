package com.example.girlsiteme_commerce.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.girlsiteme_commerce.Model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<ProductModel>> productListLiveData = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<ProductModel>> getProductList() {
        return productListLiveData;
    }

    // This function should be called once the data is retrieved from the API
    public void setProductList(List<ProductModel> productList) {
        productListLiveData.setValue(productList);
//        productListLiveData.notifyObservers();  // trigger observer after setting data
        Log.d("sharedView Data", "Successfully added data: " + productList.size() + " products");
    }

//    public ProductModel getProductById(int id) {
//        if (productListLiveData.getValue() != null) {
//            Log.d("ProductList", "Products: " + productListLiveData.getValue());
//            for (ProductModel product : productListLiveData.getValue()) {
//                Log.d("ViwModelProductID", "Checking Product ID: " + product.getId());
//                if (product.getId() == id) {
//                    return product;
//                }
//            }
//        }
//        return null;
//    }
}
