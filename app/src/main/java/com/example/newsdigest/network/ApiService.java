package com.example.newsdigest.network;

import com.example.newsdigest.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiService {

    @Headers({"api-key : 9baadf70-955d-48fa-b47f-66037d9ed061"})
    @GET("/search")
    Call<SearchResponse> getSearchResponse();
}
