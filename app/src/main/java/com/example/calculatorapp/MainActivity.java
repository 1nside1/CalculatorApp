package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // kintamieji
    private TextView tvDisplay;
    private String currentText = "0";
    private double firstOperand = 0;
    private String currentOperator = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String buttonText = button.getText().toString();

                if (isNewOperation) {
                    currentText = "";
                    isNewOperation = false;
                }

                if (buttonText.equals(".") && currentText.contains(".")) {
                    return;
                }

                if (currentText.equals("0") && !buttonText.equals(".")) {
                    currentText = buttonText;
                } else {
                    currentText += buttonText;
                }
                tvDisplay.setText(currentText);
            }
        };

        int[] numberIds = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot};
        for (int id : numberIds) {
            findViewById(id).setOnClickListener(numberClickListener);
        }

        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                currentOperator = button.getText().toString();
                firstOperand = Double.parseDouble(tvDisplay.getText().toString());
                isNewOperation = true; // Sekantis paspaustas skaičius pradės naują įvedimą
            }
        };

        findViewById(R.id.btnPlus).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnMinus).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnMultiply).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnDivide).setOnClickListener(operatorClickListener);

        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOperator.isEmpty()) return;

                double secondOperand = Double.parseDouble(tvDisplay.getText().toString());
                double result = calculateResult(firstOperand, secondOperand, currentOperator);

                String finalResult = String.valueOf(result);
                if (finalResult.endsWith(".0")) {
                    finalResult = finalResult.substring(0, finalResult.length() - 2);
                }

                tvDisplay.setText(finalResult);
                currentText = finalResult;
                currentOperator = "";
                isNewOperation = true;
            }
        });
    }
    private double calculateResult(double num1, double num2, String operator) {
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/":
                if (num2 == 0) return 0;
                return num1 / num2;
            default: return 0;
        }
    }
}