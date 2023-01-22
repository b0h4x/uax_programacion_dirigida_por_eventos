package com.example.feedback1;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    boolean nightModeEnabled;
    SharedPreferences mSharedPreferences;
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String LANG = "LANGUAGE";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Set correct NightMode (after inflation and onCreate
        // which sets the corrects variables from SharedPreferences
        setNightMode(nightModeEnabled, menu.findItem(R.id.night_mode));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case (R.id.night_mode):
                nightModeEnabled = !nightModeEnabled;
                SharedPreferences.Editor mSharedPreferencesEditor = mSharedPreferences.edit();
                mSharedPreferencesEditor.putBoolean(NIGHT_MODE, nightModeEnabled);
                mSharedPreferencesEditor.commit();
                setNightMode(nightModeEnabled, item);
                break;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get SharedPreferences for lang and nightmode
        mSharedPreferences = getDefaultSharedPreferences(this);
        nightModeEnabled = mSharedPreferences.getBoolean(NIGHT_MODE, false);
        mSharedPreferences.getString(LANG, "en");
    }

    private void setNightMode(boolean nightModeEnabled, MenuItem menuItem) {
        if (nightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            menuItem.setIcon(R.drawable.light_mode);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            menuItem.setIcon(R.drawable.night_mode);
        }
    }
}