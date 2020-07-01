package com.example.newsdigest.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsdigest.models.RegisterModel;

public class RegisterViewModel extends ViewModel {

    final MutableLiveData<RegisterModel> registerModelLiveData;

    public RegisterViewModel() {
        registerModelLiveData = new MutableLiveData<>();
    }

    public void setData(RegisterModel registerModel) {
        registerModelLiveData.setValue(registerModel);
    }

    public MutableLiveData<RegisterModel> getRegisterModel() {
        return  registerModelLiveData;
    }
}
