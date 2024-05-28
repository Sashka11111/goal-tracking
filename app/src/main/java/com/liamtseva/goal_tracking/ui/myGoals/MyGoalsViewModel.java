package com.liamtseva.goal_tracking.ui.myGoals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyGoalsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyGoalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}