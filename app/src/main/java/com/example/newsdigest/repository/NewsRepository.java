package com.example.newsdigest.repository;

import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.newsdigest.models.SearchResponse;
import com.example.newsdigest.network.ApiService;
import com.example.newsdigest.utils.BasicUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private static final String TAG = NewsRepository.class.getName();

    private static ApiService apiService;

    public static LiveData<SearchResponse> getNews() {

        apiService = BasicUtils.connectApi().create(ApiService.class);

        final MutableLiveData<SearchResponse> responseData = new MutableLiveData<>();
        // get news list
        Call<SearchResponse> call = apiService.getSearchResponse();
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                responseData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Log.d(TAG, Objects.requireNonNull(t.getMessage()));
                }
            }
        });
        return responseData;
    }
}
