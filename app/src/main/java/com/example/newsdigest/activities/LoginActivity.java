package com.example.newsdigest.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsdigest.R;
import com.example.newsdigest.databinding.LoginActivityBinding;
import com.example.newsdigest.di.DaggerSharedPrefComponent;
import com.example.newsdigest.di.SharedPrefModule;
import com.example.newsdigest.models.LoginModel;
import com.example.newsdigest.viewmodel.LoginViewModel;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class LoginActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sharedPreferences;

    private static final String TAG = LoginActivity.class.getName();
    private static final String USERNAME = "user_name";
    private static final String PASSWORD = "password";
    private static final String INVALID_USERNAME = "Invalid user name!";
    private static final String INVALID_PASSWORD = "Invalid password!";
    private static final String MANDATORY_FIELDS = "All fields are mandatory!";

    private LoginActivityBinding binding;
    private LoginViewModel loginViewModel;
    private CompositeDisposable disposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        setContentView(binding.getRoot());
        DaggerSharedPrefComponent.builder().sharedPrefModule(new SharedPrefModule(this)).
                build().inject(this);
        disposable = new CompositeDisposable();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel(loginViewModel);
        initListener();
    }

    private void observeViewModel(LoginViewModel loginViewModel) {
        loginViewModel.getLoginModelLiveData().observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(LoginModel loginModel) {
                loginViewModel.setData(loginModel);
            }
        });
    }

    private void initListener() {
        disposable.add(RxView.clicks(binding.btnLogin).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    validateInput();
                }, e -> {
                    Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                }));
    }

    private void validateInput() {
        String username = binding.tvUsername.getText().toString();
        String password = binding.tvPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, MANDATORY_FIELDS, Toast.LENGTH_LONG).show();
            return;
        }
        if (sharedPreferences.contains(USERNAME)) {
            if (!username.equals(sharedPreferences.getString(USERNAME, ""))) {
                Toast.makeText(this, INVALID_USERNAME, Toast.LENGTH_LONG).show();
                return;
            }
        }
        if (sharedPreferences.contains(PASSWORD)) {
            if (!password.equals(sharedPreferences.getString(PASSWORD, ""))) {
                Toast.makeText(this, INVALID_PASSWORD, Toast.LENGTH_LONG).show();
                return;
            }
        }
        launchLandingActivity();
    }

    private void launchLandingActivity() {
        Intent intent = new Intent(LoginActivity.this, LandingActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
