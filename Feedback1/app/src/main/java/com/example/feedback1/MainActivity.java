package com.example.feedback1;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    boolean nightModeEnabled;
    String language;
    SharedPreferences mSharedPreferences;
    private static final String NIGHT_MODE = "NIGHT_MODE";
    private static final String LANG = "LANGUAGE";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Set correct NightMode (after inflation and onCreate
        // which sets the corrects variables from SharedPreferences
        setNightMode(nightModeEnabled, menu.findItem(R.id.night_mode));
        setLang(language, menu.findItem(R.id.lang_mode));
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
            case (R.id.lang_mode):
                language = language=="en"?"es":"en";
                mSharedPreferencesEditor = mSharedPreferences.edit();
                mSharedPreferencesEditor.putString (LANG, language);
                mSharedPreferencesEditor.commit();
                setLang(language, item);

               // setContentView(R.layout.activity_main);
                restartActivity();
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get SharedPreferences for lang and nightmode
        mSharedPreferences = getDefaultSharedPreferences(this);
        nightModeEnabled = mSharedPreferences.getBoolean(NIGHT_MODE, false);
        language = mSharedPreferences.getString(LANG, "en");

        // Inflate
        setContentView(R.layout.activity_main);
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

    private void setLang(String language, MenuItem menuItem) {
        if (language == "es") {
            menuItem.setIcon(R.drawable.en);
        } else if (language == "en") {
            menuItem.setIcon(R.drawable.es);
        }
        // Set lang as default
        Locale myLocale = new Locale(language);
        Configuration config = new Configuration();
        config.locale = myLocale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void restartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}