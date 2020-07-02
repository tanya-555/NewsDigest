package com.example.newsdigest.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.newsdigest.models.BookmarkModel;

import java.util.List;

@Dao
public interface BookmarkOperationsDao {

    //Query to get list of bookmarks
    @Query("SELECT * from bookmarks")
    List<BookmarkModel> getBookmarkList();

    //Query to insert a bookmark
    @Insert
    void insertBookmark(BookmarkModel bookmark);
}
