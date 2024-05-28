package com.liamtseva.goal_tracking.ui.myGoals;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.liamtseva.goal_tracking.databinding.MyGoalsBinding;


public class MyGoals extends Fragment {

private MyGoalsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        MyGoalsViewModel myGoalsViewModel =
                new ViewModelProvider(this).get(MyGoalsViewModel.class);

    binding = MyGoalsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textHome;
        myGoalsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}