package com.example.budgetbuddy.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.model.Transaction;

import java.util.List;


public class TransactionRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    List<Transaction> transactionList;


    public TransactionRVAdapter(List<Transaction> transactionList) {
        this.transactionList = transactionList;
      }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        if(viewType == VIEW_TYPE_HEADER){
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction_header,parent,false);
            return new HeaderViewHolder(headerView);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_transaction, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).titleAmount.setText(R.string.Amount);
            ((HeaderViewHolder) holder).titleDate.setText(R.string.date);
            ((HeaderViewHolder) holder).titleDesc.setText(R.string.Description);

        } else if (holder instanceof MyViewHolder) {

            Transaction transactions = transactionList.get(position - 1);

            ((MyViewHolder) holder).txtViewDesc.setText(transactions.getDescription());
            ((MyViewHolder) holder).txtViewDate.setText(String.valueOf(transactions.getCreatedAt()));
            ((MyViewHolder) holder).txtViewAmount.setText(String.valueOf(transactions.getAmount()));

        }
    }

    @Override
    public int getItemCount() {
        return transactionList.size() + 1;
    }

    public int getItemViewType(int position){
       return position == 0 ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;
    }
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView titleAmount;
        TextView titleDesc;
        TextView titleDate;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            titleAmount = itemView.findViewById(R.id.txtViewHeaderAmount);
            titleDesc = itemView.findViewById(R.id.txtViewHeaderDesc);
            titleDate = itemView.findViewById(R.id.txtViewHeaderDate);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
    TextView txtViewAmount;
    TextView txtViewDate;
    TextView txtViewDesc;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtViewAmount = itemView.findViewById(R.id.textViewTransactionAmount);
            txtViewDate = itemView.findViewById(R.id.textViewTransactionDate);
            txtViewDesc = itemView.findViewById(R.id.textViewTransactionDescription);

        }

    }

}
