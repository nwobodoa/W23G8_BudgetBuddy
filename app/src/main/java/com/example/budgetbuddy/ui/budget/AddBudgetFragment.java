package com.example.budgetbuddy.ui.budget;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.databinding.FragmentAddBudgetBinding;
import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

public class AddBudgetFragment extends Fragment {
    List<Budget> budgets = new ArrayList<>();
    EditText editTextBudgetAmount;
    RecyclerView recyclerViewBudget;
    Button btnAddBudget;
    private FragmentAddBudgetBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AddBudgetViewModel ViewModel =
                new ViewModelProvider(this).get(AddBudgetViewModel.class);

        binding = FragmentAddBudgetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        recyclerViewBudget =binding.recyclerViewBudget;
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerViewBudget.setLayoutManager(layoutManager);
//        BudgetRecyclerViewAdapter adapter = new BudgetRecyclerViewAdapter(budgets);
//        recyclerViewBudget.setAdapter(adapter);

//        AddData();

        BudgetDao budgetDao = ServiceLocator.getInstance().getBudgetDao(getContext());

        EditText editTextHousing = binding.editTextHousing;
        EditText editTextTransportation = binding.editTextTransportation;
        EditText editTextFood = binding.editTextFood;
        EditText editTextUtilities = binding.editTextUtilities;
        EditText editTextInsurance = binding.editTextInsurance;
        EditText editTextMedical = binding.editTextMedical;
        EditText editTextPersonal = binding.editTextPersonal;
        EditText editTextEntertainment = binding.editTextEntertainment;
        EditText editTextMiscellaneous = binding.editTextMiscellaneous;
        Button btnAddBudget = binding.btnAddBudget;

        btnAddBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputStringHousing = editTextHousing.getText().toString().trim();
                String inputStringTransportation = editTextTransportation.getText().toString().trim();
                String inputStringFood = editTextFood.getText().toString().trim();
                String inputStringUtilities = editTextUtilities.getText().toString().trim();
                String inputStringInsurance = editTextInsurance.getText().toString().trim();
                String inputStringMedical = editTextMedical.getText().toString().trim();
                String inputStringPersonal = editTextPersonal.getText().toString().trim();
                String inputStringEntertainment = editTextEntertainment.getText().toString().trim();
                String inputStringMiscellaneous = editTextMiscellaneous.getText().toString().trim();

                if (!TextUtils.isEmpty(inputStringHousing) && !TextUtils.isEmpty(inputStringTransportation) &&
                        !TextUtils.isEmpty(inputStringFood) && !TextUtils.isEmpty(inputStringUtilities) &&
                        !TextUtils.isEmpty(inputStringInsurance) && !TextUtils.isEmpty(inputStringMedical) &&
                        !TextUtils.isEmpty(inputStringPersonal) && !TextUtils.isEmpty(inputStringEntertainment) &&
                        !TextUtils.isEmpty(inputStringMiscellaneous)) {
                    try {

                        budgets.add(new Budget(Double.parseDouble(inputStringHousing), "Housing"));
                        budgets.add(new Budget(Double.parseDouble(inputStringTransportation), "Transportation"));
                        budgets.add(new Budget(Double.parseDouble(inputStringFood), "Food and Dining"));
                        budgets.add(new Budget(Double.parseDouble(inputStringUtilities), "Utilities"));
                        budgets.add(new Budget(Double.parseDouble(inputStringInsurance), "Insurance"));
                        budgets.add(new Budget(Double.parseDouble(inputStringMedical), "Medical and Healthcare"));
                        budgets.add(new Budget(Double.parseDouble(inputStringPersonal), "Personal Spending"));
                        budgets.add(new Budget(Double.parseDouble(inputStringEntertainment), "Recreation and Entertainment"));
                        budgets.add(new Budget(Double.parseDouble(inputStringMiscellaneous), "Miscellaneous"));
                        int budgetCount = budgetDao.getBudgetCount();
                        if(budgetCount == 0) {
                            for (Budget budget : budgets) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    new Thread(() -> budgetDao
                                            .insertAll(new Budget(budget.getAmount(), budget.getCategory())))
                                            .start();
                                }

                                Toast.makeText(getContext(), "New budget created", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            budgets.clear();
                            budgetDao.deleteAll();
                            Toast.makeText(getContext(), "Budget is reset", Toast.LENGTH_SHORT).show();
                        }



                    } catch (NumberFormatException e) {
                        Toast.makeText(getContext(), "Error setting up page", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "Enter a value for all categories", Toast.LENGTH_SHORT).show();
                }

            }
        });

//            budgets.add(new Budget(Double.parseDouble(editTextHousing.getText().toString()), "Housing"));

            //            budgets.add(new Budget(Double.parseDouble(editTextTransportation.getText().toString()), "Transportation"));
//            budgets.add(new Budget(Double.parseDouble(editTextFood.getText().toString()), "Food"));
//            budgets.add(new Budget(Double.parseDouble(editTextUtilities.getText().toString()), "Utilities"));
//            budgets.add(new Budget(Double.parseDouble(editTextInsurance.getText().toString()), "Insurance"));
//            budgets.add(new Budget(Double.parseDouble(editTextMedical.getText().toString()), "Medical"));
//            budgets.add(new Budget(Double.parseDouble(editTextPersonal.getText().toString()), "Personal"));
//            budgets.add(new Budget(Double.parseDouble(editTextEntertainment.getText().toString()), "Entertainment"));
//            budgets.add(new Budget(Double.parseDouble(editTextMiscellaneous.getText().toString()), "Miscellaneous"));
//        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private void AddData() {
//        budgets.add(new Budget(101, "Housing", R.drawable.housing,0));
//        budgets.add(new Budget(102, "Transportation", R.drawable.transportation,0));
//        budgets.add(new Budget(103, "Food and Dining", R.drawable.feeding,0));
//        budgets.add(new Budget(104, "Utilities", R.drawable.utilities,0));
//        budgets.add(new Budget(105, "Insurance", R.drawable.insurance,0));
//        budgets.add(new Budget(106, "Medical and Healthcare", R.drawable.medical,0));
//        budgets.add(new Budget(104, "Personal Spending", R.drawable.personal_spending,0));
//        budgets.add(new Budget(105, "Recreation and Entertainment", R.drawable.entertainment,0));
//        budgets.add(new Budget(106, "Miscellaneous", R.drawable.miscellaneous,0));
//    }
}
