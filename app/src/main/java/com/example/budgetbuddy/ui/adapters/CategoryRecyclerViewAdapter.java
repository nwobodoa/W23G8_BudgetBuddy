package com.example.budgetbuddy.ui.adapters;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.ui.add_expense.CategoryIcon;

import java.util.List;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>{


    List<CategoryIcon> categories;
    int clickedIndex = -1;
    onItemClickListener onItemClickListener;


    public CategoryRecyclerViewAdapter(List<CategoryIcon> categories) {
        this.categories = categories;
    }

    public CategoryRecyclerViewAdapter(List<CategoryIcon> categories, CategoryRecyclerViewAdapter.onItemClickListener onItemClickListener) {
        this.categories = categories;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_categoryitem,parent,false);
        CategoryViewHolder holder = new CategoryViewHolder(itemView);

        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(holder.getAdapterPosition());
            clickedIndex= holder.getAdapterPosition();
            notifyDataSetChanged();
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
            holder.imgViewItem.setImageResource(categories.get(position).getCategoryPic());
            holder.txtViewItem.setText(categories.get(position).getCategoryName());
            holder.txtViewItem.setGravity(Gravity.CENTER);
            if (position == clickedIndex){
                holder.itemView.setBackgroundColor(Color.parseColor("#d5896c"));

            }else {
                holder.itemView.setBackgroundColor(Color.parseColor("#BA3B0A"));
            }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder{

            ImageView imgViewItem;
            TextView txtViewItem;

            public CategoryViewHolder(@NonNull View itemView) {
                super(itemView);
                imgViewItem = itemView.findViewById(R.id.imgViewExtItem);
                txtViewItem = itemView.findViewById(R.id.txtViewExtItem);

            }
        }


        public interface onItemClickListener {
            void onItemClick(int i);
        }
}



