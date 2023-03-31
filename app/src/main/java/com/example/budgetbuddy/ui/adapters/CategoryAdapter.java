package com.example.budgetbuddy.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.ui.add_expense.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categories.get(i).getCategoryId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_grid, parent, false);
        }

        ImageView imageView = view.findViewById(R.id.grid_item_image);
        TextView textView = view.findViewById(R.id.grid_item_text);

        // Set the image resource and text based on the item at the current position
        imageView.setImageResource(categories.get(position).getCategoryPic());
        textView.setText(categories.get(position).getCategoryName());

        return view;
    }

}
