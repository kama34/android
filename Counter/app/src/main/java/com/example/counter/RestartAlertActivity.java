package com.example.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class RestartAlertActivity extends AppCompatActivity implements View.OnClickListener {

    Button restart;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_alert);

        restart = (Button) findViewById(R.id.buttonOk);
        back = (Button) findViewById(R.id.buttonBack);

        restart.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.buttonOk:
                intent.putExtra("count", "0");
                Log.d("myLog", "Счётчик успешно обнулён.");
                break;
                // СБРОС ДАННЫХ СЧЁТЧИКА

            case R.id.buttonBack:
                Dialog dialog = new Dialog(this);
                dialog.dismiss();
                Log.d("myLog", "Переход на главную страницу.");
                // ВОЗВРАТ НА MainActivity
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}