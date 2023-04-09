package com.example.budgetbuddy.ui.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.budgetbuddy.adapters.TabStateAdapter;
import com.example.budgetbuddy.databinding.FragmentStatsBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StatsFragment extends Fragment {

    private StatsViewModel mViewModel;

    private FragmentStatsBinding binding;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStatsBinding.inflate(inflater,container,false);
        ViewPager2 viewPager = binding.viewPager;
      TabLayout tabLayout = binding.tabLayout;
        TabStateAdapter adapter = new TabStateAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(adapter.getTabTitles().get(position))).attach();

        mViewModel = new ViewModelProvider(this).get(StatsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}