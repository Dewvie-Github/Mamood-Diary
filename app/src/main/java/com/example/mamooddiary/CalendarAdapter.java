package com.example.mamooddiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<String> dayOfMonth;
    int counterDateOfMonth;
    private final int monthOfDay;
    private final int yearOfDay;
    private final OnItemListener onItemListener;


    public CalendarAdapter(ArrayList<String> dayOfMonth,
                           int monthOfDay,
                           int yearOfDay,
                           OnItemListener onItemListener) {
        this.dayOfMonth = dayOfMonth;
        this.monthOfDay = monthOfDay;
        this.yearOfDay = yearOfDay;
        this.onItemListener = onItemListener;
        counterDateOfMonth = 0;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Retrieve the day value based on the position
        String dayStr = dayOfMonth.get(counterDateOfMonth++);
        int day = dayStr.equals("") ? -1 : Integer.parseInt(dayStr);

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);

        return new CalendarViewHolder(view, onItemListener, day, monthOfDay, yearOfDay);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(dayOfMonth.get(position));
    }

    @Override
    public int getItemCount() {
        return dayOfMonth.size();
    }
    public interface OnItemListener{
        void onItemClick(int position, String dayText);
    }
}
