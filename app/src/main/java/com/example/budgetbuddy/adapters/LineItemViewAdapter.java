package com.example.budgetbuddy.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.model.LineItem;

import java.util.List;
import java.util.stream.Collectors;

public class LineItemViewAdapter extends RecyclerView.Adapter<LineItemViewAdapter.ImageViewHolder> {

    List<LineItem> lineItems;
    List<LineItem> filledLineItems;

    public LineItemViewAdapter(List<LineItem> lineItems) {
        this.lineItems = lineItems;
        filledLineItems = lineItems
                .stream()
                .map(lineItem ->
                        new LineItem(lineItem.getId(),
                                lineItem.getAllocationId(),
                                lineItem.getAmount(),
                                lineItem.getCategory()))
                .collect(Collectors.toList());
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

        holder.imageViewItem.setImageResource(lineItems.get(position).getCategory().getDrawableId());
        holder.textViewItem.setText(lineItems.get(position).getCategory().toString());
        String startingAmount = lineItems.get(position).getAmount() == 0.0 ? "" : String.valueOf(lineItems.get(position).getAmount() );
        holder.editTextItem.setText(startingAmount);
        holder.editTextItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                double amount = charSequence.toString().isBlank() ? 0.0 : Double.parseDouble((charSequence.toString()));
                filledLineItems.get(holder.getAdapterPosition()).setAmount(amount);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public List<LineItem> getFilledLineItems(){
        return filledLineItems;
    }

    @Override
    public int getItemCount() {
        return lineItems.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewItem;
        TextView textViewItem;
        public EditText editTextItem;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

           imageViewItem= itemView.findViewById(R.id.imgBudgetItem);
           textViewItem = itemView.findViewById(R.id.txtViewBudgetItem);
           editTextItem = itemView.findViewById(R.id.editTextBudgetItem);

        }



    }
}
