package com.example.newsdigest.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdigest.R;
import com.example.newsdigest.adapter.NewsAdapter;
import com.example.newsdigest.databinding.LandingActivityBinding;
import com.example.newsdigest.models.NewsList;
import com.example.newsdigest.models.SearchResponse;
import com.example.newsdigest.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.List;

public class LandingActivity extends AppCompatActivity {

    private static final String STATUS_OK = "ok";

    private LandingActivityBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private List<NewsList> newsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.landing_activity, null, false);
        setContentView(binding.getRoot());
        newsList = new ArrayList<>();
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        observeViewModel(newsViewModel);
        binding.contentView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.VISIBLE);
        initRecyclerView();
    }

    private void observeViewModel(NewsViewModel newsViewModel) {
        newsViewModel.getNewsList().observe(this, new Observer<SearchResponse>() {
            @Override
            public void onChanged(SearchResponse searchResponse) {
                binding.loadingView.setVisibility(View.GONE);
                setData(searchResponse);
            }
        });
    }

    private void setData(SearchResponse searchResponse) {
        if (searchResponse == null) {
            showErrorView();
        } else {
            showContentView();
            newsList.clear();
            if (searchResponse.response.newsList != null) {
                newsList.addAll(searchResponse.response.newsList);
            }
            adapter.setList(newsList);
            adapter.notifyDataSetChanged();
        }
    }

    private void showErrorView() {
        binding.errorView.setVisibility(View.VISIBLE);
        binding.contentView.setVisibility(View.GONE);
    }

    private void showContentView() {
        binding.contentView.setVisibility(View.VISIBLE);
        binding.errorView.setVisibility(View.GONE);
    }

    private void initRecyclerView() {
        adapter = new NewsAdapter(this);
        LinearLayout contentView = binding.contentView;
        recyclerView = contentView.findViewById(R.id.rv_search_results);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
