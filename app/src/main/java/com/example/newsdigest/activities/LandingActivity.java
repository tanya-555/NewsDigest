package com.example.newsdigest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
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
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class LandingActivity extends AppCompatActivity {

    private static final String WEB_URL = "web_url";
    private static final String TAG = LandingActivity.class.getName();

    private LandingActivityBinding binding;
    private NewsViewModel newsViewModel;
    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private List<NewsList> newsList;
    private CompositeDisposable disposable;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.landing_activity);
        setContentView(binding.getRoot());
        setSupportActionBar(findViewById(R.id.toolbar));
        newsList = new ArrayList<>();
        setupNavigationDrawer();
        disposable = new CompositeDisposable();
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        observeViewModel(newsViewModel);
        showLoadingView();
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
            subscribeToNewItemClicked(adapter.getNewsItemClickSubject());
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

    private void subscribeToNewItemClicked(PublishSubject<String> newsItemClickSubject) {
        disposable.add(newsItemClickSubject.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(url -> {
                      launchDetailsActivity(url);
                  }, e -> {
                      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                  }));
    }

    private void launchDetailsActivity(String webUrl) {
        Intent intent = new Intent(LandingActivity.this, NewsDetailsActivity.class);
        intent.putExtra(WEB_URL, webUrl);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void showLoadingView() {
        binding.contentView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.VISIBLE);
    }

    private void setupNavigationDrawer() {
        DrawerLayout dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, dl, R.string.login, R.string.login);
        dl.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
}
