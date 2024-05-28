package com.liamtseva.goal_tracking.ui.addGoals;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddGoalsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AddGoalsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}