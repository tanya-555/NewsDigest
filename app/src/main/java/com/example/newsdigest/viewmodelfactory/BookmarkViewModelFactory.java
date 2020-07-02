package com.example.newsdigest.viewmodelfactory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsdigest.viewmodel.BookmarkViewModel;

public class BookmarkViewModelFactory implements ViewModelProvider.Factory {

    private Application application;

    public BookmarkViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookmarkViewModel(application);
    }
}
