package com.example.mamooddiary;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView dayOfMonth;
    public final View parentLayout;

    private int day;
    private int monthOfDay;
    private int yearOfDay;

    private final CalendarAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView,
                              CalendarAdapter.OnItemListener onItemListener,
                              int day,
                              int monthOfDay,
                              int yearOfDay) {
        super(itemView);

        this.onItemListener = onItemListener;
        this.day = day;
        this.monthOfDay = monthOfDay;
        this.yearOfDay = yearOfDay;

        parentLayout = itemView.findViewById(R.id.parent_layout);

        // Normal day
        if ( day == -1 || !isNotedDay(day, monthOfDay, yearOfDay)){
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
        }
        // Noted day
        else{
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            parentLayout.setBackgroundColor(Color.RED);
        }
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(
                getAdapterPosition(),
                (String) dayOfMonth.getText(),
                day, monthOfDay, yearOfDay );
    }


    private boolean isNotedDay(int day, int monthOfDay, int yearOfDay) {
        // this is sql but now it mockup
        // like SELECT * FROM my_table WHERE day = day AND month = monthOfDay AND year = yearOfDay;
        for (MockNote note : MockData.getMockNotes()) {
            if (note.getDay() == day && note.getMonth() == monthOfDay && note.getYear() == yearOfDay) {
                System.out.println(true);
                return true;
            }
        }
        return false;
    }
}
