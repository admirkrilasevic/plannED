package com.example.planned;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void add(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();

    @Query("SELECT * FROM tasks WHERE activityId=:activityId")
    List<Task> getTasksForActivity(long activityId);

    @Query("SELECT * FROM tasks WHERE id=:id LIMIT 1")
    Task getTaskById(long id);

    @Query("UPDATE tasks SET title = :title, label = :label WHERE id =:id")
    void update(long id, String title, String label);

    @Query("UPDATE tasks SET deadlineTime = :time WHERE id =:id")
    void updateDeadlineTime(long id, String time);

    @Query("UPDATE tasks SET deadlineDate = :date WHERE id =:id")
    void updateDeadlineDate(long id, String date);

    @Query("SELECT * FROM tasks WHERE label = :label")
    List<Task> getTasksByLabel(String label);

    @Query("SELECT * FROM tasks WHERE deadlineDate=:date")
    List<Task> getTasksByDate(String date);

    @Query("SELECT * FROM tasks WHERE completed=1")
    List<Task> getCompletedTasks();

    @Query("UPDATE tasks SET completed = 1 WHERE id=:id")
    void complete(long id);

    @Delete
    void delete(Task task);
}
