package com.example.mamooddiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, View.OnClickListener{
    private TextView monthTextview;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    ImageView previousButton, nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
        closeActionBar();
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecycleView);

        monthTextview = findViewById(R.id.monthTextView);
        monthTextview.setOnClickListener(this);
        previousButton = findViewById(R.id.previousMonthButton);
        nextButton = findViewById(R.id.nextMonthButton);
        previousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

    private void setMonthView() {
        // set month text
        monthTextview.setText(selectedDate.getMonth().toString().toUpperCase());
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,
                selectedDate.getMonthValue() ,
                selectedDate.getYear(),
                this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // this return specify position day in calendar
    // If first day of month day is WED
    // return data is { "", "" , "", 1, 2, 3 ... ,31, "" ...}
    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        // This return position of day. If not right position it will be "". for example
        // date 1 in march 2023 should be at WED. but the the table start with SUN
        // so when it not right. It add "" to array.
        for(int i = 1; i <= 42; i++) {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            }
            else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }


    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @Override
    public void onItemClick(int position, String dayText, int day, int month, int year) {
        if(!dayText.equals("")) {
            startActivity(startDiaryActivity(day, month, year));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previousMonthButton:
                selectedDate = selectedDate.minusMonths(1);
                setMonthView();
                break;
            case R.id.nextMonthButton:
                selectedDate = selectedDate.plusMonths(1);
                setMonthView();
                break;
            case R.id.monthTextView:
                showDatePickerDialog();
                break;
        }
    }

    private Intent startDiaryActivity(int day, int month, int year){
        Intent intent = new Intent(this, DiaryActivity.class);
        intent.putExtra("day", day );
        intent.putExtra("month", month);
        intent.putExtra("year", year);
        return intent;
    }

    public void closeActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onBackPressed(){

    }

    private void showDatePickerDialog() {
        MonthYearPickerDialog monthYearPickerDialog = new MonthYearPickerDialog(
                this,
                (month, year) -> {
                    // Update the selectedDate with the selected month and year
                    selectedDate = LocalDate.of(year, month, 1);
                    setMonthView();
                }
        );

        // Show the MonthYearPickerDialog
        monthYearPickerDialog.show();
    }



}