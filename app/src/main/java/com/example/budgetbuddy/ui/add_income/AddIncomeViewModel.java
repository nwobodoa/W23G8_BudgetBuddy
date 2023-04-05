package com.example.budgetbuddy.ui.add_income;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.respository.TransactionRepository;

import java.util.List;

public class AddIncomeViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mText;
    private final TransactionRepository transactionRepository;

    public AddIncomeViewModel(Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is add income fragment");
        transactionRepository = new TransactionRepository(application);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void saveTransaction(Transaction tx, MutableLiveData<List<Long>> txIds) {
        transactionRepository.insert(txIds,tx);
    }
}
