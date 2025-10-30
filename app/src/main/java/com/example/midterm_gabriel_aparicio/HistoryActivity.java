package com.example.midterm_gabriel_aparicio;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView lvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lvHistory = findViewById(R.id.lvHistory);

        ArrayList<Integer> history = getIntent().getIntegerArrayListExtra("history");
        if (history == null) history = new ArrayList<>();

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                history
        );
        lvHistory.setAdapter(adapter);
    }
}
