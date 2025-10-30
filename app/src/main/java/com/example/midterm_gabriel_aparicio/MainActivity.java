package com.example.midterm_gabriel_aparicio;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNumber;
    private Button btnGenerate, btnHistory, btnClearAll;
    private ListView lvTable;

    private ArrayList<String> currentTable = new ArrayList<>();
    private ArrayAdapter<String> tableAdapter;

    private ArrayList<Integer> historyNumbers = new ArrayList<>();

    private static final String KEY_TABLE = "key_table";
    private static final String KEY_HISTORY = "key_history";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnHistory = findViewById(R.id.btnHistory);
        lvTable = findViewById(R.id.lvTable);
        btnClearAll = findViewById(R.id.btnClearAll);

        if (savedInstanceState != null) {
            currentTable = savedInstanceState.getStringArrayList(KEY_TABLE);
            if (currentTable == null) currentTable = new ArrayList<>();
            historyNumbers = savedInstanceState.getIntegerArrayList(KEY_HISTORY);
            if (historyNumbers == null) historyNumbers = new ArrayList<>();
        }

        tableAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                currentTable);
        lvTable.setAdapter(tableAdapter);

        btnGenerate.setOnClickListener(v -> {
            String s = etNumber.getText().toString().trim();
            if (TextUtils.isEmpty(s)) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
                return;
            }
            int n;
            try {
                n = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid number", Toast.LENGTH_SHORT).show();
                return;
            }

            currentTable.clear();
            for (int i = 1; i <= 10; i++) {
                currentTable.add(n + " × " + i + " = " + (n * i));
            }
            tableAdapter.notifyDataSetChanged();

            if (!historyNumbers.contains(n)) {
                historyNumbers.add(n);
            }
        });

        lvTable.setOnItemClickListener((parent, view, position, id) -> {
            final String item = currentTable.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Delete row?")
                    .setMessage("Do you want to delete:\n" + item)
                    .setPositiveButton("Delete", (dialog, which) -> {
                        currentTable.remove(position);
                        tableAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "Deleted: " + item, Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putIntegerArrayListExtra("history", historyNumbers);
            startActivity(intent);
        });

        // BONUS: Clear All feature attempt
        btnClearAll.setOnClickListener(v -> {
            if (currentTable.isEmpty()) {
                Toast.makeText(this, "Nothing to clear.", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Clear all?")
                    .setMessage("Do you want to delete all rows?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        currentTable.clear();
                        tableAdapter.notifyDataSetChanged();
                        Toast.makeText(this, "All rows cleared.", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    @Override
    protected void onSaveInstanceState(@Nullable Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putStringArrayList(KEY_TABLE, currentTable);
            outState.putIntegerArrayList(KEY_HISTORY, historyNumbers);
        }
    }
}
