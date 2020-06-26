package com.example.newsdigest.models;

import com.google.gson.annotations.SerializedName;

public class SearchResults {

    @SerializedName("sectionName")
    public String sectionName;

    @SerializedName("webTitle")
    public String webTitle;

    @SerializedName("webUrl")
    public String webUrl;
}
