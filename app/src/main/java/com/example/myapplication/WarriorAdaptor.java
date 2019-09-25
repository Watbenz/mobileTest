package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class WarriorAdaptor extends ArrayAdapter<Warrior> {
    private Context context;
    private int resource;
    private List<Warrior> arr;

    public WarriorAdaptor(Context context, int resource, List<Warrior> arr) {
        super(context, resource, arr);
        this.context = context;
        this.resource = resource;
        this.arr = arr;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(resource, null);
        }

        Warrior warrior = arr.get(position);

        TextView textView1 = convertView.findViewById(R.id.textView1);
        TextView textView2 = convertView.findViewById(R.id.textView2);

        @SuppressLint("DefaultLocale") String out = String.format("HP: %5d  ATK: %5d", warrior.getHp(), warrior.getAtk());
        textView1.setText(warrior.getName());
        textView2.setText(out);

        return convertView;
    }
}
