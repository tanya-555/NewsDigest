package com.example.newsdigest.viewmodel;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsdigest.models.BookmarkModel;
import com.example.newsdigest.repository.BookmarksRepository.BookmarksRepository;

import java.util.List;

public class BookmarkViewModel extends ViewModel {

    private BookmarksRepository repository;

    public BookmarkViewModel(Application application) {
        repository = new BookmarksRepository(application);
    }

    public MutableLiveData<List<BookmarkModel>> getBookmarksLiveData() {
        return repository.getBookmarks();
    }

    public void insertBookmark(BookmarkModel bookmarkModel) {
        repository.insertBookmark(bookmarkModel);
    }
}
