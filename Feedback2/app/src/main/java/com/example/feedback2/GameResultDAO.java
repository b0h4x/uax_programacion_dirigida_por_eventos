package com.example.feedback2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameResultDAO {
    @Insert
    void insert(GameResult result);

    // truncate table
    @Query("DELETE FROM results_table")
    void deleteAll();

    // get the results
    @Query("SELECT * FROM results_table")
    LiveData<List<GameResult>> getAllResults();
}
