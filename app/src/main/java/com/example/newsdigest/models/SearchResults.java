package com.example.newsdigest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class SearchResults {

    @SerializedName("status")
    @Expose
    public String status;

    @SerializedName("userTier")
    @Expose
    public String userTier;

    @SerializedName("total")
    @Expose
    public int total;

    @SerializedName("startIndex")
    @Expose
    public int startIndex;

    @SerializedName("pageSize")
    @Expose
    public int pageSize;

    @SerializedName("currentPage")
    @Expose
    public int currentPage;

    @SerializedName("pages")
    @Expose
    public int pages;

    @SerializedName("orderBy")
    @Expose
    public String orderBy;

    @SerializedName("results")
    @Expose
    public List<NewsList> newsList;
}
