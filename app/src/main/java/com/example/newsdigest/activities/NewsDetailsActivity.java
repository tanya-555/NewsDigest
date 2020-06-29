package com.example.newsdigest.activities;

import android.os.Bundle;
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
import io.reactivex.schedulers.Schedulers;

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
        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.news_details_activity, null, false);
        toolbarLayout = binding.detailsToolbar;
        backBtn = toolbarLayout.findViewById(R.id.iv_back);
        setContentView(binding.getRoot());
        disposable = new CompositeDisposable();
        getUrl();
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.webView.loadUrl(url);
    }

    private void getUrl() {
        url = getIntent().getStringExtra(WEB_URL);
    }

    private void initListener() {
        disposable.add(RxView.clicks(backBtn).subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe( s -> {
                      finish();
                  }, e -> {
                      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                  }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
