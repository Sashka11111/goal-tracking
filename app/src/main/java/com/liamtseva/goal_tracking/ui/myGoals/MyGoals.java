package com.liamtseva.goal_tracking.ui.myGoals;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.liamtseva.goal_tracking.Adapters.GoalAdapter;
import com.liamtseva.goal_tracking.AddNewGoal;
import com.liamtseva.goal_tracking.DialogCloseListener;
import com.liamtseva.goal_tracking.Model.GoalModel;
import com.liamtseva.goal_tracking.R;
import com.liamtseva.goal_tracking.RecyclerItemTouchHelper;
import com.liamtseva.goal_tracking.Utils.DatabaseHandler;
import com.liamtseva.goal_tracking.databinding.MyGoalsBinding;

import java.util.Collections;
import java.util.List;

public class MyGoals extends Fragment implements DialogCloseListener {

    private MyGoalsBinding binding;
    private DatabaseHandler db;
    private RecyclerView goalsRecyclerView;
    private GoalAdapter goalsAdapter;
    private Button fab;
    private List<GoalModel> goalList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = MyGoalsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        goalsRecyclerView = binding.goalsRecyclerView;
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        goalsAdapter = new GoalAdapter(db, getActivity());
        goalsRecyclerView.setAdapter(goalsAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(goalsAdapter));
        itemTouchHelper.attachToRecyclerView(goalsRecyclerView);

        fab = binding.fab;

        goalList = db.getAllGoals();
        Collections.reverse(goalList);

        goalsAdapter.setGoals(goalList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewGoal.newInstance().show(getParentFragmentManager(), AddNewGoal.TAG);
            }
        });
    }

    @Override
    public void handleDialogClose(DialogInterface dialog) {
        goalList = db.getAllGoals();
        Collections.reverse(goalList);
        goalsAdapter.setGoals(goalList);
        goalsAdapter.notifyDataSetChanged();
    }
}
