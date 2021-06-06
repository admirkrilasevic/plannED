package com.example.planned;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class LabelListAdapter extends BaseAdapter {

    private Context context;
    private List<Task> taskList;

    public LabelListAdapter(Context context, List<Task> taskList) {
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
        convertView = inflater.inflate(R.layout.label_list_items, parent, false);

        Task task = taskList.get(position);
        TextView title, time, date;
        title = convertView.findViewById(R.id.task_title_label);
        time = convertView.findViewById(R.id.deadline_time_label);
        date = convertView.findViewById(R.id.deadline_date_label);
        title.setText(task.getTitle());
        time.setText(task.getDeadlineTime());
        date.setText(task.getDeadlineDate());

        return convertView;
    }
}
