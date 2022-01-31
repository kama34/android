package com.example.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditAlertActivity extends AppCompatActivity implements View.OnClickListener {

    Button back;
    Button ok;

    EditText name;
    EditText count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_alert);

        setTitle("Изменить счетчик");

        back = (Button) findViewById(R.id.buttonBack);
        ok = (Button) findViewById(R.id.buttonOk);

        back.setOnClickListener(this);
        ok.setOnClickListener(this);

        name = (EditText) findViewById(R.id.edit_name);
        count = (EditText)findViewById(R.id.edit_count);

        Bundle extras = getIntent().getExtras();
        String[] data = extras.getStringArray("data");

        name.setText(data[0]);
        count.setText(data[1]);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.buttonBack:
                Dialog dialog = new Dialog(this);
                dialog.dismiss();
                Log.d("myLog", "Переход на главную страницу.");
                // ВОЗВРАЩЕНИЕ НА MainActivity
                break;

            case R.id.buttonOk:
                String[] data = {name.getText().toString(), count.getText().toString()};
                intent.putExtra("edit", data);
                Log.d("myLog", "Новые данные счётчика переданы.");
                break;
        }
        setResult(RESULT_OK, intent);
        finish();
    }
}