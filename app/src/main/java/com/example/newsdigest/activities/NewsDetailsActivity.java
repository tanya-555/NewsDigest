package com.example.newsdigest.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.newsdigest.R;
import com.example.newsdigest.databinding.NewsDetailsActivityBinding;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String WEB_URL = "web_url";
    private static final String TAG = NewsDetailsActivity.class.getName();

    private NewsDetailsActivityBinding binding;
    private String url;
    private CompositeDisposable disposable;
    private ImageView backBtn;
    private View toolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.news_details_activity);
        toolbarLayout = binding.detailsToolbar;
        backBtn = toolbarLayout.findViewById(R.id.iv_back);
        showLoadingView();
        disposable = new CompositeDisposable();
        getUrl();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showLoadingView();
        if(TextUtils.isEmpty(url)) {
            showErrorView();
        } else {
            showContentView();
        }
    }

    private void getUrl() {
        url = getIntent().getStringExtra(WEB_URL);
    }

    private void initListener() {
        disposable.add(RxView.clicks(backBtn)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe( s -> {
                      this.finish();
                  }, e -> {
                      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                  }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void showLoadingView() {
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.VISIBLE);
    }

    private void showErrorView() {
        binding.loadingView.setVisibility(View.GONE);
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.VISIBLE);
    }

    private void showContentView() {
        binding.loadingView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.GONE);
        binding.contentView.setVisibility(View.VISIBLE);
        binding.contentView.loadUrl(url);
    }
}
