package com.example.budgetbuddy.ui.allocation;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.adapters.LineItemViewAdapter;
import com.example.budgetbuddy.databinding.FragmentAddAllocationBinding;


@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class AddAllocationFragment extends Fragment {

    RecyclerView recyclerView;
    private FragmentAddAllocationBinding binding;

    AllocationViewModel allocationViewModel;

    Button button;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAddAllocationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerViewBudgetLineItem;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        allocationViewModel = new ViewModelProvider(this).get(AllocationViewModel.class);
        recyclerView.setLayoutManager(layoutManager);
        setRecyclerView();
        return root;
    }

    private void setRecyclerView() {
        allocationViewModel.getBudget().observe(getViewLifecycleOwner(), budget -> {
            allocationViewModel.getLineItems(budget).observe(getViewLifecycleOwner(), data -> {
                LineItemViewAdapter adapter = new LineItemViewAdapter(data);
                recyclerView.setAdapter(adapter);
                button = binding.btnAddAllocation;
                button.setOnClickListener(view -> saveBudget(adapter));
            });
        });

    }

    public void saveBudget(LineItemViewAdapter adapter) {
        boolean allZeroes = adapter.getFilledLineItems().stream().allMatch(lineItem -> lineItem.getAmount() == 0.0);
        boolean hasNegative = adapter.getFilledLineItems().stream().anyMatch(lineItem -> lineItem.getAmount() < 0.0);
        if (allZeroes) {
            Toast.makeText(getContext(),"Please provide at least one value for any line item", Toast.LENGTH_SHORT).show();
            return;
        }

        if (hasNegative) {
            Toast.makeText(getContext(),"Negative budget items are not allowed", Toast.LENGTH_SHORT).show();
            return;
        }
        allocationViewModel.saveLineItems(adapter.getFilledLineItems());
        allocationViewModel.getSavedLineItemIds().observe(getViewLifecycleOwner(), ids -> {
            if(ids.isEmpty()) {
                Toast.makeText(getContext(),"Transaction save failed", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(),"Transaction saved successfully", Toast.LENGTH_SHORT).show();

        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
