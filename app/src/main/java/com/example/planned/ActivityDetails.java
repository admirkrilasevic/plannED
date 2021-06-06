package com.example.planned;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityDetails extends AppCompatActivity {

    private EditText titleInput;
    private Button save, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleInput = findViewById(R.id.title_input);
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            long id = extras.getLong(HomeFragment.EXTRA_ACTIVITY_ID);
            Activity activity = AppDatabase.getInstance(this).activityDao().getActivityById(id);
            titleInput.setText(activity.getTitle());
            save.setText("UPDATE");
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = titleInput.getText().toString();
                    AppDatabase.getInstance(ActivityDetails.this).activityDao().update(id, title);
                    Intent intent = new Intent(ActivityDetails.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            cancel.setText("DELETE");
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppDatabase.getInstance(ActivityDetails.this).activityDao().delete(activity);
                    Intent intent = new Intent(ActivityDetails.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void onSave(View view){
        Activity activity = new Activity(titleInput.getText().toString());
        AppDatabase.getInstance(this).activityDao().add(activity);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onCancel(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}