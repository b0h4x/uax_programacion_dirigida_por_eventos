package com.example.feedback1;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {GameResult.class}, version = 1, exportSchema = false)
public abstract class GameResultRoomDatabase extends RoomDatabase {
    public abstract  GameResultDAO GameResultDAO();
    private static GameResultDAO INSTANCE;

    static GameResultRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (GameResultRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = (GameResultDAO) Room.databaseBuilder(context.getApplicationContext(),
                                    GameResultRoomDatabase.class, "game_result_database" )
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return (GameResultRoomDatabase) INSTANCE;
    }
}
