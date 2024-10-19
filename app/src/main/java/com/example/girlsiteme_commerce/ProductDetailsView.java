package com.example.girlsiteme_commerce;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.girlsiteme_commerce.Model.ProductModel;
import com.example.girlsiteme_commerce.ViewModel.SharedViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductDetailsView extends AppCompatActivity {

    TextView dBrand, dPrice, dDiscount, dTitle, dDes, dRating, dDelivery, dReturn;
    Button addToCart_btn;
    ImageView dImage;
    SharedViewModel sharedViewModel;
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
        dDes = findViewById(R.id.dDes);
        dTitle = findViewById(R.id.dTitle);
        dDiscount = findViewById(R.id.dDiscount);
        addToCart_btn = findViewById(R.id.addToCart_btn);

        int id = getIntent().getIntExtra("productId", 0);

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        sharedViewModel.getProductList().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productList) {
                product = sharedViewModel.getProductById(id);
                if (product != null) {
                    Toast.makeText(ProductDetailsView.this, "Product not found" + id, Toast.LENGTH_SHORT).show();
                    displayProductDetails(product);
                } else {
                    Toast.makeText(ProductDetailsView.this, "Product not found", Toast.LENGTH_SHORT).show();
                }
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
        dDes.setText(product.getDescription());
        dTitle.setText(product.getTitle());
        dRating.setText(String.valueOf(product.getRating()));
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