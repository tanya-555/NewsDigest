package com.example.newsdigest.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasicUtils {

    public static final String baseUrl = "https://content.guardianapis.com/";

    public static Retrofit connectApi() {
        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
        return retrofit;
    }
}
