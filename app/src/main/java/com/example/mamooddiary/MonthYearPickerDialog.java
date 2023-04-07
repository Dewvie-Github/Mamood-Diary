package com.example.mamooddiary;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import java.util.Calendar;

public class MonthYearPickerDialog {
    private final Context context;
    private final OnMonthYearSelectedListener listener;
    private final Calendar calendar;

    public MonthYearPickerDialog(Context context, OnMonthYearSelectedListener listener) {
        this.context = context;
        this.listener = listener;
        this.calendar = Calendar.getInstance();
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.month_year_picker_dialog, null);

        NumberPicker monthPicker = view.findViewById(R.id.month_picker);
        NumberPicker yearPicker = view.findViewById(R.id.year_picker);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(calendar.get(Calendar.MONTH) + 1);

        int currentYear = calendar.get(Calendar.YEAR);
        yearPicker.setMinValue(currentYear - 100);
        yearPicker.setMaxValue(currentYear + 100);
        yearPicker.setValue(currentYear);

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

