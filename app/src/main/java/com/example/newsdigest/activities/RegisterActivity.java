package com.example.newsdigest.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.newsdigest.R;
import com.example.newsdigest.databinding.RegisterActivityBinding;
import com.example.newsdigest.di.DaggerSharedPrefComponent;
import com.example.newsdigest.di.SharedPrefModule;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity  {

    private static final String TAG = RegisterActivity.class.getName();

    private RegisterActivityBinding binding;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register_activity);
        setContentView(binding.getRoot());
        DaggerSharedPrefComponent.builder().sharedPrefModule(new SharedPrefModule(this)).
                build().inject(this);
    }
}
