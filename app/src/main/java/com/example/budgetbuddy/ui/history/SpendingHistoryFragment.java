package com.example.budgetbuddy.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentSpendingHistoryBinding;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;
import com.example.budgetbuddy.ui.adapters.TransactionAdapter;

import java.util.List;

public class SpendingHistoryFragment extends Fragment {
    private FragmentSpendingHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSpendingHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btnExport = binding.btnExport;
        ListView listViewTransactionHistory = binding.listViewTransactionHistory;
        TransactionDao transactionDao = ServiceLocator.getInstance().getTransactionDao(getContext());
        List<Transaction> sortedTransactions = transactionDao.getAll();

        TransactionAdapter transactionAdapter = new TransactionAdapter(sortedTransactions);
        listViewTransactionHistory.setAdapter(transactionAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
