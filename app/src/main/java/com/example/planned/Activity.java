package com.example.planned;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "activities")
public class Activity {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;

    private int progress;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Activity(String title) {
        this.title = title;
    }

    @Ignore
    public Activity(long id, String title) {
        this.id = id;
        this.title = title;
        this.progress = 0;
    }
}