package com.example.feedback1;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class ScoresActivity extends BaseActivity {
    RecyclerView mRecyclerView;
    private GameResultViewModel mGameResultViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        mRecyclerView = findViewById(R.id.recycler_view);

        final GameResultListAdapter adapter = new GameResultListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGameResultViewModel = ViewModelProviders.of(this).get(GameResultViewModel.class);
        mGameResultViewModel.getmAllResults().observe(this, new Observer<List<GameResult>>() {
            @Override
            public void onChanged(List<GameResult> gameResults) {
                adapter.setGameResults(gameResults);
            }
        });

        mGameResultViewModel.insert(12345);
        mGameResultViewModel.insert(12345);


//        List<GameResult> lol = (List<GameResult>) mGameResultViewModel.getmAllResults();
    }
}