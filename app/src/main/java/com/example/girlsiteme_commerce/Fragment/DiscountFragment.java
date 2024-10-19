package com.example.girlsiteme_commerce.Fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;

import com.example.girlsiteme_commerce.Adapter.ProductAdapter;
import com.example.girlsiteme_commerce.ApiClient;
import com.example.girlsiteme_commerce.DAO.APIService;
import com.example.girlsiteme_commerce.Model.ProductModel;
import com.example.girlsiteme_commerce.Model.ProductResponse;
import com.example.girlsiteme_commerce.R;
import com.example.girlsiteme_commerce.ViewModel.SharedViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;


public class DiscountFragment extends Fragment {
    RecyclerView disRecyclerView;
    ProductAdapter productAdapter;
    SharedViewModel sharedViewModel;
    ArrayList<ProductModel> productList;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discount, container, false);
        // Initialize the AppBar and RecyclerView
        appBar();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getProductList().observe(getViewLifecycleOwner(), productModels -> {
            productList = new ArrayList<>(productModels);
            filterDiscountProducts();

            disRecyclerView = view.findViewById(R.id.disRecyclerView);
            productAdapter = new ProductAdapter(getContext(), productList, "discountFrag");
            disRecyclerView.setAdapter(productAdapter);
        });



        // Inflate the layout for this fragment
        return view;
    }

    private void filterDiscountProducts() {
        ArrayList<ProductModel> filteredList = new ArrayList<>();
        for (ProductModel product : productList) {
            if (product.getDiscountPercentage() >= 10) {
                filteredList.add(product);
            }
        }
        productList = filteredList;
    }

    private void appBar() {
        // Set the AppBar color programmatically
        // AppBarLayout appBarLayout = view.findViewById(R.id.appBarLayout);
        // if (appBarLayout != null) {
        //     appBarLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.my_primary));
        // }

        // Set the StatusBar color to match the AppBar color
        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.my_primary));
    }
}