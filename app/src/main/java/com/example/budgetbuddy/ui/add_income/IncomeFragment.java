package com.example.budgetbuddy.ui.add_income;


import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Entity;

import com.example.budgetbuddy.databinding.FragmentAddIncomeBinding;
import com.example.budgetbuddy.model.Income;
import com.example.budgetbuddy.repository.dao.IncomeDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



@Entity
public class IncomeFragment extends Fragment {
    EditText editTextIncome;
    EditText editTextDescription;
    EditText editTextDate;
    Button btnAddIncome;
    private FragmentAddIncomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddIncomeViewModel addIncomeViewModel =
                new ViewModelProvider(this).get(AddIncomeViewModel.class);

        binding = FragmentAddIncomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editTextIncome = binding.editTextIncome;
        editTextDescription = binding.editTextDescription;
        editTextDate = binding.editTextDate;
        btnAddIncome = binding.btnAddIncome;


        IncomeDao incomeDao = ServiceLocator.getInstance().getIncomeDao(getContext());

        editTextDate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8) {
                        clean = clean + ddmmyyyy.substring(clean.length());
                    } else {
                        int day = Integer.parseInt(clean.substring(0, 2));
                        int mon = Integer.parseInt(clean.substring(2, 4));
                        int year = Integer.parseInt(clean.substring(4, 8));

                        if (mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon - 1);
                        year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                        cal.set(Calendar.YEAR, year);

                        day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                        clean = String.format("%02d%02d%02d", day, mon, year);


                        Calendar currentDate = Calendar.getInstance();
                        currentDate.set(Calendar.HOUR_OF_DAY, 0);
                        currentDate.set(Calendar.MINUTE, 0);
                        currentDate.set(Calendar.SECOND, 0);
                        currentDate.set(Calendar.MILLISECOND, 0);
                        if (cal.after(currentDate)) {
                            editTextDate.setError("Invalid date");
                            editTextDate.requestFocus();
                            return;
                        }
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = Math.max(sel, 0);
                    current = clean;
                    editTextDate.setText(current);
                    editTextDate.setSelection(Math.min(sel, current.length()));
                }
            }
        });


        btnAddIncome.setOnClickListener(v -> {

            try {
                if(editTextIncome.getText().toString().equalsIgnoreCase("0") ||
                        editTextIncome.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Income cannot be 0 or empty", Toast.LENGTH_SHORT).show();
                }
                if(editTextDescription.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
                }
                if(editTextDate.getText().toString().equalsIgnoreCase("") ||
                        editTextDate.getText().toString().toLowerCase().contains("d") ||
                        editTextDate.getText().toString().toLowerCase().contains("m") ||
                        editTextDate.getText().toString().toLowerCase().contains("y")){
                    Toast.makeText(getContext(), "Please enter a valid date", Toast.LENGTH_SHORT).show();

                }else{
                    double income = Double.parseDouble(editTextIncome.getText().toString());
                    String description = editTextDescription.getText().toString();
                    String incomeDate = editTextDate.getText().toString();
                    //TODO Please use futures to handle work that needs to leave the main UI thread
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        new Thread(() -> incomeDao
                                .insertAll(new Income(income, description, LocalDate.parse(incomeDate, DateTimeFormatter.ofPattern("dd/M/yyyy")))))
                                .start();
                    }

                    Toast.makeText(getContext(), "Income added successfully", Toast.LENGTH_LONG).show();
                }
               } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error entering input", Toast.LENGTH_SHORT).show();
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