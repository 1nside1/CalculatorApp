package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //kintamieji
    private TextView tvDisplay;
    private String currentText = "0";

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

                if (currentText.equals("0") && !buttonText.equals(".")) {
                    currentText = buttonText;
                } else {
                    currentText += buttonText;
                }
                tvDisplay.setText(currentText);
            }
        };

        findViewById(R.id.btn0).setOnClickListener(numberClickListener);
        findViewById(R.id.btn1).setOnClickListener(numberClickListener);
        findViewById(R.id.btn2).setOnClickListener(numberClickListener);
        findViewById(R.id.btn3).setOnClickListener(numberClickListener);
        findViewById(R.id.btn4).setOnClickListener(numberClickListener);
        findViewById(R.id.btn5).setOnClickListener(numberClickListener);
        findViewById(R.id.btn6).setOnClickListener(numberClickListener);
        findViewById(R.id.btn7).setOnClickListener(numberClickListener);
        findViewById(R.id.btn8).setOnClickListener(numberClickListener);
        findViewById(R.id.btn9).setOnClickListener(numberClickListener);
        findViewById(R.id.btnDot).setOnClickListener(numberClickListener);
    }
}