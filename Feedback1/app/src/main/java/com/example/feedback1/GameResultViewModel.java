package com.example.feedback1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class GameResultViewModel extends AndroidViewModel {
    private GameResultRepository mRepository;
    private LiveData<List<GameResult>> mAllResults;
    public GameResultViewModel(Application application) {
       super(application);
       mRepository = new GameResultRepository(application);
       mAllResults = mRepository.getAllResults();
    }

    LiveData<List<GameResult>> getmAllResults() {
        return mAllResults;
    }

    public void insert(int score) {
        mRepository.insert(score);
    }
}
