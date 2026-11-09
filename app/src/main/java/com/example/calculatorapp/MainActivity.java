package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // kintamieji
    private static final String DEFAULT_VALUE = "0"; // Konstanta didžiosiomis raidėmis

    private TextView tvDisplay;
    private String currentText = DEFAULT_VALUE;
    private double firstOperand = 0;
    private String currentOperator = "";
    private boolean isNewOperation = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // 2. Skaičių ir taško mygtukų logika
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
                    return; // Neleidžiame įvesti antro taško
                }

                if (currentText.equals(DEFAULT_VALUE) && !buttonText.equals(".")) {
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

        // 3. Pagrindinių operacijų (+, -, *, /) logika
        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                currentOperator = button.getText().toString();
                firstOperand = Double.parseDouble(tvDisplay.getText().toString());
                isNewOperation = true;
            }
        };

        findViewById(R.id.btnPlus).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnMinus).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnMultiply).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnDivide).setOnClickListener(operatorClickListener);

        // 4. Lygybės mygtuko logika
        findViewById(R.id.btnEqual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentOperator.isEmpty()) return;
                double secondOperand = Double.parseDouble(tvDisplay.getText().toString());
                double result = calculateResult(firstOperand, secondOperand, currentOperator);

                currentText = formatResult(result);
                tvDisplay.setText(currentText);
                currentOperator = "";
                isNewOperation = true;
            }
        });

        // ---------------------------------------------------------
        // V ETAPAS: Papildomos operacijos (Reikalavimai 2 ir 3)
        // ---------------------------------------------------------

        // C - Teksto išvalymas (Clear All)
        findViewById(R.id.btnC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentText = DEFAULT_VALUE;
                firstOperand = 0;
                currentOperator = "";
                isNewOperation = true;
                tvDisplay.setText(currentText);
            }
        });

        // CE - Dabartinio įvedimo išvalymas (Clear Entry)
        findViewById(R.id.btnCE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentText = DEFAULT_VALUE;
                isNewOperation = true;
                tvDisplay.setText(currentText);
            }
        });

        // ← - Ženklų trynimas (Backspace)
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentText.length() > 1) {
                    currentText = currentText.substring(0, currentText.length() - 1);
                } else {
                    currentText = DEFAULT_VALUE;
                    isNewOperation = true;
                }
                tvDisplay.setText(currentText);
            }
        });

        // ± - Skaičiaus ženklo keitimas
        findViewById(R.id.btnSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentText.equals(DEFAULT_VALUE)) {
                    double value = Double.parseDouble(currentText);
                    currentText = formatResult(value * -1);
                    tvDisplay.setText(currentText);
                }
            }
        });

        // √ - Kvadratinės šaknies traukimas
        findViewById(R.id.btnSqrt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(currentText);
                if (value >= 0) {
                    currentText = formatResult(Math.sqrt(value));
                    tvDisplay.setText(currentText);
                    isNewOperation = true;
                }
            }
        });

        // % ir 1/x (Užprogramuota papildomai dėl sklandumo)
        findViewById(R.id.btnPercent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(currentText);
                currentText = formatResult(value / 100);
                tvDisplay.setText(currentText);
                isNewOperation = true;
            }
        });

        findViewById(R.id.btnInverse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(currentText);
                if (value != 0) {
                    currentText = formatResult(1 / value);
                    tvDisplay.setText(currentText);
                    isNewOperation = true;
                }
            }
        });
    }

    // 5. Pagalbiniai metodai (Reikalavimas 3c: metodai pradedami mažąja raide)
    private double calculateResult(double num1, double num2, String operator) {
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/":
                if (num2 == 0) return 0; // Apsauga nuo dalybos iš 0
                return num1 / num2;
            default: return 0;
        }
    }

    private String formatResult(double result) {
        String finalResult = String.valueOf(result);
        // Gražesnis atvaizdavimas - nuimame ".0" sveikiesiems skaičiams
        if (finalResult.endsWith(".0")) {
            return finalResult.substring(0, finalResult.length() - 2);
        }
        return finalResult;
    }
}