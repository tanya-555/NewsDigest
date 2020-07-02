package com.example.newsdigest.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsdigest.R;
import com.example.newsdigest.adapter.NewsAdapter;
import com.example.newsdigest.databinding.LandingActivityBinding;
import com.example.newsdigest.di.DaggerSharedPrefComponent;
import com.example.newsdigest.di.SharedPrefModule;
import com.example.newsdigest.models.NewsList;
import com.example.newsdigest.models.SearchResponse;
import com.example.newsdigest.viewmodel.NewsViewModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class LandingActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sharedPreferences;

    private static final String WEB_URL = "web_url";
    private static final String IS_LOGGED_IN = "is_logged_in";
    private static final String LOGGED_OUT = "Successfully logged out";
    private static final String MANDATORY_FIELDS = "All fields are mandatory!";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "user_name";
    private static final String INVALID_CURRENT_PASSWORD = "Current password is invalid!";
    private static final String RESET_SUCCESS = "Password reset successfully!";
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
        DaggerSharedPrefComponent.builder().sharedPrefModule(new SharedPrefModule(this)).
                build().inject(this);
        newsList = new ArrayList<>();
        setupNavigationDrawer();
        setNavUsername();
        disposable = new CompositeDisposable();
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        observeViewModel(newsViewModel);
        showLoadingView();
        initRecyclerView();
    }

    private void observeViewModel(NewsViewModel newsViewModel) {
        newsViewModel.getNewsList().observe(this, searchResponse -> {
            binding.loadingView.setVisibility(View.GONE);
            setData(searchResponse);
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
        binding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.reset:
                        resetPassword();
                        break;
                    case R.id.bookmark:
                        break;
                    case R.id.logout:
                        exitLandingScreen();
                        break;
                }
                return true;
            }
        });
    }

    private void exitLandingScreen() {
        if (sharedPreferences.contains(IS_LOGGED_IN)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(IS_LOGGED_IN, false);
            editor.apply();
            Toast.makeText(this, LOGGED_OUT, Toast.LENGTH_LONG).show();
            launchLoginActivity();
            finish();
        }
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void resetPassword() {
        showResetPasswordDialog();
    }

    private void showResetPasswordDialog() {
        Dialog dialog = new Dialog(Objects.requireNonNull(this));
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.reset_password_dialog);
        Button saveBtn = dialog.findViewById(R.id.btn_save);
        saveBtn.setOnClickListener(v -> {
            String currentPassword = ((EditText)dialog.findViewById(R.id.et_current_password)).getText().toString();
            String newPassword = ((EditText)dialog.findViewById(R.id.et_new_password)).getText().toString();
            if(validateInput(currentPassword, newPassword)) {
                saveNewPassword(newPassword);
                Toast.makeText(this, RESET_SUCCESS, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean validateInput(String currentPassword, String newPassword) {
        if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword)) {
            Toast.makeText(this, MANDATORY_FIELDS, Toast.LENGTH_LONG).show();
            return false;
        }
        if (!currentPassword.equals(sharedPreferences.getString(PASSWORD, ""))) {
            Toast.makeText(this, INVALID_CURRENT_PASSWORD, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void saveNewPassword(String newPassword) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, newPassword);
        editor.apply();
    }

    private void setNavUsername() {
        View navUser = binding.navigationView.getHeaderView(0);
        TextView username = navUser.findViewById(R.id.username);
        if(sharedPreferences.contains(USERNAME)) {
            username.setText(sharedPreferences.getString(USERNAME, ""));
        }
    }
}
