package com.example.newsdigest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsdigest.models.RegisterModel;

public class RegisterViewModel extends ViewModel {

    MutableLiveData<RegisterModel> registerModelLiveData;

    RegisterViewModel() {
        registerModelLiveData = new MutableLiveData<>();
        init();
    }

    private void init() {

    }
}
