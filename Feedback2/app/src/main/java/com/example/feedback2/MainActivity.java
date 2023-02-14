package com.example.feedback2;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.Manifest;

import com.example.feedback2.ui.login.LoginActivity;

public class MainActivity extends BaseActivity {
    Button mButtonMaps;

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

        if (ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            Log.d("jejeje","aaa");
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }
}