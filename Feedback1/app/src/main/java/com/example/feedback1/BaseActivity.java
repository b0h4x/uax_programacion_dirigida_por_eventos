package com.example.feedback1;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.io.Console;
import java.util.List;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    boolean nightModeEnabled;
    String language;
    SharedPreferences mSharedPreferences;
    static final String NIGHT_MODE = "NIGHT_MODE";
    GameResultViewModel mGameResultViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // Get SharedPreferences for lang and nightmode
        mSharedPreferences = getDefaultSharedPreferences(this);
        nightModeEnabled = mSharedPreferences.getBoolean(NIGHT_MODE, false);
        Locale currentLocale = Locale.getDefault();
        language = currentLocale.getLanguage();
        super.onCreate(savedInstanceState);
        mGameResultViewModel = ViewModelProviders.of(this).get(GameResultViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Set correct NightMode (after inflation and onCreate
        // which sets the corrects variables from SharedPreferences
        setNightMode(nightModeEnabled, menu.findItem(R.id.night_mode));
        setLangIcon(language, menu.findItem(R.id.lang_mode));
        if (!getClass().getSimpleName().equals("ScoresActivity")) {
            menu.findItem(R.id.delete_icon).setVisible(false);
        }
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
                infoLang(language, item);
                break;
            case (R.id.delete_icon):
                mGameResultViewModel.removeAll();
                break;
            case (R.id.about):
                AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
                builder.setMessage(R.string.dialog_about_message)
                        .setTitle(R.string.about_dialog_title)
                        .setCancelable(true)
                        .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

    // modo nocturno
    private void setNightMode(boolean nightModeEnabled, MenuItem menuItem) {
        if (nightModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            menuItem.setIcon(R.drawable.light_mode);
            menuItem.setTitle(R.string.light_mode);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            menuItem.setIcon(R.drawable.night_mode);
            menuItem.setTitle(R.string.night_mode);
        }
    }

    private void setLangIcon(String language, MenuItem item) {
        if (language.equals("es")) {
            item.setIcon(R.drawable.en);
        } else if (language.equals("en")) {
            item.setIcon(R.drawable.es);
        }
    }

    // programmatically no he podido/sabido hacerlo, parece que es bastante más complicado de lo que
    // parece, y está bastante desaconsejado tocar los locales así
    private void infoLang(String language, MenuItem menuItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BaseActivity.this);
        builder.setMessage(R.string.info_lang)
                .setTitle(R.string.info_popup)
                .setCancelable(true)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        }
}
