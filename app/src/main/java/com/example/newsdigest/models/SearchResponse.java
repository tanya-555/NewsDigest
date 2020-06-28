package com.example.newsdigest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class SearchResponse {

    @SerializedName("response")
    @Expose
    public SearchResults response;

}
