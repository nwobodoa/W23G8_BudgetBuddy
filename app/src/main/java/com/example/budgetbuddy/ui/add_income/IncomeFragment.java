package com.example.budgetbuddy.ui.add_income;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Entity;

import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.databinding.FragmentAddIncomeBinding;
import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.Transaction;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;


//by Smart Egbuchulem (SmartGlaxx)
@Entity
public class IncomeFragment extends Fragment {
    EditText editTextIncome;
    EditText editTextDescription;
    EditText editTextDate;
    Button btnAddIncome;
    private FragmentAddIncomeBinding binding;
    MutableLiveData<Calendar> pickedTime = new MutableLiveData<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddIncomeViewModel addIncomeViewModel = new ViewModelProvider(this).get(AddIncomeViewModel.class);
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date").build();
        FragmentManager fm =((AppCompatActivity) requireActivity()).getSupportFragmentManager();

        binding = FragmentAddIncomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editTextIncome = binding.editTextIncome;
        editTextDescription = binding.editTextDescription;
        editTextDate = binding.editTextDate;
        btnAddIncome = binding.btnAddIncome;

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            pickedTime.setValue(calendar);
        });

        editTextDate.setOnClickListener(v -> {
            datePicker.show(fm, "MATERIAL_DATE_PICKER");
        });

        pickedTime.observe(getViewLifecycleOwner(), time -> {
            editTextDate.setText(
                    LocalDateConverter.fromLocalDate(LocalDateTime.ofInstant(time.toInstant(),time.getTimeZone().toZoneId()).toLocalDate()));
        });

        btnAddIncome.setOnClickListener(v -> {
                if(editTextIncome.getText().toString().equalsIgnoreCase("0") ||
                        editTextIncome.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Income cannot be 0 or empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editTextDescription.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(editTextDate.getText() == null || editTextDate.getText().toString().isEmpty() || editTextDate.getText().toString().isBlank()) {
                    Toast.makeText(getContext(), "Please enter a valid date", Toast.LENGTH_SHORT).show();
                    return;
                }

            double income = Double.parseDouble(editTextIncome.getText().toString());
            String description = editTextDescription.getText().toString();
            String incomeDate = editTextDate.getText().toString();
            Transaction tx = new Transaction(income, description, Category.INCOME, LocalDateConverter.fromString(incomeDate));
            MutableLiveData<List<Long>> txIds = new MutableLiveData<>();
            addIncomeViewModel.saveTransaction(tx, txIds);
            txIds.observe(getViewLifecycleOwner(), ids -> {
                if (!ids.isEmpty()) {
                    Toast.makeText(getContext(), "Income added successfully", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getContext(), "Income  unsuccessful", Toast.LENGTH_LONG).show();
            });
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
