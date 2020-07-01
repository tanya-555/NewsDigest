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
import com.example.newsdigest.databinding.RegisterActivityBinding;
import com.example.newsdigest.di.DaggerSharedPrefComponent;
import com.example.newsdigest.di.SharedPrefModule;
import com.example.newsdigest.models.RegisterModel;
import com.example.newsdigest.viewmodel.RegisterViewModel;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getName();
    private static final String USERNAME = "user_name";
    private static final String PASSWORD = "password";

    private RegisterActivityBinding binding;
    private RegisterViewModel registerViewModel;
    private CompositeDisposable disposable;

    @Inject
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.register_activity);
        setContentView(binding.getRoot());
        disposable = new CompositeDisposable();
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
        disposable.add(RxView.clicks(binding.btnRegister).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    validateInput();
                }, e -> {
                    Log.d(TAG, Objects.requireNonNull(e.getMessage()));
                }));
    }

    private void validateInput() {
        String username = binding.tvUsername.getText().toString();
        String password = binding.tvPassword.getText().toString();
        String confirmPassword = binding.tvConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "All field are mandatory!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_LONG).show();
            return;
        }
        saveDetailsInPreferences(username, password);
        launchLoginActivity();
    }

    private void saveDetailsInPreferences(String username, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
