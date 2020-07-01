package com.example.newsdigest.di;

import com.example.newsdigest.MainActivity;
import com.example.newsdigest.activities.LoginActivity;
import com.example.newsdigest.activities.RegisterActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SharedPrefModule.class})
public interface SharedPrefComponent {

    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);
}
