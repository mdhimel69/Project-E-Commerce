package com.example.girlsiteme_commerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.girlsiteme_commerce.Model.ProductModel;
import com.example.girlsiteme_commerce.ProductDetailsView;
import com.example.girlsiteme_commerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HOME = 0;
    private static final int VIEW_TYPE_DISCOUNT = 1;
    private static final int VIEW_TYPE_CART = 2;

    private ArrayList<ProductModel> productList;
    private Context context;
    private String whichFragment;

    public ProductAdapter(Context context, ArrayList<ProductModel> productList, String whichFragment) {
        this.productList = productList;
        this.context = context;
        this.whichFragment = whichFragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_HOME || viewType == VIEW_TYPE_DISCOUNT) {
            view = LayoutInflater.from(context).inflate(R.layout.product_grid_layout, parent, false);
            return new ProductViewHolder(view);
        } else if (viewType == VIEW_TYPE_CART) {
            view = LayoutInflater.from(context).inflate(R.layout.product_cart_list_layout, parent, false);
            return new CartViewHolder(view);
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductViewHolder) {
            ProductModel product = productList.get(position);
            ProductViewHolder productHolder = (ProductViewHolder) holder;
            displayProduct(productHolder, product);

            productHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailsView.class);
                    intent.putExtra("productId", product.getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    private void displayProduct(ProductViewHolder holder, ProductModel product) {
        String imageUrl = product.getThumbnail();
        String brandName = product.getBrand();
        Double discountPercent = product.getDiscountPercentage();

        if (brandName != null){
            holder.pBrand.setText(brandName);
            holder.pBrand.setVisibility(View.VISIBLE);
        } else {
            holder.pBrand.setVisibility(View.INVISIBLE);
        }

        if (discountPercent >= 10) {
            holder.pDiscount.setText("Sale " + discountPercent + "%");
            holder.pDiscount.setVisibility(View.VISIBLE);
        } else {
            holder.pDiscount.setVisibility(View.INVISIBLE);  // Hide discount for home fragment if below 10%
        }

        Picasso.get().load(imageUrl).into(holder.pimage);
        holder.pDes.setText(product.getDescription());
        holder.pTitle.setText(product.getTitle());
        holder.pRating.setText(String.valueOf(product.getRating()));
        holder.pPrice.setText("$" + product.getPrice());
    }

    @Override
    public int getItemViewType(int position) {
        if (whichFragment.equals("homeFrag")) {
            return VIEW_TYPE_HOME;
        } else if (whichFragment.equals("discountFrag")) {
            return VIEW_TYPE_DISCOUNT;
        } else if (whichFragment.equals("cartFrag")) {
            return VIEW_TYPE_CART;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView pimage;
        TextView pBrand, pPrice, pTitle, pDiscount, pRating, pDes;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            pimage = itemView.findViewById(R.id.pImage);
            pBrand = itemView.findViewById(R.id.pBrand);
            pPrice = itemView.findViewById(R.id.pPrice);
            pTitle = itemView.findViewById(R.id.pTitle);
            pDiscount = itemView.findViewById(R.id.pDiscount);
            pRating = itemView.findViewById(R.id.pRating);
            pDes = itemView.findViewById(R.id.pDes);
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}