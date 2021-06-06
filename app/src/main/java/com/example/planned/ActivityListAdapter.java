package com.example.planned;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class ActivityListAdapter extends BaseAdapter {

    private Context context;
    private List<Activity> activityList;

    public ActivityListAdapter(Context context, List<Activity> activityList) {
        this.context=context;
        this.activityList=activityList;
    }

    @Override
    public int getCount() {
        return activityList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return activityList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.activity_list_items, parent, false);

        Activity activity = activityList.get(position);
        TextView itemTitle = convertView.findViewById(R.id.text_title);
        itemTitle.setText(activity.getTitle());
        ProgressBar progressBar = convertView.findViewById(R.id.progressBar);
        TextView progressValue = convertView.findViewById(R.id.progress_value);
        int noOfTasks = AppDatabase.getInstance(context).taskDao().getTasksForActivity(activity.getId()).size();
        int completedTasks = AppDatabase.getInstance(context).taskDao().getCompletedTasks().size();
        if (completedTasks>0){
            activity.setProgress(completedTasks/noOfTasks*100);
        }
        else{
            activity.setProgress(0);
        }
        String progress = String.valueOf(activity.getProgress());
        progressValue.setText(progress);
        progressBar.setProgress(activity.getProgress());

        return convertView;
    }
}
