package com.novak.volodymyr.lab1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ArrayList<CalculationResult> calculationResults = new ArrayList<CalculationResult>();

        TextView textView = findViewById(R.id.historyResultsText);

        try {
            calculationResults = CalculationResultsFileWorker.readCalculationData(getApplicationContext());
        } catch (FileNotFoundException e) {
            textView.setText(e.getStackTrace().toString());
        }

        if (calculationResults.isEmpty()) {
            textView.setText("History is empty");
        } else {
            StringBuilder sb = new StringBuilder();

            for (CalculationResult result: calculationResults) {
                sb.append(result.toString());
            }

            textView.setText(sb.toString());
        }
    }

    public void onReturn(View view) {
        Intent intent = new Intent(this,
                MainActivity.class);
        startActivity(intent);
    }
}
