package com.example.girlsiteme_commerce.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.girlsiteme_commerce.Model.ProductModel;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<List<ProductModel>> productListLiveData = new MutableLiveData<>();

    public LiveData<List<ProductModel>> getProductList() {
        return productListLiveData;
    }

    // This function should be called once the data is retrieved from the API
    public void setProductList(List<ProductModel> productList) {
        productListLiveData.setValue(productList);
    }

    public ProductModel getProductById(int id) {
        if (productListLiveData.getValue() != null) {
            for (ProductModel product : productListLiveData.getValue()) {
                if (product.getId() == id) {
                    return product;
                }
            }
        }
        return null;
    }
}
