package com.example.feedback2;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.feedback2.ui.login.LoginActivity;

import java.util.Date;

public class MainActivity extends BaseActivity {
    Button mButtonMaps;
    ImageView mLogoView;
    TextView mTextView;
    TextView mDate;
    Button mButtonTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate
        setContentView(R.layout.activity_main);

        // Game Button
        Button button= (Button)findViewById(R.id.button_start_game);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.game_instructions_text)
                       .setTitle(R.string.game_instructions_title)
                       .setCancelable(true)
                       .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               Intent intent = new Intent(MainActivity.this, Game.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                               startActivity(intent);
                           }
                       });
               AlertDialog alert = builder.create();
               alert.show();
            }
        });

        // Results button
        Button mButtonResults = (Button)findViewById(R.id.button_results);
        mButtonResults.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScoresActivity.class);
                startActivity(intent);
            }
        });

        // Login button
        Button mLogginButton = (Button)findViewById(R.id.button_log_in);
        mLogginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // Maps button
        mButtonMaps = (Button) findViewById(R.id.button_maps);
        mButtonMaps.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                    }
                }
        );
        ActivityResultLauncher<String> requestPermissionLauncher =
                registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                    if (isGranted) {
                        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                        startActivity(intent);
                    } else {
                        Log.d("fail", "faili");
                    }
                });

        // Tabs button
        mButtonTabs = findViewById(R.id.button_tabs);
        mButtonTabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TabbedActivity.class);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        String logoPreference = sharedPreferences.getString("logo", "");
        // Check logo preference
        if (logoPreference.equals("setter")) {
            mLogoView = findViewById(R.id.imageView);
            mLogoView.setImageResource(R.drawable.dog);
        }
        // Check signature preference
        String signaturePreference = sharedPreferences.getString("signature", "");
        if (!signaturePreference.equals("")) {
            mTextView = findViewById(R.id.signatureText);
            mTextView.setText("Your signature is: " + signaturePreference);
        }
        // Check date preference
        Boolean datePreference = sharedPreferences.getBoolean("date", false);
        if (datePreference) {
            Date date = new Date();
            mDate = findViewById(R.id.date_text);
            mDate.setText("Date is: " + date.toString());
        }

    }
}