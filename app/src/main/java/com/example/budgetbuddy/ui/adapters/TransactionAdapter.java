package com.example.budgetbuddy.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.model.Transaction;

import java.util.List;

public class TransactionAdapter extends BaseAdapter {

    List<Transaction> TransactionList;
    int SelectedIndex;
    TextView textViewTransaction;
    TextView textViewTransactionDescription;
    TextView textViewTransactionDate;
    public TransactionAdapter(List<Transaction> transactionList) {
        TransactionList = transactionList;
        setSelectedIndex(-1);
    }

    public List<Transaction> getTransactionList() {
        return TransactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        TransactionList = transactionList;
    }

    public int getSelectedIndex() {
        return SelectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        SelectedIndex = selectedIndex;
    }

    @Override
    public int getCount() {
        return TransactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return TransactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView ==null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_budget_transaction, parent, false);
        }
        textViewTransaction = convertView.findViewById(R.id.textViewTransactionAmount);
        textViewTransactionDescription = convertView.findViewById(R.id.textViewTransactionDescription);
        textViewTransactionDate = convertView.findViewById(R.id.textViewTransactionDate);

        String amount = "" + TransactionList.get(position).getAmount();
        String prefix = "";
        if (!amount.contains("-")) {
            prefix = "$";
        } else {
            prefix = "-$";
            amount = amount.substring(1);
        }
        textViewTransaction.setText(prefix + amount);
        textViewTransactionDescription.setText(""+TransactionList.get(position).getDescription());
        textViewTransactionDate.setText(""+TransactionList.get(position).getCreatedAt());


        int textColor = R.color.green;
        if (TransactionList.get(position).getAmount() < 0 ) {
            textColor = R.color.red;
        }

        textViewTransaction.setTextColor(ContextCompat.getColor(parent.getContext(), textColor));
        textViewTransactionDescription.setTextColor(ContextCompat.getColor(parent.getContext(), textColor));
        textViewTransactionDate.setTextColor(ContextCompat.getColor(parent.getContext(), textColor));

        return convertView;
    }
}
