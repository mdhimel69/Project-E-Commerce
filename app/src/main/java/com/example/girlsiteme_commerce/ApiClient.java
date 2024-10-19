package com.example.girlsiteme_commerce;

import com.example.girlsiteme_commerce.DAO.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://dummyjson.com/";
    private static Retrofit retrofit = null;

    // Singleton pattern for Retrofit instance
    public static APIService getAPIService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(APIService.class);
    }
}
