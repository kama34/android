package com.example.counter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private static DBHelper dbHelper;// МОЙ КЛАСС ДЛЯ РАБОТЫ С БАЗОЙ ДАННЫХ
    private static SQLiteDatabase database;// БАЗА ДАННЫХ
    private static int id = 1;
    private static SharedPreferences prefs;

    TextView countView; // СЧЁТЧИК - ТЕКСТ
    Button buttonPlus; // КНОПКА - ПЛЮС
    Button buttonMinus; // КНОПКА - МИНУС

    Intent intent = null; // ОБЪЕКТ ДЛЯ ПЕРЕХОДА НА НОВЫЙ ЭКРАН

    Vibrator vibrator;

    String value;

    // ФУНКИЦИЯ ЗАПУСКАЮЩАЯСЯ ПРИ СОЗДАНИИ АКТИВИТИ
    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Context context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        value = prefs.getString("style_list", "Светлый");
        if (value.equals("Светлый")) {
            setTheme(R.style.Theme_Counter);
        } else if (value.equals("Темный")) {
            setTheme(R.style.Theme_AppCompat);
        } else {
            setTheme(R.style.Theme_AppCompat_DayNight_DarkActionBar);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        countView = (TextView) findViewById(R.id.count);
        buttonPlus = (Button) findViewById(R.id.buttonPlus);
        buttonMinus = (Button) findViewById(R.id.buttonMinus);

        buttonPlus.setOnClickListener(this);
        buttonMinus.setOnClickListener(this);
        countView.setOnClickListener(this);

        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();
        if (prefs.getBoolean("display_on_check_box", false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        save(dbHelper.loadName(database, id), Integer.toString(dbHelper.loadCount(database, id)));

        setTitle(load()[0]);
        countView.setText(load()[1]);

        Log.d("myLog", load()[0] + " = " + load()[1]);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent2 = getIntent();
        finish();
        startActivity(intent2);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.saveCounter(database, id, load()[0], Integer.parseInt(load()[1]));
    }

    // СЛУШАТЕЛЬ НАЖАТИЯ НА КНОПКИ (+ и -)
    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View v) {

        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final float Volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        boolean flag = false;

        int count = Integer.parseInt(prefs.getString("count", "0"));
        switch (v.getId()) {
            case R.id.buttonPlus:
                save(prefs.getString("name", "Counter"), Integer.toString(++count));
                break;

            case R.id.buttonMinus:
                if (count == 0) {
                    Toast.makeText(this,"Нельзя уйти в минус", Toast.LENGTH_SHORT).show();
                } else {
                    save(prefs.getString("name", "Counter"), Integer.toString(--count));
                }
                break;

            case R.id.count:
                if (prefs.getBoolean("counter_inc_check_box", true)) {
                    save(prefs.getString("name", "Counter"), Integer.toString(++count));
                } else flag = true;
        }

        if (!flag) {
            if (!prefs.getBoolean("sound_check_box", true) ) {
                audioManager.playSoundEffect(SoundEffectConstants.CLICK,Volume );
            }

            if (prefs.getBoolean("vibration_check_box", false)) {
                vibrator.vibrate(3);
            }
        }

        countView.setText(Integer.toString(count));
    }

    // СОЗДАНИЕ МЕНЮ (три точки сверху)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // ОБРАБОТЧИК НАЖАТИЙ КНОПОК МЕНЮ
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_restart:
                intent = new Intent(this, RestartAlertActivity.class);
                break;

            case R.id.menu_edit:
                intent = new Intent(this, EditAlertActivity.class);
                String[] data = load();
                intent.putExtra("data", data);
                break;

            case R.id.menu_delete:
                intent = new Intent(this, DeleteAlertActivity.class);
                break;

            case R.id.menu_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
        }
        if (intent != null) {
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    // принятие результата с перехода диалоговых окон
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        } else if(data.getStringExtra("count") != null) {

            Log.d("myLog", "Обнуление счётчика завершено.");
            save(load()[0],data.getStringExtra("count"));
            countView.setText(prefs.getString("count", "0"));
        } else if(data.getStringArrayExtra("edit") != null) {
            String[] data_edit = data.getStringArrayExtra("edit");

            save(data_edit[0], data_edit[1]);

            setTitle(load()[0]);
            countView.setText(load()[1]);
        } else if (data.getStringArrayExtra("delete") != null) {
            String[] data_delete = data.getStringArrayExtra("delete");

            data_delete = delete(data_delete[0], data_delete[1]);

            setTitle(data_delete[0]);
            countView.setText(data_delete[1]);
        }
    }

    @SuppressLint("SetTextI18n")
    public static String[] delete (String name, String count) {
        save(name,count);
        dbHelper.saveCounter(database, id, load()[0], Integer.parseInt(load()[1]));
        return load();
    }

    // Использование аппартаных кнопок
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (prefs.getBoolean("counter_phone_check_box", false)) {
            if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
                onClick(buttonMinus);
                return true;
            } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
                onClick(buttonPlus);
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    static void save(String name, String count) {
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString("name", name);
        ed.putString("count", count);
        ed.apply();
    }

    static String[] load() {

        return new String[]{prefs.getString("name", "Counter"), prefs.getString("count", "0")};
    }
}