package com.example.feedback1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "results_table")
public class GameResult {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    int score;
}
