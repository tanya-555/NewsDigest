package com.example.newsdigest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.newsdigest.models.SearchResponse;
import com.example.newsdigest.repository.NewsRepository;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository();
    }

    public MutableLiveData<SearchResponse> getNewsList() {
        return newsRepository.getNews();
    }

}
