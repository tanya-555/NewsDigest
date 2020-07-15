package com.example.newsdigest.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "bookmarks")
public class BookmarkModel implements Serializable {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    private int bookmarkId;

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;

    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    private String url;

    @SerializedName("section")
    @Expose
    @ColumnInfo(name = "section")
    private String section;

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getUrl() {
        return url;
    }
}
