package com.example.planned;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class CategoriesFragment extends Fragment {

    private List<Task> tasks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);

        ListView listView = (ListView)v.findViewById(R.id.list_view_container_label);
        Button exams = v.findViewById(R.id.exams);
        Button studying = v.findViewById(R.id.studying);
        Button assignments = v.findViewById(R.id.assignments);
        Button other = v.findViewById(R.id.other);

        exams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks = AppDatabase.getInstance(getActivity().getBaseContext()).taskDao().getTasksByLabel("exam");
                LabelListAdapter adapter = new LabelListAdapter(getActivity().getBaseContext(), tasks);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        studying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks = AppDatabase.getInstance(getActivity().getBaseContext()).taskDao().getTasksByLabel("studying");
                LabelListAdapter adapter = new LabelListAdapter(getActivity().getBaseContext(), tasks);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks = AppDatabase.getInstance(getActivity().getBaseContext()).taskDao().getTasksByLabel("assignment");
                LabelListAdapter adapter = new LabelListAdapter(getActivity().getBaseContext(), tasks);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks = AppDatabase.getInstance(getActivity().getBaseContext()).taskDao().getTasksByLabel("other");
                LabelListAdapter adapter = new LabelListAdapter(getActivity().getBaseContext(), tasks);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        return v;
    }
}
