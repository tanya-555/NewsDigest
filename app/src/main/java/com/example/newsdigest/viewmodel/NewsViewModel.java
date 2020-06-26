package com.example.newsdigest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.newsdigest.models.SearchResponse;
import com.example.newsdigest.repository.NewsRepository;

public class NewsViewModel extends AndroidViewModel {

    private final LiveData<SearchResponse> responseLiveData;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        responseLiveData = NewsRepository.getNews();
    }

    public LiveData<SearchResponse> getNewsList() {
        return responseLiveData;
    }

}
