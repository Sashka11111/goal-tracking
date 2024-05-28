package com.liamtseva.goal_tracking.ui.advice;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AdviceViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AdviceViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}