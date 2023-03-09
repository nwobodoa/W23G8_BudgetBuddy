package com.example.budgetbuddy.ui.spendingbycat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentAddExpenseBinding;
import com.example.budgetbuddy.databinding.FragmentSpendingByCatBinding;
import com.example.budgetbuddy.ui.add_expense.AddExpenseViewModel;

public class SpendingByCategoryFragment extends Fragment {
    private FragmentSpendingByCatBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SpendingByCatViewModel addExpenseViewModel =
                new ViewModelProvider(this).get(SpendingByCatViewModel.class);

        binding = FragmentSpendingByCatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.txtCat;
        addExpenseViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
