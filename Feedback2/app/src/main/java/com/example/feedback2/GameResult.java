package com.example.feedback2;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "results_table")
public class GameResult {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    int score;

    public GameResult(int score) {
        this.score = score;
    }

    public int getScore() {return this.score;}
}
