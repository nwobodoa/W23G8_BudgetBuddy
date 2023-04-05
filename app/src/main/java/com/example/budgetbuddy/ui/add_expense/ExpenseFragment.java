package com.example.budgetbuddy.ui.add_expense;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.databinding.FragmentAddExpenseBinding;
import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.ui.adapters.CategoryRecyclerViewAdapter;
import com.example.budgetbuddy.utils.VectorDrawableUtils;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ExpenseFragment extends Fragment {
    List<CategoryIcon> categories = new ArrayList<>();
    EditText editTextExpense;
    RecyclerView recyclerViewCategories;
    EditText editTextExpenseDescription;
    EditText editTextExpenseDate;

    EditText editTextCategory;
    Button btnAddExpense;

    MutableLiveData<Calendar> pickedTime = new MutableLiveData<>();

    private FragmentAddExpenseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExpenseViewModel addExpenseViewModel =
                new ViewModelProvider(this).get(ExpenseViewModel.class);
        var datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date").build();
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewCategories = binding.recyclerViewCategories;
        editTextExpense = binding.editTextExpense;
        editTextExpenseDescription = binding.editTextExpenseDescription;
        btnAddExpense = binding.btnAddExpense;
        editTextCategory = binding.editTextCategory;
        editTextExpenseDate = binding.editTextExpenseDate;
        var fm =((AppCompatActivity) requireActivity()).getSupportFragmentManager();
        AddData();
        editTextCategory.setInputType(InputType.TYPE_NULL);
        editTextCategory.setOnClickListener(view -> {
            Log.d("MyActivity", "EditText clicked");
            recyclerViewCategories.setVisibility(View.VISIBLE);
        });

        editTextCategory.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                recyclerViewCategories.setVisibility(View.GONE);
            }
        });

        pickedTime.observe(getViewLifecycleOwner(), time -> {
            editTextExpenseDate.setText(
                    LocalDateConverter.fromLocalDate(LocalDateTime.ofInstant(time.toInstant(),time.getTimeZone().toZoneId()).toLocalDate()));
        });

        GridLayoutManager gm = new GridLayoutManager(getContext(), 3);
        recyclerViewCategories.setLayoutManager(gm);
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(categories, i -> {
            editTextCategory.setText(categories.get(i).getCategoryName());
            int selectedImage = categories.get(i).getCategoryPic();
            Bitmap bitmap = VectorDrawableUtils.getBitmapFromVectorDrawable(requireContext(),selectedImage);
            int desiredWidth = 55;
            int desiredHeight = 55;
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, false);
            Drawable drawable = new BitmapDrawable(getResources(), scaledBitmap);
            editTextCategory.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            recyclerViewCategories.setVisibility(View.GONE);
        });

        recyclerViewCategories.setAdapter(categoryRecyclerViewAdapter);

        datePicker.addOnPositiveButtonClickListener(selection -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            calendar.setTimeInMillis(selection);
            pickedTime.setValue(calendar);
        });

        editTextExpenseDate.setOnClickListener(v -> {
            datePicker.show(fm, "MATERIAL_DATE_PICKER");
        });



        btnAddExpense.setOnClickListener(v -> {
            if (isValidInput()) {
                double expense = Double.parseDouble(editTextExpense.getText().toString());
                String description = editTextExpenseDescription.getText().toString();
                String expenseDate = editTextExpenseDate.getText().toString();
                MutableLiveData<List<Long>> savedIds = new MutableLiveData<>();
                addExpenseViewModel.insert(savedIds,
                        new Transaction(-1 * expense,
                                description,
                                Category.valueOfLabel(editTextCategory.getText().toString().toLowerCase()),
                                LocalDateConverter.fromString(expenseDate)));
                savedIds.observe(getViewLifecycleOwner(), ids -> {
                    if(!ids.isEmpty()) {
                        Toast.makeText(getContext(), "Expense added successfully", Toast.LENGTH_LONG).show();
                        clearInput();
                    }
                });
            }
        });

        return root;
    }

    private void AddData() {
        categories.add(new CategoryIcon(103, Category.DINING_OUT.toString(), R.drawable.utensils_solid));
        categories.add(new CategoryIcon(101, Category.SHOPPING.toString(), R.drawable.basket_shopping_solid));
        categories.add(new CategoryIcon(102, Category.TRAVEL.toString(), R.drawable.bus_simple_solid));
        categories.add(new CategoryIcon(106, Category.CASH.toString(), R.drawable.cash_wave_solid));
        categories.add(new CategoryIcon(101, Category.HOME.toString(), R.drawable.house_solid));
        categories.add(new CategoryIcon(106, Category.HEALTH.toString(), R.drawable.medical_solid));
        categories.add(new CategoryIcon(105, Category.ENTERTAINMENT.toString(), R.drawable.film_solid));
        categories.add(new CategoryIcon(104, Category.EDUCATION.toString(), R.drawable.education_solid));
        categories.add(new CategoryIcon(104, Category.UTILITIES.toString(), R.drawable.utilities_solid));
        categories.add(new CategoryIcon(106, Category.MISCELLANEOUS.toString(), R.drawable.miscellaneous));
    }


    public void clearInput() {
        editTextExpenseDate.setText(null);
        editTextExpenseDescription.setText(null);
        editTextCategory.setText(null);
        editTextExpenseDate.setText(null);
        editTextExpense.setText(null);
    }

    public boolean isValidInput() {
        if (editTextExpense.getText().toString().equalsIgnoreCase("0") ||
                editTextExpense.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Income cannot be 0 or empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextExpenseDescription.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextExpenseDate.getText().toString().equalsIgnoreCase("") ||
                editTextExpenseDate.getText().toString().toLowerCase().contains("d") ||
                editTextExpenseDate.getText().toString().toLowerCase().contains("m") ||
                editTextExpenseDate.getText().toString().toLowerCase().contains("y")) {
            Toast.makeText(getContext(), "Please enter a valid date", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
