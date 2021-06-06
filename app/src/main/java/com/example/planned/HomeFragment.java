package com.example.planned;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class HomeFragment extends Fragment {

    public static final String EXTRA_ACTIVITY_ID="HomeFragment/EXTRA_ACTIVITY_ID";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ListView listView = (ListView)v.findViewById(R.id.list_view_container);
        List<Activity> activities = AppDatabase.getInstance(getActivity().getBaseContext()).activityDao().getAll();
        ActivityListAdapter adapter = new ActivityListAdapter(getActivity().getBaseContext(), activities);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long activityId = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(getActivity().getBaseContext(), TaskActivity.class);
                intent.putExtra(EXTRA_ACTIVITY_ID, activityId);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                long activityId = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(getActivity().getBaseContext(), ActivityDetails.class);
                intent.putExtra(EXTRA_ACTIVITY_ID, activityId);
                startActivity(intent);
                return true;
            }
        });
        return v;
    }


}
