package com.example.budgetbuddy.ui.add_expense;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.converter.LocalDateConverter;
import com.example.budgetbuddy.databinding.FragmentAddExpenseBinding;
import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.adapters.CategoryRecyclerViewAdapter;
import com.example.budgetbuddy.utils.VectorDrawableUtils;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class ExpenseFragment extends Fragment {

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
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select date").build();
        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewCategories = binding.recyclerViewCategories;
        editTextExpense = binding.editTextExpense;
        editTextExpenseDescription = binding.editTextExpenseDescription;
        btnAddExpense = binding.btnAddExpense;
        editTextCategory = binding.editTextCategory;
        editTextExpenseDate = binding.editTextExpenseDate;
        FragmentManager fm =((AppCompatActivity) requireActivity()).getSupportFragmentManager();

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
        List<Category> categories = Category.getAllCategories();
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(categories, i -> {
            editTextCategory.setText(categories.get(i).toString());
            int selectedImage = categories.get(i).getDrawableId();
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

        btnAddExpense.setOnClickListener(v -> addExpenseListener(addExpenseViewModel));
        btnAddExpense.setEnabled(false);

        addExpenseViewModel.getExpenseFormState().observe(getViewLifecycleOwner(), this::expenseFormStateListener);
        TextWatcher afterTextChangedListener = setUpTextWatcher(editTextExpenseDescription,editTextCategory,editTextExpense,editTextExpenseDate,addExpenseViewModel);

        editTextExpenseDescription.addTextChangedListener(afterTextChangedListener);
        editTextCategory.addTextChangedListener(afterTextChangedListener);
        editTextExpense.addTextChangedListener(afterTextChangedListener);
        editTextExpenseDate.addTextChangedListener(afterTextChangedListener);

        return root;
    }

    private void expenseFormStateListener(ExpenseFormState expenseFormState) {
        if(expenseFormState == null) {
            return;
        }
        btnAddExpense.setEnabled(expenseFormState.isDataValid());
        if(expenseFormState.getExpenseError() != null){
            editTextExpense.setError(getString(expenseFormState.getExpenseError()));
        }
        if(expenseFormState.getCategoryError() != null) {
            editTextCategory.setError(getString(expenseFormState.getCategoryError()));
        }
        if(expenseFormState.getDescriptionError() != null) {
            editTextExpenseDescription.setError(getString(expenseFormState.getDescriptionError()));
        }
        if(expenseFormState.getExpenseDateError() != null) {
            editTextExpenseDate.setError(getString(expenseFormState.getExpenseDateError()));
        }
    }

    private void addExpenseListener(ExpenseViewModel addExpenseViewModel) {
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



    public TextWatcher setUpTextWatcher(EditText editTextExpenseDescription,
                                        EditText editTextCategory,
                                        EditText editTextExpense,
                                        EditText editTextExpenseDate,
                                        ExpenseViewModel expenseViewModel) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }
            @Override
            public void afterTextChanged(Editable s) {
                expenseViewModel.addExpenseTextChanged(
                        editTextExpenseDescription.getText().toString(),
                         editTextCategory.getText().toString(),
                         editTextExpense.getText().toString(),
                        editTextExpenseDate.getText().toString());
            }
        };
    }


    public void clearInput() {
        editTextExpenseDate.setText(null);
        editTextExpenseDescription.setText(null);
        editTextCategory.setText(null);
        editTextExpenseDate.setText(null);
        editTextExpense.setText(null);

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
