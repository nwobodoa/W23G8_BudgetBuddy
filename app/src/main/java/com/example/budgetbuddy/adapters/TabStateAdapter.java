package com.example.budgetbuddy.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.budgetbuddy.ui.history.SpendingHistoryFragment;
import com.example.budgetbuddy.ui.allocation.AllocationFragment;
import com.example.budgetbuddy.ui.spendingbycat.SpendingByCategoryFragment;

import java.util.List;

public class TabStateAdapter extends FragmentStateAdapter {

    private final int NUM_TABS = 3;

    public TabStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       if(position == 0) {
           return new SpendingByCategoryFragment();
       }
       if(position == 1) {
           return new AllocationFragment();
       }
        return new SpendingHistoryFragment();
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }

    public List<String > getTabTitles() {
      return   List.of("Spending By Category", "Allocated vs Actual Spend", "Spending History");
    }

}
