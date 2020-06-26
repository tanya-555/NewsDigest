package com.example.newsdigest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("status")
    public String status;

    @SerializedName("total")
    public int total;

    @SerializedName("results")
    public List<SearchResults> resultsList;
}
