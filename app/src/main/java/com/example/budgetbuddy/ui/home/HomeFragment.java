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
import com.example.budgetbuddy.model.Budget;
import com.example.budgetbuddy.model.Transaction;
import com.example.budgetbuddy.repository.dao.BudgetDao;
import com.example.budgetbuddy.repository.dao.TransactionDao;
import com.example.budgetbuddy.servicelocator.ServiceLocator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeFragment extends Fragment {
    //    BarChart barChart;
//    ArrayList<BarEntry> barEntries1;
    List<Budget> budgets = new ArrayList<>();
////    ArrayList<BarEntry> barEntries2;
    List<Transaction> expenses = new ArrayList<>();
    List<Transaction> incomes = new ArrayList<>();
    private FragmentHomeBinding binding;
//     PieChart pieChart;
BarChart mChart;
     TextView txtHomeTitle;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        txtHomeTitle = binding.txtHomeTitle;



        BudgetDao budgetDao = ServiceLocator.getInstance().getBudgetDao(getContext());
        TransactionDao transactionDao = ServiceLocator.getInstance().getTransactionDao(getContext());

        budgets = budgetDao.getAll();
        expenses = transactionDao.getAllNegativeTransactions();
        incomes = transactionDao.getAllPositiveTransactions();

        TextView textViewTotalBudget = binding.textViewTotalBudgetValues;
        TextView textViewTotalIncome = binding.textViewTotalIncomeValues;
        TextView textViewTotalExpense = binding.textViewTotalExpenseValues;



        double totalBudget = 0.0;
        double totalExpense = 0.0;
        double totalIncome = 0.0;

        for (Budget budget : budgets) {
            totalBudget += budget.getAmount();
        }
        for (Transaction expense : expenses) {
            totalExpense += (expense.getAmount() * -1);
        }
        for (Transaction income : incomes) {
            totalIncome += income.getAmount();
        }
        textViewTotalBudget.setText(""+totalBudget);
        textViewTotalExpense.setText(""+totalExpense);
        textViewTotalIncome.setText(""+totalIncome);

        textViewTotalExpense.setTextColor(Color.RED);
        textViewTotalIncome.setTextColor(Color.BLUE);



        if(budgets.size() > 0 && expenses.size() > 0){
            GroupBarChart();
        }

         return root;
    }


    public void GroupBarChart(){

            mChart = (BarChart) binding.barChart;
            mChart.setDrawBarShadow(false);
            mChart.getDescription().setEnabled(false);
            mChart.setPinchZoom(false);
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(false);
            mChart.setDrawGridBackground(true);
            mChart.setVisibleXRangeMaximum(6f);
            // empty labels so that the names are spread evenly
            String[] labels = {"", "Housing", "Transportation", "Food and Dining", "Utilities", "Insurance", "Medical and Healthcare", "Personal Spending", "Recreation and Entertainment", "Miscellaneous", ""};
            XAxis xAxis = mChart.getXAxis();
            xAxis.setCenterAxisLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(true);
            xAxis.setGranularity(1f); // only intervals of 1 day
            xAxis.setTextColor(Color.BLACK);
            xAxis.setTextSize(12);
            xAxis.setAxisLineColor(Color.WHITE);
            xAxis.setAxisMinimum(1f);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
            xAxis.setLabelRotationAngle(90);
            xAxis.setLabelCount(labels.length);
            xAxis.setAvoidFirstLastClipping(true);
            xAxis.setTextSize(16f);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setTextColor(Color.BLACK);
            leftAxis.setTextSize(12);
            leftAxis.setAxisLineColor(Color.WHITE);
            leftAxis.setDrawGridLines(true);
            leftAxis.setGranularity(2);
            leftAxis.setLabelCount(8, true);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            leftAxis.setTextSize(16f);

            mChart.getAxisRight().setEnabled(false);
            mChart.getLegend().setEnabled(false);


            BudgetDao budgetDao = ServiceLocator.getInstance().getBudgetDao(getContext());
            TransactionDao transactionDao = ServiceLocator.getInstance().getTransactionDao(getContext());
            budgets = budgetDao.getAll();
            expenses = transactionDao.getAllNegativeTransactions();
            Collections.sort(budgets, new Comparator<Budget>() {
                @Override
                public int compare(Budget budget1, Budget budget2) {
                    return budget1.getCategory().compareTo(budget2.getCategory());
                }
            });
        Collections.sort(expenses, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction expense1, Transaction expense2) {
                return expense1.getCategory().compareTo(expense2.getCategory());
            }
        });

        List<Float> budgetEntries = new ArrayList<Float>();
        List<Float> expenseEntries = new ArrayList<Float>();

        for (String label : labels) {
            Budget budget = null;
            Transaction expense = null;
            for (Budget b : budgets) {
                if (b.getCategory().equals(label)) {
                    budget = b;
                    break;
                }
            }
            for (Transaction e : expenses) {
                if (e.getCategory().equals(label)) {
                    expense = e;
                    break;
                }
            }
            if (budget != null) {
                Float budgetAmount = (float) Math.round(budget.getAmount());
                budgetEntries.add(budgetAmount);
            } else {
                budgetEntries.add(0f);
            }
            if (expense != null) {
                Float expenseAmount = (float) Math.round(expense.getAmount());
                expenseEntries.add(expenseAmount * -1);
            } else {
                expenseEntries.add(0f);
            }
        }


        ArrayList<BarEntry> barOne = new ArrayList<>();
        ArrayList<BarEntry> barTwo = new ArrayList<>();

        for (int i = 1; i < labels.length - 1; i++) {
            String category = labels[i];
            float expenseAmount = 0f;
            float budgetAmount = 0f;
            for (Transaction expense : expenses) {
                if (expense.getCategory().equals(category)) {
                    expenseAmount = (float) Math.round(expense.getAmount() * -1);
                    break;
                }
            }
            for (Budget budget : budgets) {
                if (budget.getCategory().equals(category)) {
                    budgetAmount = (float) Math.round(budget.getAmount());
                    break;
                }
            }
            barOne.add(new BarEntry(i, expenseAmount));
            barTwo.add(new BarEntry(i, budgetAmount));
        }

        BarDataSet set1 = new BarDataSet(barOne, "Expenses");
        set1.setColor(Color.RED);
        BarDataSet set2 = new BarDataSet(barTwo, "Budgets");
        set2.setColor(Color.GRAY);

        set1.setHighlightEnabled(false);
        set2.setHighlightEnabled(false);
        set1.setDrawValues(false);
        set2.setDrawValues(false);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        dataSets.add(set2);
        BarData data = new BarData(dataSets);

        float groupSpace = 0.6f;
        float barSpace = 0f;
        float barWidth = 0.2f;
// (barSpace + barWidth) * 2 + groupSpace = 1
        data.setBarWidth(barWidth);

        xAxis.setAxisMaximum(labels.length - 1);
        mChart.setData(data);
        mChart.groupBars(1f, groupSpace, barSpace);
        mChart.invalidate();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}