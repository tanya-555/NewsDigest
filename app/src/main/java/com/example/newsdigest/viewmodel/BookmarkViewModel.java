package com.example.newsdigest.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsdigest.models.BookmarkModel;
import com.example.newsdigest.repository.BookmarksRepository;

import java.util.List;

public class BookmarkViewModel extends ViewModel {

    private BookmarksRepository repository;

    public BookmarkViewModel(Application application) {
        repository = new BookmarksRepository(application);
    }

    public LiveData<List<BookmarkModel>> getBookmarksLiveData() {
        return repository.getBookmarks();
    }

    public void insertBookmark(BookmarkModel bookmarkModel) {
        repository.insertBookmark(bookmarkModel);
    }
}
