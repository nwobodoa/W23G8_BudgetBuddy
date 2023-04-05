package com.example.budgetbuddy.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.budgetbuddy.databinding.FragmentHomeBinding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
     PieChart pieChart;
     TextView txtHomeTitle;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txtHomeTitle = binding.txtHomeTitle;
         pieChart = binding.pieChartView;

         initPieChart();
         showPieChart();

         return root;
    }
  //TODO show the title from data gotten from db.

    private void showPieChart(){
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        Map<String, Integer> expenseCategory = new HashMap<>();
        expenseCategory.put("Housing",200);
        expenseCategory.put("Transportation",230);
        expenseCategory.put("Food",200);
        expenseCategory.put("Utilities",200);
        expenseCategory.put("Insurance",200);
        expenseCategory.put("Medical",200);
        expenseCategory.put("Personal",200);
        expenseCategory.put("Entertainment",200);
        expenseCategory.put("Miscellaneous",200);

        ArrayList<Integer> colors =  new ArrayList<>();
        colors.add(Color.parseColor("#304567"));
        colors.add(Color.parseColor("#309967"));
        colors.add(Color.parseColor("#476567"));
        colors.add(Color.parseColor("#890567"));
        colors.add(Color.parseColor("#a35567"));
        colors.add(Color.parseColor("#ff5f67"));
        colors.add(Color.parseColor("#3ca567"));
        colors.add(Color.parseColor("#FFA600"));
        colors.add(Color.parseColor("#DE425B"));

        for(String type: expenseCategory.keySet()){
            pieEntries.add(new PieEntry(Objects.requireNonNull(expenseCategory.get(type)).floatValue(),type));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieData.setValueFormatter(new PercentFormatter());
    }

    private void initPieChart(){
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(true);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setRotationAngle(0);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        pieChart.setHoleRadius(40f);
        pieChart.setTransparentCircleRadius(50f);
        pieChart.setHoleColor( Color.parseColor("#F5E3DC"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
