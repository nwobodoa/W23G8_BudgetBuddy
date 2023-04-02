package com.example.budgetbuddy.ui.add_expense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.databinding.FragmentAddExpenseBinding;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;
import com.example.budgetbuddy.ui.adapters.CategoryRecyclerViewAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//by Smart Egbuchulem (SmartGlaxx)
public class ExpenseFragment extends Fragment {


    List<Category> categories = new ArrayList<>();
    EditText editTextExpense;
    RecyclerView recyclerViewCategories;
    EditText editTextExpenseDescription;
    EditText editTextExpenseDate;

    EditText editTextCategory;
    Button btnAddExpense;
    private FragmentAddExpenseBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddExpenseViewModel addExpenseViewModel =
                new ViewModelProvider(this).get(AddExpenseViewModel.class);

        binding = FragmentAddExpenseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerViewCategories = binding.recyclerViewCategories;
        editTextExpense = binding.editTextExpense;
        editTextExpenseDescription = binding.editTextExpenseDescription;
        editTextExpenseDate = binding.editTextExpenseDate;
        btnAddExpense = binding.btnAddExpense;
        editTextCategory = binding.editTextCategory;

        AddData();

        editTextCategory.setInputType(InputType.TYPE_NULL);
        editTextCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("MyActivity", "EditText clicked");
                recyclerViewCategories.setVisibility(view.VISIBLE);
            }
        });

        // InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        // imm.showSoftInput(editTextCategory, InputMethodManager.SHOW_IMPLICIT);
        editTextCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    recyclerViewCategories.setVisibility(View.GONE);
                }
            }
        });

        GridLayoutManager gm = new GridLayoutManager(getContext(), 3);

        recyclerViewCategories.setLayoutManager(gm);
        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(categories, new CategoryRecyclerViewAdapter.onItemClickListener() {


            @Override
            public void onItemClick(int i) {
                String selectedCategory = categories.get(i).getCategoryName();
                int selectedImage = categories.get(i).getCategoryPic();

                Drawable originalDrawable = ContextCompat.getDrawable(getContext(), selectedImage);
                BitmapDrawable bitmapDrawable = (BitmapDrawable) originalDrawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                int desiredWidth = 35;
                int desiredHeight = 35;
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, false);
                Drawable drawable = new BitmapDrawable(getResources(), scaledBitmap);
                editTextCategory.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                editTextCategory.setText(selectedCategory);

            }
        });


        recyclerViewCategories.setAdapter(categoryRecyclerViewAdapter);


        TransactionDao transactionDao = ServiceLocator.getInstance().getTransactionDao(getContext());

        editTextExpenseDate.addTextChangedListener(new TextWatcher() {
            private String current = "";
            private final String ddmmyyyy = "DDMMYYYY";
            private final Calendar cal = Calendar.getInstance();

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
                            editTextExpenseDate.setError("Invalid date");
                            editTextExpenseDate.requestFocus();
                            return;
                        }
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = Math.max(sel, 0);
                    current = clean;
                    editTextExpenseDate.setText(current);
                    editTextExpenseDate.setSelection(Math.min(sel, current.length()));
                }
            }
        });




        btnAddExpense.setOnClickListener(v -> {

            try {
                if(editTextExpense.getText().toString().equalsIgnoreCase("0") ||
                        editTextExpense.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Income cannot be 0 or empty", Toast.LENGTH_SHORT).show();
                }
                if(editTextExpenseDescription.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
                }
                if(editTextExpenseDate.getText().toString().equalsIgnoreCase("") ||
                        editTextExpenseDate.getText().toString().toLowerCase().contains("d") ||
                        editTextExpenseDate.getText().toString().toLowerCase().contains("m") ||
                        editTextExpenseDate.getText().toString().toLowerCase().contains("y")){
                    Toast.makeText(getContext(), "Please enter a valid date", Toast.LENGTH_SHORT).show();

                }else{
                    double expense = Double.parseDouble(editTextExpense.getText().toString());
                    String description = editTextExpenseDescription.getText().toString();
                    String expenseDate = editTextExpenseDate.getText().toString();
                    //TODO Please use futures to handle work that needs to leave the main UI thread
                    // TODO Change category
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        new Thread(() -> transactionDao
                                .insertAll(new Transaction(-1 * expense,description, com.example.budgetbuddy.model.Category.INCOME,LocalDate.parse(expenseDate, DateTimeFormatter.ofPattern("dd/M/yyyy")))))
                                .start();
                    }

                    Toast.makeText(getContext(), "Expense added successfully", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Error entering input", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void AddData() {
        categories.add(new Category(101, "Housing", R.drawable.housing));
        categories.add(new Category(102, "Transportation", R.drawable.transportation));
        categories.add(new Category(103, "Food and Dining", R.drawable.feeding));
        categories.add(new Category(104, "Utilities", R.drawable.utilities));
        categories.add(new Category(105, "Insurance", R.drawable.insurance));
        categories.add(new Category(106, "Medical and Healthcare", R.drawable.medical));
        categories.add(new Category(104, "Personal Spending", R.drawable.personal_spending));
        categories.add(new Category(105, "Recreation and Entertainment", R.drawable.entertainment));
        categories.add(new Category(106, "Miscellaneous", R.drawable.miscellaneous));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}