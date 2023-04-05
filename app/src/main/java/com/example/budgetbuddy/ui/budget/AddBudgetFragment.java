package com.example.budgetbuddy.ui.budget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.databinding.FragmentAddBudgetBinding;
import com.example.budgetbuddy.ui.adapters.BudgetRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AddBudgetFragment extends Fragment {
    List<Budget> budgets = new ArrayList<>();
    RecyclerView recyclerViewBudget;
    private FragmentAddBudgetBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddBudgetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewBudget =binding.recyclerViewBudget;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewBudget.setLayoutManager(layoutManager);
        BudgetRecyclerViewAdapter adapter = new BudgetRecyclerViewAdapter(budgets);
        recyclerViewBudget.setAdapter(adapter);
        AddData();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void AddData() {
        budgets.add(new Budget(101, "Housing", R.drawable.housing,0));
        budgets.add(new Budget(102, "Transportation", R.drawable.transportation,0));
        budgets.add(new Budget(103, "Food and Dining", R.drawable.feeding,0));
        budgets.add(new Budget(104, "Utilities", R.drawable.utilities,0));
        budgets.add(new Budget(105, "Insurance", R.drawable.insurance,0));
        budgets.add(new Budget(106, "Medical and Healthcare", R.drawable.medical,0));
        budgets.add(new Budget(104, "Personal Spending", R.drawable.personal_spending,0));
        budgets.add(new Budget(105, "Recreation and Entertainment", R.drawable.entertainment,0));
        budgets.add(new Budget(106, "Miscellaneous", R.drawable.miscellaneous,0));
    }
}
