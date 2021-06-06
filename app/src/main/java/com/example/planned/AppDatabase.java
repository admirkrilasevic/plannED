package com.example.planned;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Activity.class, Task.class}, version = 6, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ActivityDao activityDao();
    public abstract TaskDao taskDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context){
        if (INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "app_database").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }

}