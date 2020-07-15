package com.example.newsdigest.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.newsdigest.R;
import com.example.newsdigest.adapter.BookmarkAdapter;
import com.example.newsdigest.databinding.BookmarkActivityBinding;
import com.example.newsdigest.models.BookmarkModel;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class BookmarkActivity extends AppCompatActivity {

    private static final String TAG = BookmarkActivity.class.getName();
    private static final String BOOKMARK_LIST = "bookmark_list";

    private List<BookmarkModel> bookmarkModelList;
    private BookmarkActivityBinding binding;
    private ImageView backBtn;
    private CompositeDisposable disposable;
    private BookmarkAdapter adapter;

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
        showLoadingView();
        initListener();
        initRecyclerView();
        showBookmarks();
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
        }
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

}
