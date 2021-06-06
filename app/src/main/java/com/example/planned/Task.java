package com.example.planned;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks", foreignKeys = @ForeignKey(entity=Activity.class, parentColumns="id", childColumns="activityId"))
public class Task {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long activityId;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    private String title;

    private String deadlineTime;

    private String deadlineDate;

    private String label;

    private int completed;

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDeadlineTime() {
        return deadlineTime;
    }

    public void setDeadlineTime(String deadlineTime) {
        this.deadlineTime = deadlineTime;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Task(String title, long activityId, String label) {
        this.title = title;
        this.activityId = activityId;
        this.deadlineDate = null;
        this.deadlineTime = null;
        this.completed = 0;
        this.label = label;
    }

    @Ignore
    public Task(String title, String deadlineDate, String deadlineTime) {
        this.title = title;
        this.deadlineDate = deadlineDate;
        this.deadlineTime = deadlineTime;
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

}
