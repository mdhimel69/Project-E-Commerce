package com.example.girlsiteme_commerce.Fragment;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.example.girlsiteme_commerce.Adapter.ProductAdapter;
import com.example.girlsiteme_commerce.ApiClient;
import com.example.girlsiteme_commerce.DAO.APIService;
import com.example.girlsiteme_commerce.Model.ProductModel;
import com.example.girlsiteme_commerce.Model.ProductResponse;
import com.example.girlsiteme_commerce.R;
import com.example.girlsiteme_commerce.ViewModel.SharedViewModel;
import com.google.android.material.appbar.AppBarLayout;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    SharedViewModel sharedViewModel;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    ArrayList<ProductModel> productList;
    public APIService apiService;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        appBar();
        recyclerView = view.findViewById(R.id.recyclerView);
        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(getContext(), productList, "homeFrag");
        recyclerView.setAdapter(productAdapter);

        apiService = ApiClient.getAPIService();
        loadProducts();

        return view;
    }

    private void loadProducts() {
        Call<ProductResponse> call = apiService.getAllProducts();
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.body() != null) {
                    Log.d("API_RESPONSE_BODY", response.body().toString());  // Log the body for analysis
                    if (response.body().getProducts() != null) {
                        productList.addAll(response.body().getProducts());
                        productAdapter.notifyDataSetChanged();
                        sharedViewModel.setProductList(productList);
                    } else {
                        Log.e("API_ERROR", "Error fetching products, response data is null");
                    }
                } else {
                    Log.e("API_ERROR", "Error fetching products, response body is null");
                }
            }
            @Override
            public void onFailure(Call<ProductResponse> call, Throwable throwable) {
                Log.d("error", throwable.getMessage());
            }
        });
    }

    private void appBar() {
        AppBarLayout appBarLayout = view.findViewById(R.id.appBarLayout);
        if (appBarLayout != null) {
            appBarLayout.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.my_primary));
        }

        Window window = requireActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(requireContext(), R.color.my_primary));
    }
}