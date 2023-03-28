package com.example.budgetbuddy.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.model.Expense;

import java.util.List;

public class ExpenseAdapter extends BaseAdapter {

    List<Expense> ExpenseList;
    int SelectedIndex;
    TextView textViewExpense;
    TextView textViewExpenseCategory;
    TextView textViewExpenseDate;

    public ExpenseAdapter(List<Expense> expenseList) {
        ExpenseList = expenseList;
        setSelectedIndex(-1);
    }

    public List<Expense> getExpenseList() {
        return ExpenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        ExpenseList = expenseList;
    }

    public int getSelectedIndex() {
        return SelectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        SelectedIndex = selectedIndex;
    }

    @Override
    public int getCount() {
        return ExpenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return ExpenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_budget_expense, parent, false);
        }
        textViewExpense = convertView.findViewById(R.id.textViewExpense);
        textViewExpenseCategory = convertView.findViewById(R.id.textViewExpenseCategory);
        textViewExpenseDate = convertView.findViewById(R.id.textViewExpenseDate);

        textViewExpense.setText(""+ExpenseList.get(position).getExpense());
        textViewExpenseCategory.setText(""+ExpenseList.get(position).getCategory());
        textViewExpenseDate.setText(""+ExpenseList.get(position).getExpenseDate());

        return convertView;
    }
}

