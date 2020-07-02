package com.example.newsdigest.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsdigest.models.BookmarkModel;
import com.example.newsdigest.repository.BookmarksRepository.BookmarksRepository;

import java.util.List;

public class BookmarkViewModel extends ViewModel {

    private BookmarksRepository repository;
    private MutableLiveData<List<BookmarkModel>> bookmarksLiveData;

    public BookmarkViewModel(Application application) {
        repository = new BookmarksRepository(application);
        bookmarksLiveData = repository.getBookmarks();
    }

    public MutableLiveData<List<BookmarkModel>> getBookmarksLiveData() {
        return bookmarksLiveData;
    }

    public void insertBookmark(BookmarkModel bookmarkModel) {
        repository.insertBookmark(bookmarkModel);
    }
}
