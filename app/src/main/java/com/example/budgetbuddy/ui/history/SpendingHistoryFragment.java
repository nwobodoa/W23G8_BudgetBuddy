package com.example.budgetbuddy.ui.history;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.budgetbuddy.databinding.FragmentSpendingHistoryBinding;
import com.example.budgetbuddy.adapters.TransactionAdapter;
import com.example.budgetbuddy.model.Transaction;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class SpendingHistoryFragment extends Fragment {
    private FragmentSpendingHistoryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSpendingHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button btnExport = binding.btnExport;
        ListView listViewTransactionHistory = binding.listViewTransactionHistory;
        HistoryViewModel historyViewModel = new ViewModelProvider(this).get(HistoryViewModel.class);
        historyViewModel.getAllTransactions()
                .observe(getViewLifecycleOwner(),
                        transactions -> listViewTransactionHistory
                                .setAdapter(new TransactionAdapter(transactions)));

        Button btnExportPDF = binding.btnExport2;
        Button btnExportCSV = binding.btnExport;
        btnExportCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveData<List<Transaction>> transactions = historyViewModel.getAllTransactions();
                transactions.observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
                    @Override
                    public void onChanged(List<Transaction> transactionList) {
                        StringBuilder data = new StringBuilder();
                        for (Transaction transaction : transactionList) {
                            //data.append(transaction.getId()).append(",")
                            data.append(transaction.getAmount()).append(",")
                                    .append(transaction.getCreatedAt()).append(",")
                                    .append(transaction.getDescription()).append("\n");
                        }

                        try {

                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "transactions4.csv");
                            FileOutputStream outputStream = new FileOutputStream(file);
                            outputStream.write(data.toString().getBytes());
                            outputStream.close();

                            Toast.makeText(getActivity(), "Transactions exported to Downloads" , Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        btnExportPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveData<List<Transaction>> transactions = historyViewModel.getAllTransactions();
                transactions.observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
                    @Override
                    public void onChanged(List<Transaction> transactionList) {

                        PdfDocument document = new PdfDocument();

                        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
                        PdfDocument.Page page = document.startPage(pageInfo);
                        Canvas canvas = page.getCanvas();
                        Paint paint = new Paint();

                        float x = 50;
                        float y = 50;

                        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                        canvas.drawText("Amount|Date|Description|Category", x, y, paint);
                        y += 20;


                        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));

                        for (Transaction transaction : transactionList) {
                            String line = transaction.getAmount() + "," + transaction.getCreatedAt() + "," + transaction.getDescription()+ "," + transaction.getCategory();
                            canvas.drawText(line, x, y, paint);
                            y += 20;
                        }

                        document.finishPage(page);

                        try {

                            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "transactions.pdf");
                            FileOutputStream outputStream = new FileOutputStream(file);
                            document.writeTo(outputStream);
                            outputStream.close();
                            document.close();

                            Toast.makeText(getActivity(), "Transactions exported to Downloads" , Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
