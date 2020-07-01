package com.example.newsdigest.activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsdigest.R;
import com.example.newsdigest.databinding.RegisterActivityBinding;
import com.example.newsdigest.di.DaggerSharedPrefComponent;
import com.example.newsdigest.di.SharedPrefModule;
import com.example.newsdigest.models.RegisterModel;
import com.example.newsdigest.viewmodel.RegisterViewModel;

import javax.inject.Inject;

public class RegisterActivity extends AppCompatActivity  {

    private static final String TAG = RegisterActivity.class.getName();

    private RegisterActivityBinding binding;
    private RegisterViewModel registerViewModel;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register_activity);
        setContentView(binding.getRoot());
        DaggerSharedPrefComponent.builder().sharedPrefModule(new SharedPrefModule(this)).
                build().inject(this);
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        observeViewModel(registerViewModel);
        initListener();
    }

    private void observeViewModel(RegisterViewModel registerViewModel) {
        registerViewModel.getRegisterModel().observe(this, new Observer<RegisterModel>() {
            @Override
            public void onChanged(RegisterModel registerModel) {
                registerViewModel.setData(registerModel);
            }
        });
    }

    private void initListener() {

    }
}
