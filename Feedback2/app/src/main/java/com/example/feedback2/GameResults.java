package com.example.feedback2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameResults extends AppCompatActivity {
    int score;
    TextView mTextView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);
        score = getIntent().getIntExtra("score", 0);
        mTextView = findViewById(R.id.score_result);
        mTextView.append(String.valueOf(score));
        button = findViewById(R.id.finish_game);
        button.setOnClickListener(
                new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    finish();
                  }
              }
        );
    }
}