package com.example.mamooddiary;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView dayOfMonth;
    public View parentLayout;
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
            parentLayout = itemView.findViewById(R.id.parent_empty_layout);
            dayOfMonth = itemView.findViewById(R.id.cellEmptyDayText);;
        }
        // default day
        else if ( !isDairyDay(day, monthOfDay, yearOfDay)){
            parentLayout = itemView.findViewById(R.id.parent_default_layout);
            dayOfMonth = itemView.findViewById(R.id.cellDefaultDayText);
            itemView.setOnClickListener(this);
        }
        switch (getMoodTypeByDate(parentLayout.getContext() ,day,monthOfDay,yearOfDay)){
            case "happy":
                parentLayout = itemView.findViewById(R.id.parent_happy_layout);
                dayOfMonth = itemView.findViewById(R.id.cellHappyDayText);
                itemView.setOnClickListener(this);
                break;
            case "normal":
                parentLayout = itemView.findViewById(R.id.parent_normal_layout);
                dayOfMonth = itemView.findViewById(R.id.cellNormalDayText);
                itemView.setOnClickListener(this);
                break;
            case "notok":
                parentLayout = itemView.findViewById(R.id.parent_notok_layout);
                dayOfMonth = itemView.findViewById(R.id.cellNotokDayText);
                itemView.setOnClickListener(this);
                break;
            case "sad":
                parentLayout = itemView.findViewById(R.id.parent_sad_layout);
                dayOfMonth = itemView.findViewById(R.id.cellSadDayText);
                itemView.setOnClickListener(this);
                break;
            case "smile":
                parentLayout = itemView.findViewById(R.id.parent_smile_layout);
                dayOfMonth = itemView.findViewById(R.id.cellSmileDayText);
                itemView.setOnClickListener(this);
                break;
        }

        // System.out.println("Day:" + day + " WOW: " + parentLayout.getContext().getResources().getResourceEntryName((dayOfMonth.getId())));
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(
                getAdapterPosition(),
                (String) dayOfMonth.getText(),
                day, monthOfDay, yearOfDay );
    }

    public static boolean isDairyDay(int day, int monthOfDay, int yearOfDay) {
        // this is sql but now it mockup
        // like SELECT * FROM my_table WHERE day = day AND month = monthOfDay AND year = yearOfDay;
        for (MockNote note : MockData.getMockNotes()) {
            if (note.getDay() == day && note.getMonth() == monthOfDay && note.getYear() == yearOfDay) {
                return true;
            }
        }
        return false;
    }

    public static String getMoodTypeByDate(Context context, int day, int monthOfDay, int yearOfDay) {
        String mood = "";

        DBHelper dbHelper = new DBHelper(context);

        mood = dbHelper.selectMood(String.valueOf(day), String.valueOf(monthOfDay),String.valueOf(yearOfDay));

        return mood;
    }
}
