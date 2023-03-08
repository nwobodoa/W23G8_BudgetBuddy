package com.example.budgetbuddy.ui.add_income;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentAddIncomeBinding;



public class IncomeFragment extends Fragment {

    private FragmentAddIncomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddIncomeViewModel slideshowViewModel =
                new ViewModelProvider(this).get(AddIncomeViewModel.class);

        binding = FragmentAddIncomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAddIncome;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}