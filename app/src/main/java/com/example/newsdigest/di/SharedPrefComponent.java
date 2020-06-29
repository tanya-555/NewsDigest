package com.example.newsdigest.di;

import com.example.newsdigest.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {SharedPrefModule.class})
public interface SharedPrefComponent {

    void inject(MainActivity mainActivity);
}
