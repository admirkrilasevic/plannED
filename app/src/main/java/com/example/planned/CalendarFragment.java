package com.example.planned;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class CalendarFragment extends Fragment {

    CalendarView calendar;
    TextView tasks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar = v.findViewById(R.id.calendarView);
        tasks = v.findViewById(R.id.tasks_calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month++;
                String date = dayOfMonth + "/" + month + "/" + year;
                List<Task> taskList = AppDatabase.getInstance(getActivity().getBaseContext()).taskDao().getTasksByDate(date);

                StringBuilder sb = new StringBuilder();
                for (Task t : taskList)
                {
                    String title = t.getTitle();
                    String deadlineTime = t.getDeadlineTime();
                    String deadlineDate = t.getDeadlineDate();
                    String taskDetails = title + " " + deadlineTime + " " + deadlineDate;

                    sb.append(taskDetails);
                    sb.append("\n\n");
                }
                tasks.setText(sb.toString());


            }
        });

        return v;
    }
}
