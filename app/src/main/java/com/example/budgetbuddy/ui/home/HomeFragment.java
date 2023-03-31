package com.example.budgetbuddy.ui.home;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentHomeBinding;
import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.Expense;
import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;
import com.example.budgetbuddy.ui.adapters.TransactionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment {
    List<Income> IncomeList = new ArrayList<Income>();
    ListView listViewBudgetTransaction;
    TextView textViewBudget;
    TextView textViewBudgetBalanceValue;
    TextView textViewIncome;
    TextView textViewIncomeBalanceValue;
    TextView textViewExpense;


    List<Budget> budgets = new ArrayList<>();
    List<Income> incomes = new ArrayList<>();
    List<Expense> expenses = new ArrayList<>();

    double totalBudgetValue = 0.0;
    double budgetBalance = 0.0;
    double totalIncomeValue = 0.0;
    double incomeBalance = 0.0;
    double totalExpenseValue = 0.0;


    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        listViewBudgetTransaction = binding.listViewBudgetTransaction;
//        TransactionDao transactionDao = ServiceLocator.getInstance(getContext()).getTransactionDao(getContext());
//        List<Transaction> sortedTransactions = transactionDao.getAll();
//
//        TransactionAdapter transactionAdapter = new TransactionAdapter(sortedTransactions);
//        listViewBudgetTransaction.setAdapter(transactionAdapter);
//


        listViewBudgetTransaction = binding.listViewBudgetTransaction;
        textViewBudget = binding.textViewBudget;
        textViewBudgetBalanceValue = binding.textViewBudgetBalanceValue;
        textViewIncome = binding.textViewIncome;
        textViewIncomeBalanceValue = binding.textViewIncomeBalanceValue;
        textViewExpense = binding.textViewExpense;
//        listViewBudgetReportExpense = binding.listViewBudgetReportExpense;

        ServiceLocator db = ServiceLocator.getInstance(getContext());

        List<Income> incomeData = db.getIncomeDao(getContext()).getAll();
        List<Expense> expenseData = db.getExpenseDao(getContext()).getAll();
        List<Budget> budgetData = db.getBudgetDao(getContext()).getAll();


        List<Transaction> transactionData = new ArrayList<>();

        for (Income income : incomeData) {
            transactionData.add(new Transaction(income.getIncome(), income.getDescription(), income.getIncomeDate()));
        }
        for (Expense expense : expenseData) {
            transactionData.add(new Transaction(-expense.getExpense(), expense.getCategory(), expense.getExpenseDate()));
        }


        List<Transaction> sortedTransactions = transactionData.stream()
                .sorted((t1, t2) -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        return t1.getTransactionDate().compareTo(t2.getTransactionDate());
                    } else {
                        return 0;
                    }
                })
                .collect(Collectors.toList());

        TransactionAdapter transactionAdapter = new TransactionAdapter(sortedTransactions);




//        IncomeAdapter incomeBudgetAdapter = new IncomeAdapter(incomeData);
//        ExpenseAdapter expenseBudgetAdapter = new ExpenseAdapter(expenseData);
//
//
//        listViewBudgetReportIncome.setAdapter(incomeBudgetAdapter);
        listViewBudgetTransaction.setAdapter(transactionAdapter);

        totalBudgetValue = budgetData.stream()
                .mapToDouble(Budget::getBudget)
                .sum();

        totalIncomeValue = incomeData.stream()
                .mapToDouble(Income::getIncome)
                .sum();

        totalExpenseValue = expenseData.stream()
                .mapToDouble(Expense::getExpense)
                .sum();

        budgetBalance = totalBudgetValue - totalExpenseValue;
        incomeBalance = totalIncomeValue - totalExpenseValue;

        String totalBudgetValueFormated = String.format("%.2f", totalBudgetValue);
        String totalIncomeValueFormated = String.format("%.2f", totalIncomeValue);
        String totalExpenseValueFormated = String.format("%.2f", totalExpenseValue);

        textViewBudget.setText("$"+totalBudgetValueFormated);
        textViewIncome.setText("$"+totalIncomeValueFormated);
        textViewExpense.setText("-$"+totalExpenseValueFormated);
        if(budgetBalance < 0){
            String hexColor = "#86042F";
            int color = Color.parseColor(hexColor);
            textViewBudgetBalanceValue.setTextColor(color);
        }else {
            String hexColor = "#FF669900";
            int color = Color.parseColor(hexColor);
            textViewBudgetBalanceValue.setTextColor(color);
        }
        if(incomeBalance < 0){
            String hexColor = "#86042F";
            int color = Color.parseColor(hexColor);
            textViewIncomeBalanceValue.setTextColor(color);
        }else {
            textViewIncomeBalanceValue.setTextColor(Color.BLUE);
        }
        textViewBudgetBalanceValue.setText(""+budgetBalance);
        textViewIncomeBalanceValue.setText(""+incomeBalance);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}