package com.example.feedback2;

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

    void removeAll() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                mGameResultDAO.deleteAll();
            }
        });
        thread.start();
    }

    public void insert (int score) {
        Thread thread = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        GameResult result = new GameResult(score);
                        mGameResultDAO.insert(result);
                    }
                }
        );
        thread.start();
    }
}
