package com.example.planned;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskListAdapter extends BaseAdapter {

    int setYear, setMonth, setDay, setHour, setMinute;
    private Context context;
    private List<Task> taskList;

    public TaskListAdapter(Context context, List<Task> taskList) {
        this.context=context;
        this.taskList=taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return taskList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.task_list_items, parent, false);

        Task task = taskList.get(position);
        TextView taskTitle = convertView.findViewById(R.id.task_title);
        taskTitle.setText(task.getTitle());
        String label = task.getLabel();
        switch(label){
            case "exam":
                taskTitle.setTextColor(Color.RED);
                break;
            case "studying":
                taskTitle.setTextColor(Color.YELLOW);
                break;
            case "assignment":
                taskTitle.setTextColor(Color.BLUE);
                break;
            default:
                break;
        }
        TextView deadlineTime = convertView.findViewById(R.id.deadline_time);
        deadlineTime.setText(task.getDeadlineTime());
        TextView deadlineDate = convertView.findViewById(R.id.deadline_date);
        deadlineDate.setText(task.getDeadlineDate());
        Button setDeadline = convertView.findViewById(R.id.time_button);
        Button setReminder = convertView.findViewById(R.id.reminder_button);
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    AppDatabase.getInstance(context).taskDao().delete(task);
                    //refresh
                }
            }
        });

        setDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                setHour = hourOfDay;
                                setMinute = minute;
                                String timeSet = String.format(Locale.getDefault(), "%02d:%02d", setHour, setMinute);
                                Toast.makeText(context, "Deadline set for " + timeSet, Toast.LENGTH_LONG).show();
                                AppDatabase.getInstance(context).taskDao().updateDeadlineTime(task.getId(), timeSet);
                                deadlineTime.setText(task.getDeadlineTime());
                                //refresh

                            }
                        }, cal.getTime().getHours(), cal.getTime().getMinutes(), true
                );
                timePickerDialog.setTitle("Select time: ");
                timePickerDialog.show();

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        context,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                setYear = year;
                                setMonth = month + 1;
                                setDay = dayOfMonth;
                                String dateSet = setDay + "/" + setMonth + "/" + setYear;
                                Toast.makeText(context, "Deadline set for " + dateSet, Toast.LENGTH_LONG).show();
                                AppDatabase.getInstance(context).taskDao().updateDeadlineDate(task.getId(), dateSet);
                                deadlineDate.setText(task.getDeadlineDate());
                                //refresh
                            }
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.setTitle("Select date: ");
                datePickerDialog.show();

            }
        });

        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Select reminder time: ").setCancelable(true).setItems(new String[]{"10 minutes before", "1 hour before", "1 day before"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialoginterface, int i) {
                                switch (i){
                                    case 0:
                                        reminderFunction(600000, context, task);

                                        break;
                                    case 1:
                                        reminderFunction(3600000, context, task);
                                        break;
                                    case 2:
                                        reminderFunction(86400000, context, task);
                                        break;
                                    default:
                                        break;
                                }
                            }
                        }
                )
                        .show();
            }
        });

        return convertView;
    }

    public void reminderFunction(long timeBefore, Context context, Task task){
        Toast.makeText(context, "Reminder set", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(context, ReminderBroadcast.class);
        intent.putExtra("taskTitle", task.getTitle());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        String dateString = task.getDeadlineTime() +" "+ task.getDeadlineDate();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        try {
            Date date = sdf.parse(dateString);
            long timeInMiliseconds = date.getTime();
            timeInMiliseconds -= timeBefore;
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMiliseconds, pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}