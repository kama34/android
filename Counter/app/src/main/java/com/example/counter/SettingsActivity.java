package com.example.counter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences prefs;
    String value;

    int i = 0;

    @SuppressLint("CommitTransaction")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        Context context = getApplicationContext();
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        value = prefs.getString("style_list", "Светлый");

        if (value.equals("Светлый")) {
            setTheme(R.style.Theme_Counter);
        } else if (value.equals("Темный")) {
            setTheme(R.style.Theme_AppCompat);
        } else {
            setTheme(R.style.Theme_AppCompat_DayNight);
        }
        super.onCreate(savedInstanceState);

        prefs.registerOnSharedPreferenceChangeListener(this);

        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Настройки");
        }

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("style_list") && prefs.getString("check", "false").equals("false")) {
            prefs.unregisterOnSharedPreferenceChangeListener(this);
            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = prefs.edit();
            editor.putString("check", "true");
            editor.apply();

            i++;
            Intent intent2 = getIntent();
            finish();
            startActivity(intent2);
        }

        if (sharedPreferences.getBoolean("display_on_check_box", false)) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        if (key.equals("settings_delete")) {

        }
    }


}