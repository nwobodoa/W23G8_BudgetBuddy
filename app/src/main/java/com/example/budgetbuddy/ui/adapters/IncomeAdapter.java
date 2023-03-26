package com.example.budgetbuddy.ui.adapters;

//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.example.budgetbuddy.R;
//
//import java.util.List;
//
//public class IncomeAdapter {
//}
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.model.Income;

import java.util.List;

public class IncomeAdapter extends BaseAdapter {
    List<Income> IncomeList;
    int SelectedIndex;

    TextView textViewIncome;
    TextView textViewIncomeDescription;
    TextView textViewIncomeDate;

    public IncomeAdapter(List<Income> incomeList) {
        IncomeList = incomeList;
        setSelectedIndex(-1);
    }

    public List<Income> getIncomeList() {
        return IncomeList;
    }

    public void setIncomeList(List<Income> incomeList) {
        IncomeList = incomeList;
    }

    public int getSelectedIndex() {
        return SelectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        SelectedIndex = selectedIndex;
    }

    @Override
    public int getCount() {
        return IncomeList.size();
    }

    @Override
    public Object getItem(int position) {
        return IncomeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(
                    parent.getContext())
                    .inflate(R.layout.layout_budget_income, parent, false);
        }
        textViewIncome = convertView.findViewById(R.id.textViewIncome);
        textViewIncomeDescription = convertView.findViewById(R.id.textViewIncomeDescription);
        textViewIncomeDate = convertView.findViewById(R.id.textViewIncomeDate);

        textViewIncome.setText(""+IncomeList.get(position).getIncome());
        textViewIncomeDescription.setText(IncomeList.get(position).getDescription());
        textViewIncomeDate.setText(""+IncomeList.get(position).getIncomeDate());
        return convertView;
    }
}
