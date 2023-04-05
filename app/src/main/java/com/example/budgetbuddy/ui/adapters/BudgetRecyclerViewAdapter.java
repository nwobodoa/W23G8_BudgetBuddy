package com.example.budgetbuddy.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.ui.budget.Budget;

import java.util.List;

public class BudgetRecyclerViewAdapter extends RecyclerView.Adapter<BudgetRecyclerViewAdapter.ImageViewHolder> {

    List<Budget> BudgetList;

    public BudgetRecyclerViewAdapter(List<Budget> budgetList) {
        BudgetList = budgetList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_budget_item,parent,false);
       ImageViewHolder holder = new ImageViewHolder(itemView);
       return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        holder.imageViewItem.setImageResource(BudgetList.get(position).getBudgetPic());
        holder.textViewItem.setText(BudgetList.get(position).getBudgetName());

    }

    @Override
    public int getItemCount() {
        return BudgetList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewItem;
        TextView textViewItem;
        EditText editTextItem;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

           imageViewItem= itemView.findViewById(R.id.imgBudgetItem);
           textViewItem = itemView.findViewById(R.id.txtViewBudgetItem);
           editTextItem = itemView.findViewById(R.id.editTextBudgetItem);

        }
    }
}
