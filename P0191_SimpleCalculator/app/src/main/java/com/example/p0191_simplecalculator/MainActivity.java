package com.example.p0191_simplecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final int MENU_RESET_ID = 1;
    final int MENU_QUIT_ID = 2;

    EditText etNum1;
    EditText etNum2;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;

    TextView tvResult;

    String operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        tvResult = (TextView) findViewById(R.id.tvResult);

        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {
        float a = 0;
        float b = 0;
        float result= 0;
        operation = "";

        // проверка на пустату
        if ((TextUtils.isEmpty(etNum1.getText().toString()))
            || (TextUtils.isEmpty(etNum2.getText().toString()))) {
            tvResult.setText("Поле ввода не заполнено");
            return;
        }

        // читаем значения полей
        a = Float.parseFloat(etNum1.getText().toString());
        b = Float.parseFloat(etNum2.getText().toString());


        switch (v.getId()) {
            case R.id.btnAdd:
                operation = "+";
                result = a + b;
                break;
            case R.id.btnSub:
                operation = "-";
                result = a - b;
                break;
            case R.id.btnMult:
                operation = "*";
                result = a * b;
                break;
            case R.id.btnDiv:
                operation = "/";
                result = a / b;
                break;
        }

        tvResult.setText(String.valueOf(a) + " " + operation + " " + String.valueOf(b) + " = " + String.valueOf(result));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_RESET_ID, 0, "Reset" );
        menu.add(0, MENU_QUIT_ID, 0, "Quit" );
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESET_ID:
                etNum1.setText("");;
                etNum2.setText("");;
                tvResult.setText("");;
                break;

            case MENU_QUIT_ID: {
                finish();;
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}