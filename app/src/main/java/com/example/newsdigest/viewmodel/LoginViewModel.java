package com.example.newsdigest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsdigest.models.LoginModel;

public class LoginViewModel extends ViewModel {

    final MutableLiveData<LoginModel> loginModelLiveData;

    public LoginViewModel() {
        loginModelLiveData = new MutableLiveData<>();
    }

    public void setData(LoginModel loginModel) {
        loginModelLiveData.setValue(loginModel);
    }

    public MutableLiveData<LoginModel> getLoginModelLiveData() {
        return loginModelLiveData;
    }
}
