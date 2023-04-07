package com.example.mamooddiary;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView dayOfMonth;
    public final View parentLayout;
    public ImageView image;

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


        if (day == -1){
            parentLayout = itemView.findViewById(R.id.parent_default_layout);
            dayOfMonth = itemView.findViewById(R.id.cellDefaultDayText);;
        }
        // Normal day
        else if ( !isNotedDay(day, monthOfDay, yearOfDay)){
            parentLayout = itemView.findViewById(R.id.parent_layout);
            dayOfMonth = itemView.findViewById(R.id.cellDayText);
            itemView.setOnClickListener(this);
        }
        // Noted happy day
        else{
            parentLayout = itemView.findViewById(R.id.parent_happy_layout);
            dayOfMonth = itemView.findViewById(R.id.cellHappyDayText);
            itemView.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(
                getAdapterPosition(),
                (String) dayOfMonth.getText(),
                day, monthOfDay, yearOfDay );
    }


    public static boolean isNotedDay(int day, int monthOfDay, int yearOfDay) {
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

    public static String getMoodTypeByDate(int day, int monthOfDay, int yearOfDay) {
        String mood = "";

        for (MockNote note : MockData.getMockNotes()) {
            if (note.getDay() == day && note.getMonth() == monthOfDay && note.getYear() == yearOfDay) {
                mood = note.getMood();
            }
        }

        return mood;
    }
}
