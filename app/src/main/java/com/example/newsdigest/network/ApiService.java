package com.example.newsdigest.network;

import com.example.newsdigest.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/search")
    Call<SearchResponse> getSearchResponse(@Query("api-key") String apiKey);
}
