package com.example.newsdigest.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.newsdigest.R;
import com.example.newsdigest.databinding.LoginActivityBinding;
import com.example.newsdigest.di.DaggerSharedPrefComponent;
import com.example.newsdigest.di.SharedPrefModule;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    @Inject
    SharedPreferences sharedPreferences;

    private static final String TAG = LoginActivity.class.getName();

    private LoginActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        setContentView(binding.getRoot());
        DaggerSharedPrefComponent.builder().sharedPrefModule(new SharedPrefModule(this)).
                build().inject(this);
        initListener();
    }

    private void initListener() {

    }
}
