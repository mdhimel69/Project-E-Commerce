package com.example.girlsiteme_commerce;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.girlsiteme_commerce.Model.ProductModel;
import com.example.girlsiteme_commerce.Model.ProductResponse;
import com.example.girlsiteme_commerce.ViewModel.SharedViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsView extends AppCompatActivity {

    TextView dBrand, dPrice, dDiscount, dTitle, dDes, dRating, dDelivery, dReturn;
    Button addToCart_btn;
    ImageView dImage;
    MaterialToolbar topAppBar;
    ConstraintLayout main;
    ProductModel product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_view);
        appBar();

        dBrand = findViewById(R.id.dBrand);
        dPrice = findViewById(R.id.dPrice);
        dReturn = findViewById(R.id.dReturn);
        dDelivery = findViewById(R.id.dDelivery);
        dRating = findViewById(R.id.dRating);
        dImage = findViewById(R.id.dImage);
        main = findViewById(R.id.main);
        dDes = findViewById(R.id.dDes);
        dTitle = findViewById(R.id.dTitle);
        dDiscount = findViewById(R.id.dDiscount);
        topAppBar = findViewById(R.id.topAppBar);
        addToCart_btn = findViewById(R.id.addToCart_btn);

        main.setVisibility(View.INVISIBLE);
        //sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        int id = getIntent().getIntExtra("productId", 1);
        Log.d("id of product", ""+id);


        //add retrofitCall
        Call<ProductModel> call = ApiClient.getAPIService().getProductById(id);
        call.enqueue(new Callback<ProductModel>() {
            @Override
            public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                product = response.body();
                if (product != null) {
                    main.setVisibility(View.VISIBLE);
                    displayProductDetails(product);
                    Log.d("found", "Product found with ID: " + id);
                } else {
                    Toast.makeText(ProductDetailsView.this, "Product not found with ID: " + id, Toast.LENGTH_SHORT).show();
                    Log.d("Not found", "Product not found");
                }
            }

            @Override
            public void onFailure(Call<ProductModel> call, Throwable throwable) {
                Toast.makeText(ProductDetailsView.this, "There is something wrong, please try again! ", Toast.LENGTH_SHORT).show();
                Log.d("ResponseNot found", ""+throwable);
            }
        });

    }


    private void displayProductDetails(ProductModel product) {
        String imageUrl = product.getImages().get(0);
        String brandName = product.getBrand();
        Double discountPercent = product.getDiscountPercentage();

        if (brandName != null) {
            dBrand.setText(brandName);
            dBrand.setVisibility(View.VISIBLE);
        } else {
            dBrand.setVisibility(View.INVISIBLE);
        }

        if (discountPercent >= 10) {
            dDiscount.setText("Sale " + discountPercent + "%");
            dDiscount.setVisibility(View.VISIBLE);
        } else {
            dDiscount.setVisibility(View.INVISIBLE);
        }

        Picasso.get().load(imageUrl).into(dImage);
        topAppBar.setTitle(product.getCategory());
        dDes.setText(""+ product.getDescription());
        dTitle.setText(""+ product.getTitle());
        dRating.setText(""+ product.getRating());
        dPrice.setText("$" + product.getPrice());
    }

    private void appBar() {
        // Set the AppBar color programmatically
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        if (appBarLayout != null) {
            appBarLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.my_primary));
        }

        // Set the StatusBar color to match the AppBar color
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.my_primary));
    }
}