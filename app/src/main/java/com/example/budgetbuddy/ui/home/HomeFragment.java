package com.example.budgetbuddy.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentHomeBinding;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;
import com.example.budgetbuddy.ui.adapters.TransactionAdapter;

import java.util.List;

public class HomeFragment extends Fragment {
    ListView listViewBudgetTransaction;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listViewBudgetTransaction = binding.listViewBudgetTransaction;
        TransactionDao transactionDao = ServiceLocator.getInstance(getContext()).getTransactionDao(getContext());
        List<Transaction> sortedTransactions = transactionDao.getAll();

        TransactionAdapter transactionAdapter = new TransactionAdapter(sortedTransactions);
        listViewBudgetTransaction.setAdapter(transactionAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}