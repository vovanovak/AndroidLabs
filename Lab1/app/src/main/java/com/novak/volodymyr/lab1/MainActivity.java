package com.novak.volodymyr.lab1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private RadioGroup radioGroupOptions;
    private EditText editTextA;
    private EditText editTextB;
    private TextView textViewResultHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupOptions = findViewById(R.id.radioGroupOptions);
        editTextA = findViewById(R.id.editTextA);
        editTextB = findViewById(R.id.editTextB);
        textViewResultHeader = findViewById(R.id.textViewResultHeader);
    }

    public void onCalculate(View view) {
        double a = getNumberFromEditText(editTextA);
        double b = getNumberFromEditText(editTextB);

        int checkedRadioButtonId = radioGroupOptions.getCheckedRadioButtonId();

        String textViewResultHeaderText;

        try {
            double res = performCalculationByCheckedRadioButtonId(a, b, checkedRadioButtonId);
            textViewResultHeaderText = "Result: " + res;
        } catch (ArithmeticException e) {
            textViewResultHeaderText = e.getMessage();
        }

        printResult(textViewResultHeaderText);
    }

    private double getNumberFromEditText(EditText editText) {
        return Integer.parseInt(editText.getText().toString());
    }

    private double performCalculationByCheckedRadioButtonId(
            double a,
            double b,
            int checkedRadioButtonId) throws ArithmeticException {
        double res = Double.MIN_VALUE;

        switch (checkedRadioButtonId) {
            case R.id.radioButtonAdd:
                res = a + b;
                break;
            case R.id.radioButtonSubtract:
                res = a - b;
                break;
            case R.id.radioButtonMultiply:
                res = a * b;
                break;
            case R.id.radioButtonDivide:
                if (b == 0)
                    throw new ArithmeticException("Division on zero is illegal!");
                res = a / b;
                break;
        }

        return res;
    }

    private void printResult(String textViewResultHeaderText) {
        textViewResultHeader.setText(textViewResultHeaderText);
    }
}
