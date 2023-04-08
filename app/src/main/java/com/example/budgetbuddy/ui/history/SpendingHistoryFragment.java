package com.example.budgetbuddy.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentSpendingHistoryBinding;
import com.example.budgetbuddy.adapters.TransactionAdapter;

public class SpendingHistoryFragment extends Fragment {
    private FragmentSpendingHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSpendingHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btnExport = binding.btnExport;
        ListView listViewTransactionHistory = binding.listViewTransactionHistory;
        HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        historyViewModel.getAllTransactions()
                .observe(getViewLifecycleOwner(),
                        transactions -> listViewTransactionHistory
                                .setAdapter(new TransactionAdapter(transactions)));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
