package com.example.counter;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    Preference preference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_preference);

        /* get preference */
        preference = findPreference("style_list");
        preference.setSummary(((ListPreference) preference).getEntry());
        SharedPreferences.Editor editor = preference.getEditor();
        editor.putString("check", "false");
        editor.commit();

        Preference button = findPreference("export_preference");
        button.setOnPreferenceClickListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        /* update summary */
        if (key.equals("style_list")) {
            preference.setSummary(((ListPreference) preference).getEntry());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {

        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("application/message/rfc822");
        email.putExtra(Intent.EXTRA_TEXT, MainActivity.load()[0] + "," + MainActivity.load()[1] + "\n");

        startActivity(Intent.createChooser(email, "Send email"));

        return true;
    }
}