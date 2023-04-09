package com.example.budgetbuddy.ui.allocation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.adapters.SummaryAdapter;
import com.example.budgetbuddy.databinding.FragmentAllocatedBinding;
import com.example.budgetbuddy.model.SummaryItem;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class AllocationFragment extends Fragment {
    TextView txtViewTotalExpenditure;
    TextView txtViewExpenditureTitle;
    TextView txtViewTotalBudgeted;
    TextView txtViewBudgetedTitle;
    RecyclerView recyclerView;
    AllocationViewModel allocationViewModel;
    private FragmentAllocatedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAllocatedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        txtViewTotalExpenditure = binding.txtTotalExpenditure;
        recyclerView = binding.recyclerViewStat;
        txtViewExpenditureTitle = binding.txtMonthlyExpenditureTitle;
        txtViewBudgetedTitle = binding.txtMonthlyBudgetedTitle;
        txtViewTotalBudgeted = binding.txtTotalBudgeted;
        allocationViewModel = new ViewModelProvider(this).get(AllocationViewModel.class);
        updateBudgetStatus();
        setupSummary();
        return root;
    }

    private void setupSummary() {
        allocationViewModel.getBudgetSummary().observe(getViewLifecycleOwner(), summaryItems -> {
            var sortedItems = summaryItems.stream().sorted(Comparator.comparing(a -> a.getCategory().toString())).collect(Collectors.toList());
            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(lm);
            SummaryAdapter adapter = new SummaryAdapter(sortedItems, getContext());
            recyclerView.setAdapter(adapter);
        });
    }

    public void updateBudgetStatus() {
        allocationViewModel.getBudgetSummary().observe(getViewLifecycleOwner(), summaryItems -> {
            if (summaryItems.isEmpty()) {
                txtViewTotalExpenditure.setVisibility(View.GONE);
                txtViewTotalBudgeted.setVisibility(View.GONE);
                txtViewBudgetedTitle.setVisibility(View.GONE);
                txtViewExpenditureTitle.setText(R.string.no_budget);
                return;
            }
            setTotalMonthlySpend(summaryItems);
        });
    }

    public void setTotalMonthlySpend(List<SummaryItem> summaryItems) {
        txtViewExpenditureTitle.setText(R.string.txtMonthlyExpenditure);
        txtViewTotalExpenditure.setVisibility(View.VISIBLE);
        txtViewTotalBudgeted.setVisibility(View.VISIBLE);
        txtViewBudgetedTitle.setVisibility(View.VISIBLE);
        var totalSpend = summaryItems.stream().map(SummaryItem::getActualSpend).reduce(0.0, Double::sum);
        var totalBudgeted = summaryItems.stream().map(SummaryItem::getBudgetAmount).reduce(0.0, Double::sum);
        txtViewTotalBudgeted.setText(totalBudgeted.toString());
        txtViewTotalExpenditure.setText(totalSpend.toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
