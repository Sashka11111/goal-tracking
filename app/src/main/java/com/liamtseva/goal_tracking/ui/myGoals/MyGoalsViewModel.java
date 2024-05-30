package com.liamtseva.goal_tracking.ui.myGoals;// MyGoalsViewModel.java
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.liamtseva.goal_tracking.Model.GoalModel;

import java.util.List;

public class MyGoalsViewModel extends ViewModel {
    private MutableLiveData<List<GoalModel>> goalsList;

    public MyGoalsViewModel() {
        goalsList = new MutableLiveData<>();
        // Ініціалізуйте goalsList або завантажте дані за замовчуванням
    }

    public LiveData<List<GoalModel>> getGoalsList() {
        return goalsList;
    }

    public void updateGoalsList(List<GoalModel> goals) {
        goalsList.setValue(goals);
    }
}
