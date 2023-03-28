package com.example.budgetbuddy.ui.budget;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentAddBudgetBinding;
import com.example.budgetbuddy.databinding.FragmentHomeBinding;
import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.repository.dao.IncomeDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;
import com.example.budgetbuddy.ui.home.HomeViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public class AddBudgetFragment extends Fragment {
    private FragmentAddBudgetBinding binding;
    EditText editTextBudget;
    Button btnCreateBudget;
    double totalBudgetValue = 0.0;
    double totalIncomeValue = 0.0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddBudgetViewModel ViewModel =
                new ViewModelProvider(this).get(AddBudgetViewModel.class);

        binding = FragmentAddBudgetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editTextBudget = binding.editTextBudget;
        btnCreateBudget = binding.btnCreateBudget;

        BudgetDao budgetDao = ServiceLocator.getInstance().getBudgetDao(getContext());

        ServiceLocator db = ServiceLocator.getInstance(getContext());
        List<Budget> budgetData = db.getBudgetDao(getContext()).getAll();
        List<Income> incomeData = db.getIncomeDao(getContext()).getAll();

        totalBudgetValue = budgetData.stream()
                .mapToDouble(Budget::getBudget)
                .sum();

        totalIncomeValue = incomeData.stream()
                .mapToDouble(Income::getIncome)
                .sum();

        btnCreateBudget.setOnClickListener(v -> {

            try {
                if(editTextBudget.getText().toString().equalsIgnoreCase("0")){
                    Toast.makeText(getContext(), "Budget cannot be 0 or empty", Toast.LENGTH_SHORT).show();

                }else {

                    if((totalBudgetValue + Double.parseDouble(editTextBudget.getText().toString())) > totalIncomeValue){
                        Toast.makeText(getContext(), "Add more funds to income", Toast.LENGTH_SHORT).show();
                    }else{
                        double budget = Double.parseDouble(editTextBudget.getText().toString());

                        //TODO Please use futures to handle work that needs to leave the main UI thread
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            new Thread(() -> budgetDao
                                    .insertAll(new Budget(budget)))
                                    .start();
                        }

                        Toast.makeText(getContext(), "Budget added successfully", Toast.LENGTH_LONG).show();
                        editTextBudget.setText("");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error entering budget", Toast.LENGTH_SHORT).show();
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
