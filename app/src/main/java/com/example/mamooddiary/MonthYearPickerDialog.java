package com.example.mamooddiary;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.lang.reflect.Field;
import java.text.DateFormatSymbols;
import java.util.Calendar;

public class MonthYearPickerDialog {
    private final Context context;
    private final OnMonthYearSelectedListener listener;
    private final Calendar calendar;
    private final String[] monthNames;

    public MonthYearPickerDialog(Context context, OnMonthYearSelectedListener listener) {
        this.context = context;
        this.listener = listener;
        this.calendar = Calendar.getInstance();
        this.monthNames = new DateFormatSymbols().getMonths();
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.month_year_picker_dialog, null);

        NumberPicker monthPicker = view.findViewById(R.id.month_picker);
        NumberPicker yearPicker = view.findViewById(R.id.year_picker);

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setDisplayedValues(monthNames);

        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        yearPicker.setMinValue(currentYear - 100);
        yearPicker.setMaxValue(currentYear);
        yearPicker.setValue(currentYear);

        // Set the max value of the monthPicker based on the selected year
        yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (newVal == currentYear) {
                monthPicker.setMaxValue(currentMonth);
            } else {
                monthPicker.setMaxValue(11);
            }
        });

        // Initialize the monthPicker based on the current date
        if (yearPicker.getValue() == currentYear) {
            monthPicker.setMaxValue(currentMonth);
            monthPicker.setValue(currentMonth);
        } else {
            monthPicker.setMaxValue(11);
            monthPicker.setValue(0);
        }

        builder.setView(view)
                .setPositiveButton("OK", (dialog, which) -> {
                    int selectedMonth = monthPicker.getValue();
                    int selectedYear = yearPicker.getValue();
                    listener.onMonthYearSelected(selectedMonth, selectedYear);

                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    public interface OnMonthYearSelectedListener {
        void onMonthYearSelected(int month, int year);
    }

}

