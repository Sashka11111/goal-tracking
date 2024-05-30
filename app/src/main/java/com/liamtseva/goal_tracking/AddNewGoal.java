package com.liamtseva.goal_tracking;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.liamtseva.goal_tracking.Model.GoalModel;
import com.liamtseva.goal_tracking.Utils.DatabaseHandler;

public class AddNewGoal extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newGoalText;
    private Button newGoalSaveButton;

    private DatabaseHandler db;

    public static AddNewGoal newInstance(){
        return new AddNewGoal();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.new_goal, container, false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newGoalText = requireView().findViewById(R.id.newGoalText);
        newGoalSaveButton = getView().findViewById(R.id.newGoalButton);

        boolean isUpdate = false;

        final Bundle bundle = getArguments();
        if(bundle != null){
            isUpdate = true;
            String goal = bundle.getString("goal");
            newGoalText.setText(goal);
            assert goal != null;
            if(goal.length()>0)
                newGoalSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
        }

        db = new DatabaseHandler(getActivity());
        db.openDatabase();

        newGoalText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newGoalSaveButton.setEnabled(false);
                    newGoalSaveButton.setTextColor(Color.GRAY);
                }
                else{
                    newGoalSaveButton.setEnabled(true);
                    newGoalSaveButton.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        final boolean finalIsUpdate = isUpdate;
        newGoalSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newGoalText.getText().toString();
                if(finalIsUpdate){
                    db.updateGoal(bundle.getInt("id"), text);
                }
                else {
                    GoalModel goal = new GoalModel();
                    goal.setGoal(text);
                    goal.setStatus(0);
                    db.insertGoal(goal);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof DialogCloseListener)
            ((DialogCloseListener) activity).handleDialogClose(dialog);
    }
}
