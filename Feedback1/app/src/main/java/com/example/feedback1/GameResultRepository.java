package com.example.feedback1;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class GameResultRepository {
    private GameResultDAO mGameResultDAO;
    private LiveData<List<GameResult>> mAllResults;

    GameResultRepository(Application application) {
        GameResultRoomDatabase db = GameResultRoomDatabase.getDatabase(application);
        mGameResultDAO = db.GameResultDAO();
        mAllResults = mGameResultDAO.getAllResults();
    }
    LiveData<List<GameResult>> getAllResults() {
       return mAllResults;
    }

    public void insert (int score) {
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        mGameResultDAO.insert(score);
                    }
                }
        );
    }
}
