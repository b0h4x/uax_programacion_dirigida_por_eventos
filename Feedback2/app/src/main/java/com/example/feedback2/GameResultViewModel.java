package com.example.feedback2;

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

   void removeAll() {
        mRepository.removeAll();
   }

    public void insert(int score) {
        mRepository.insert(score);
    }
}
