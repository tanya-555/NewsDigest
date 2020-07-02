package com.example.newsdigest.db;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.newsdigest.models.BookmarkModel;

import java.util.List;

@Dao
public interface BookmarkOperationsDao {

    //Query to get list of bookmarks
    @Query("SELECT COUNT(*) from bookmarks")
    MutableLiveData<List<BookmarkModel>> getBookmarkList();
}
