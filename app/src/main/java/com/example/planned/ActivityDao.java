package com.example.planned;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ActivityDao {

    @Insert
    void add(Activity activity);

    @Query("SELECT * FROM activities")
    List<Activity> getAll();

    @Query("SELECT * FROM activities WHERE id=:id LIMIT 1")
    Activity getActivityById(long id);

    @Query("UPDATE activities SET title = :title WHERE id =:id")
    void update(long id, String title);

    @Delete
    void delete(Activity activity);
}
