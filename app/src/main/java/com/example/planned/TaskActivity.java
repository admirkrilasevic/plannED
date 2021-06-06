package com.example.planned;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class TaskActivity extends AppCompatActivity {

    public static final String EXTRA_TASK_ID="TaskActivity/EXTRA_TASK_ID";
    public static final String EXTRA_ACTIVITY_ID="TaskActivity/EXTRA_ACTIVITY_ID";
    public static final String EXTRA_ACTION="TaskActivity/EXTRA_ACTION";
    private ListView listView;
    long id;
    Bundle extras;
    TextView activityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        extras = getIntent().getExtras();
        id = extras.getLong(HomeFragment.EXTRA_ACTIVITY_ID);

        listView = findViewById(R.id.list_view_container_tasks);
        setUpListAdapter();


        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText(AppDatabase.getInstance(this).activityDao().getActivityById(id).getTitle());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long taskId = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(TaskActivity.this, TaskDetails.class);
                intent.putExtra(EXTRA_TASK_ID, taskId);
                intent.putExtra(EXTRA_ACTION, "edit");
                startActivity(intent);
            }
        });
    }

    private void setUpListAdapter(){

        List<Task> tasks = AppDatabase.getInstance(this).taskDao().getTasksForActivity(id);

        TaskListAdapter adapter = new TaskListAdapter(this, tasks);
        listView.setAdapter(adapter);
    }

    public void onAddClick(View view){
        Intent intent = new Intent(this, TaskDetails.class);
        intent.putExtra(EXTRA_ACTIVITY_ID, id);
        intent.putExtra(EXTRA_ACTION, "add");
        startActivity(intent);
    }

    public void onBackClick(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onRefreshClick(View view){
        Intent intent = new Intent(this, TaskActivity.class);
        intent.putExtra(HomeFragment.EXTRA_ACTIVITY_ID, id);
        startActivity(intent);
    }

}