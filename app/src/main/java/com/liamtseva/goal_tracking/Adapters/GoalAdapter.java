package com.liamtseva.goal_tracking.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.liamtseva.goal_tracking.AddNewGoal;
import com.liamtseva.goal_tracking.Model.GoalModel;
import com.liamtseva.goal_tracking.R;
import com.liamtseva.goal_tracking.Utils.DatabaseHandler;

import java.util.List;

public class GoalAdapter extends RecyclerView.Adapter<GoalAdapter.ViewHolder> {

    private List<GoalModel> goalList;
    private DatabaseHandler db;
    private Context context;

    public GoalAdapter(DatabaseHandler db, Context context) {
        this.db = db;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.goal_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        db.openDatabase();

        final GoalModel item = goalList.get(position);
        holder.goal.setText(item.getGoal());
        holder.goal.setChecked(toBoolean(item.getStatus()));
        holder.goal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db.updateStatus(item.getId(), 1);
                } else {
                    db.updateStatus(item.getId(), 0);
                }
            }
        });
    }

    private boolean toBoolean(int n) {
        return n != 0;
    }

    @Override
    public int getItemCount() {
        return goalList.size();
    }

    public Context getContext() {
        return context;
    }

    public void setGoals(List<GoalModel> goalList) {
        this.goalList = goalList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        GoalModel item = goalList.get(position);
        db.deleteGoal(item.getId());
        goalList.remove(position);
        notifyItemRemoved(position);
    }

    public void editItem(int position) {
        GoalModel item = goalList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id", item.getId());
        bundle.putString("goal", item.getGoal());
        AddNewGoal fragment = new AddNewGoal();
        fragment.setArguments(bundle);
        fragment.show(((FragmentActivity) context).getSupportFragmentManager(), AddNewGoal.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox goal;

        ViewHolder(View view) {
            super(view);
            goal = view.findViewById(R.id.goalCheckBox);
        }
    }
}
