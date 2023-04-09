package com.example.budgetbuddy.ui.add_expense;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.util.List;

import static android.content.ContentValues.TAG;

public class ExpenseViewModel extends AndroidViewModel {
    private final TransactionRepository mRepository;
    private final MutableLiveData<ExpenseFormState> expenseFormState = new MutableLiveData<>();

    public ExpenseViewModel(Application application) {
        super(application);
        mRepository = new TransactionRepository(application);

    }

    public LiveData<List<Transaction>> getTransactions() {
        return mRepository.getAllTransactions();
    }

    public void insert(MutableLiveData<List<Long>> savedTransactionIds, Transaction... transactions) {
        mRepository.insert(savedTransactionIds, transactions);
    }

    private boolean isValidExpense(String expense) {
        try {
            double expDouble = Double.parseDouble(expense);
            return expDouble > 0.0;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isValidDate(String date) {
        try {
            LocalDateConverter.fromString(date);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "isValidDate: " + false);
            Log.d(TAG, "isValidDate: " + e.getMessage());
            return false;
        }
    }

    public void addExpenseTextChanged(String description, String category, String expense, String expenseDate) {
        if (!isValidExpense(expense)) {
            expenseFormState.postValue(
                    new ExpenseFormState(R.string.expense_error, null, null, null, false)
            );
            return;
        }
        if (description.isBlank() || description.length() > 25) {
            expenseFormState.postValue(
                    new ExpenseFormState(null, R.string.expense_description_error, null, null, false)
            );
            return;
        }


        if (!isValidDate(expenseDate)) {
            expenseFormState.postValue(
                    new ExpenseFormState(null, null, null, R.string.expense_date_error, false)
            );
            return;
        }
        expenseFormState.setValue(new ExpenseFormState(true));
    }

    public MutableLiveData<ExpenseFormState> getExpenseFormState() {
        return expenseFormState;
    }

}
