package com.example.budgetbuddy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.databinding.LayoutSummaryItemBinding;
import com.example.budgetbuddy.model.SummaryItem;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;
import java.util.stream.Collectors;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.SummaryViewHolder> {
    List<SummaryItem> summaryItemList;
    Context context;

    public SummaryAdapter(List<SummaryItem> summaryItemList, Context context) {
        this.summaryItemList = summaryItemList
                .stream()
                .filter(summaryItem -> summaryItem.getActualSpend() != 0.0 || summaryItem.getBudgetAmount() != 0.0)
                .collect(Collectors.toList());
        this.context = context;
    }

    @NonNull
    @Override
    public SummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutSummaryItemBinding binding = LayoutSummaryItemBinding
                .inflate(LayoutInflater.from(parent.getContext()),
                        parent,false);

            SummaryViewHolder holder = new SummaryViewHolder(binding);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryViewHolder holder, int position) {

       Double budgetAmt = summaryItemList.get(position).getBudgetAmount();
       Double amtSpent =summaryItemList.get(position).getActualSpend();

        holder.binding.txtBudgetAmt.setText(String.valueOf(budgetAmt));
        holder.binding.txtSpentAmount.setText(String.valueOf(amtSpent));
        holder.binding.txtViewCat.setText(String.valueOf(summaryItemList.get(position).getCategory()));
        setIndicator(holder.binding.progressIndicator,budgetAmt,amtSpent);
    }

    private double calculatedPercentSpent(double budgetAmt, double amtSpent) {
        if (budgetAmt == 0.0) {
            return  0;
        }
        return  (amtSpent/budgetAmt) * 100;
    }

    private void setIndicator(LinearProgressIndicator linearProgressIndicator,double budgetAmt, double amtSpent) {
        linearProgressIndicator.setTrackThickness(20);
        linearProgressIndicator.setTrackCornerRadius(50);

        if(amtSpent >= budgetAmt) {
            linearProgressIndicator.setProgress(100);
            linearProgressIndicator.setIndicatorColor(Color.RED);
            return;
        }
        double percentageSpent = calculatedPercentSpent(budgetAmt, amtSpent);
        if((int) percentageSpent > 60) {
            linearProgressIndicator.setIndicatorColor(ContextCompat.getColor(context, R.color.warning_indicator));
            linearProgressIndicator.setProgress((int) percentageSpent);
            return;
        }
        linearProgressIndicator.setIndicatorColor(ContextCompat.getColor(context, R.color.green_indicator));
        linearProgressIndicator.setProgress((int) percentageSpent);

    }

    @Override
    public int getItemCount() {
        return summaryItemList.size();
    }

    public static class SummaryViewHolder extends RecyclerView.ViewHolder{
        LayoutSummaryItemBinding binding;
        public SummaryViewHolder(LayoutSummaryItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
}
