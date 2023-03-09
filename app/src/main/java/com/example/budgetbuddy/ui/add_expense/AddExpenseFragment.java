package com.example.budgetbuddy.ui.add_expense;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentAddExpenseBinding;


public class AddExpenseFragment extends Fragment {

    private FragmentAddExpenseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddExpenseViewModel addExpenseViewModel =
                new ViewModelProvider(this).get(AddExpenseViewModel.class);

        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAddExpense;
        addExpenseViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}