package com.example.planned;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TaskDetails extends AppCompatActivity {


    private EditText taskTitleInput, taskLabelInput;
    private Button saveTask, cancelTask;
    long activityId, id;
    Bundle extras;
    String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        taskTitleInput = findViewById(R.id.task_title_input);
        taskLabelInput = findViewById(R.id.task_label_input);
        saveTask = findViewById(R.id.save_task);
        cancelTask = findViewById(R.id.cancel_task);

        extras = getIntent().getExtras();
        action = extras.getString(TaskActivity.EXTRA_ACTION);
        activityId = extras.getLong(TaskActivity.EXTRA_ACTIVITY_ID);
        if(action.equals("edit")){
            id = extras.getLong(TaskActivity.EXTRA_TASK_ID);
            Task task = AppDatabase.getInstance(this).taskDao().getTaskById(id);
            taskTitleInput.setText(task.getTitle());
            taskLabelInput.setText(task.getLabel());
            saveTask.setText("UPDATE");
            saveTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = taskTitleInput.getText().toString();
                    String label = taskLabelInput.getText().toString();
                    AppDatabase.getInstance(TaskDetails.this).taskDao().update(id, title, label);
                    Intent intent = new Intent(TaskDetails.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            cancelTask.setText("DELETE");
            cancelTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppDatabase.getInstance(TaskDetails.this).taskDao().delete(task);
                    Intent intent = new Intent(TaskDetails.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    public void onSave(View view){
        Task task = new Task(taskTitleInput.getText().toString(), activityId, taskLabelInput.getText().toString());
        AppDatabase.getInstance(this).taskDao().add(task);
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(HomeFragment.EXTRA_ACTIVITY_ID, activityId);
        startActivity(intent);
    }

    public void onCancel(View view){
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }


}