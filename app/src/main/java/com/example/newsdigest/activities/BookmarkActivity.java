package com.example.newsdigest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsdigest.R;
import com.example.newsdigest.adapter.BookmarkAdapter;
import com.example.newsdigest.databinding.BookmarkActivityBinding;
import com.example.newsdigest.models.BookmarkModel;
import com.example.newsdigest.viewmodel.BookmarkViewModel;
import com.example.newsdigest.viewmodelfactory.BookmarkViewModelFactory;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class BookmarkActivity extends AppCompatActivity {

    private static final String TAG = BookmarkActivity.class.getName();
    private static final String BOOKMARK_LIST = "bookmark_list";
    private static final String WEB_URL = "web_url";

    private List<BookmarkModel> bookmarkModelList;
    private BookmarkActivityBinding binding;
    private ImageView backBtn;
    private CompositeDisposable disposable;
    private BookmarkAdapter adapter;
    private BookmarkViewModel bookmarkViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bookmark_activity);
        setContentView(binding.getRoot());
        View toolbarLayout = binding.detailsToolbar;
        backBtn = toolbarLayout.findViewById(R.id.iv_back);
        disposable = new CompositeDisposable();
        bookmarkModelList = new ArrayList<>();
        bookmarkModelList = (List<BookmarkModel>)getIntent().getExtras().getSerializable(BOOKMARK_LIST);
        ((TextView)binding.detailsToolbar.findViewById(R.id.tv_detail)).setText(R.string.toolbar_header);
        bookmarkViewModel = new ViewModelProvider(this, new BookmarkViewModelFactory(getApplication()))
                .get(BookmarkViewModel.class);
        observeDeleteBookmarkViewModel(bookmarkViewModel);
        showLoadingView();
        initListener();
        initRecyclerView();
        showBookmarks();
    }

    private void observeDeleteBookmarkViewModel(BookmarkViewModel bookmarkViewModel) {
        bookmarkViewModel.getBookmarksLiveData().observe(this, bookmarkList -> {
            bookmarkModelList.clear();
            if(bookmarkList != null) {
                bookmarkModelList.addAll(bookmarkList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void showLoadingView(){
        binding.contentView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.VISIBLE);
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

    private void initRecyclerView() {
        adapter = new BookmarkAdapter(this);
        binding.contentView.setLayoutManager(new LinearLayoutManager(this));
        binding.contentView.setAdapter(adapter);
    }

    private void showBookmarks() {
        if(bookmarkModelList.size() == 0) {
            showErrorView();
        } else {
            showContentView();
            adapter.setBookmarkModelList(bookmarkModelList);
            adapter.notifyDataSetChanged();
            subscribeToBookmarkClick(adapter.getBookmarkClickSubject());
            subscribeToBookmarkLongClick(adapter.getBookmarkLongClickSubject());
        }
    }

    private void subscribeToBookmarkClick(PublishSubject<String> bookmarkClickSubject) {
        disposable.add(bookmarkClickSubject.subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(url -> {
                      launchDetailsActivity(url);
                  }, e -> {
                      Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                  }));
    }

    private void subscribeToBookmarkLongClick(PublishSubject<BookmarkModel> bookmarkLongClickSubject) {
        disposable.add(bookmarkLongClickSubject.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(bookmark -> {
            bookmarkViewModel.deleteBookmark(bookmark);
            Toast.makeText(this, "News removed from bookmark", Toast.LENGTH_LONG).show();
        }, e -> {
            Log.d(TAG, Objects.requireNonNull(e.getMessage()));
        }));
    }

    private void showErrorView() {
        binding.contentView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.GONE);
        binding.errorView.setVisibility(View.VISIBLE);
    }

    private void showContentView() {
        binding.errorView.setVisibility(View.GONE);
        binding.loadingView.setVisibility(View.GONE);
        binding.contentView.setVisibility(View.VISIBLE);
    }

    private void launchDetailsActivity(String url) {
        Intent intent = new Intent(BookmarkActivity.this, NewsDetailsActivity.class);
        intent.putExtra(WEB_URL, url);
        startActivity(intent);
    }

}
