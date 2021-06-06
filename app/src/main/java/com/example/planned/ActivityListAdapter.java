package com.example.planned;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
        ImageView image = convertView.findViewById(R.id.image);
        image.setImageResource(R.drawable.activity);
        itemTitle.setText(activity.getTitle());

        return convertView;
    }
}
