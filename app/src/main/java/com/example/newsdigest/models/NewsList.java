package com.example.newsdigest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class NewsList {
    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("sectionId")
    @Expose
    public String sectionId;

    @SerializedName("sectionName")
    @Expose
    public String sectionName;

    @SerializedName("webPublicationDate")
    @Expose
    public String webPublicationDate;

    @SerializedName("webTitle")
    @Expose
    public String webTitle;

    @SerializedName("webUrl")
    @Expose
    public String webUrl;

    @SerializedName("apiUrl")
    @Expose
    public String apiUrl;
}
