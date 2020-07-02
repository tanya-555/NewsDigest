package com.example.newsdigest.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bookmarks")
public class BookmarkModel {

    @PrimaryKey(autoGenerate = true)
    private int bookmarkId;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "url")
    private String url;

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
