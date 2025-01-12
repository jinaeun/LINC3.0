package com.cookandroid.linc_sadaju.ui.mypage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPageViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}