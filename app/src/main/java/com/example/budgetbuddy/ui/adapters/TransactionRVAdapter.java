package com.example.budgetbuddy.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.model.Transaction;

import java.util.List;


public class TransactionRVAdapter extends RecyclerView.Adapter<TransactionRVAdapter.myViewHolder> {

    List<Transaction> transactionList;

    public TransactionRVAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction, parent, false);
        return new myViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.txtViewDesc.setText(transactionList.get(position).getDescription());
        holder.txtViewDate.setText(String.valueOf(transactionList.get(position).getCreatedAt()));
        holder.txtViewAmount.setText(String.valueOf(transactionList.get(position).getAmount()));
        holder.txtViewCategory.setText(String.valueOf(transactionList.get(position).getCategory()));

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
    TextView txtViewAmount;
    TextView txtViewDate;
    TextView txtViewDesc;
    TextView txtViewCategory;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            txtViewAmount = itemView.findViewById(R.id.textViewTransactionAmount);
            txtViewCategory = itemView.findViewById(R.id.textViewTransactionCategory);
            txtViewDate = itemView.findViewById(R.id.textViewTransactionDate);
            txtViewDesc = itemView.findViewById(R.id.textViewTransactionDescription);



        }
    }
}
