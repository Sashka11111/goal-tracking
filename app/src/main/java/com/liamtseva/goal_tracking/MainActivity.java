package com.liamtseva.goal_tracking;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.liamtseva.goal_tracking.databinding.ActivityMainBinding;
import com.liamtseva.goal_tracking.Adapters.GoalAdapter;
import com.liamtseva.goal_tracking.AddNewGoal;
import com.liamtseva.goal_tracking.DialogCloseListener;
import com.liamtseva.goal_tracking.Model.GoalModel;
import com.liamtseva.goal_tracking.Utils.DatabaseHandler;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DialogCloseListener {

    private ActivityMainBinding binding;
    private DatabaseHandler db;
    private RecyclerView goalsRecyclerView;
    private GoalAdapter goalsAdapter;
    private FloatingActionButton fab;
    private List<GoalModel> goalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseHandler(this);
        db.openDatabase();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_myGoals, R.id.navigation_addGoals, R.id.navigation_advice)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        goalsRecyclerView = findViewById(R.id.goalsRecyclerView);
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        goalsAdapter = new GoalAdapter(db, this);
        goalsRecyclerView.setAdapter(goalsAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(goalsAdapter));
        itemTouchHelper.attachToRecyclerView(goalsRecyclerView);

        fab = findViewById(R.id.fab);

        goalList = db.getAllGoals();
        Collections.reverse(goalList);

        goalsAdapter.setGoals(goalList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewGoal.newInstance().show(getSupportFragmentManager(), AddNewGoal.TAG);
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
