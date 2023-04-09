package com.example.budgetbuddy.ui.spendingbycat;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgetbuddy.R;
import com.example.budgetbuddy.adapters.TransactionRVAdapter;
import com.example.budgetbuddy.databinding.FragmentSpendingByCatBinding;
import com.example.budgetbuddy.model.Category;
import com.example.budgetbuddy.model.TransactionByCategory;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class SpendingByCategoryFragment extends Fragment {


    PieChart pieChart;
    TextView txtHomeTitle;
    RecyclerView recyclerView;
    PieDataSet pieDataSet;
    private FragmentSpendingByCatBinding binding;
    private SpendingByCatViewModel spendingByCatViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSpendingByCatBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txtHomeTitle = binding.txtHomeTitle;
        pieChart = binding.pieChartView;
        recyclerView = binding.recyclerViewTransaction;
        txtHomeTitle.setText(R.string.select_category_to_view);
        spendingByCatViewModel = new ViewModelProvider(this).get(SpendingByCatViewModel.class);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

        spendingByCatViewModel
                .getTransactionsForCategory()
                .observe(getViewLifecycleOwner(), transactions -> {
                    TransactionRVAdapter transactionAdapter = new TransactionRVAdapter(transactions);
                    recyclerView.setAdapter(transactionAdapter);
                });

        spendingByCatViewModel = new ViewModelProvider(this).get(SpendingByCatViewModel.class);
        initPieChart();
        showPieChart();


        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String label = ((PieEntry) e).getLabel().toLowerCase();
                Category selectedCategory = Category.valueOfLabel(label);
                spendingByCatViewModel.getTransactionsForCategory(selectedCategory).observe(
                        getViewLifecycleOwner(), transactions -> {
                            String title = String.format("%s (%s)", selectedCategory.toString(), YearMonth.now());
                            txtHomeTitle.setText(title);
                            spendingByCatViewModel.updateTransactionsForCategory(transactions);
                        }
                );
            }

            @Override
            public void onNothingSelected() {
                txtHomeTitle.setText(R.string.select_category_to_view);
                TransactionRVAdapter transactionAdapter = new TransactionRVAdapter(List.of());
                recyclerView.setAdapter(transactionAdapter);
            }
        });

        return root;
    }


    private void showPieChart() {
        spendingByCatViewModel.getSpendingByCategory().observe(getViewLifecycleOwner(), this::setupPieChart);
    }

    private List<Integer> getPieChartColors() {
        return List.of(
                Color.parseColor("#304567"),
                Color.parseColor("#309967"),
                Color.parseColor("#476567"),
                Color.parseColor("#890567"),
                Color.parseColor("#a35567"),
                Color.parseColor("#ff5f67"),
                Color.parseColor("#3ca567"),
                Color.parseColor("#FFA600"),
                Color.parseColor("#DE425B"));
    }


    private void setupPieChart(List<TransactionByCategory> transactionByCategories) {
        if(transactionByCategories.isEmpty()) {
            txtHomeTitle.setText(R.string.no_expenditure);
            pieChart.setVisibility(View.GONE);
            return;
        }
        pieChart.setVisibility(View.VISIBLE);
        txtHomeTitle.setText(R.string.select_category_to_view);
        List<PieEntry> pieEntries = transactionByCategories
                .stream()
                .map(t -> new PieEntry((float) Math.abs(t.total), t.category.toString()))
                .collect(Collectors.toList());
        pieDataSet = new PieDataSet(pieEntries, "type");
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(getPieChartColors());
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieData.setValueFormatter(new PercentFormatter());
    }

    private void initPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setRotationAngle(0);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setHoleColor(Color.parseColor("#F5E3DC"));
        pieChart.getLegend().setEnabled(false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
