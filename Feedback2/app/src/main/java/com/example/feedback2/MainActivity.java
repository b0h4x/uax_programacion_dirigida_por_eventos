package com.example.feedback2;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

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
        Button buttonResults = (Button)findViewById(R.id.button_results);
        buttonResults.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScoresActivity.class);
                startActivity(intent);
            }
        });
    }
}