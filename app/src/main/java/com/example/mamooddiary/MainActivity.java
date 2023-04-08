package com.example.mamooddiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, View.OnClickListener{

    private TextView monthTextview, yearTextview;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;
    private LocalDate oldDate;

    //ImageView previousButton, nextButton;
    Button btnBackToSplash;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
        mediaPlayer = MediaPlayer.create(this, R.raw.click);
        closeActionBar();


    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecycleView);

        monthTextview = findViewById(R.id.monthTextView);
        monthTextview.setOnClickListener(this);
        yearTextview = findViewById(R.id.yearTextview);

        btnBackToSplash = (Button) findViewById(R.id.btnBackToSplash);
        btnBackToSplash.setOnClickListener(this);

//        previousButton = findViewById(R.id.previousMonthButton);
//        nextButton = findViewById(R.id.nextMonthButton);
//        previousButton.setOnClickListener(this);
//        nextButton.setOnClickListener(this);

        // Add the swipe gesture listener to the calendar RecyclerView
        final GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureListener());
        calendarRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return gestureDetector.onTouchEvent(e);
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void setMonthView() {
        // set month and year text
        monthTextview.setText(selectedDate.getMonth().toString().toUpperCase());
        yearTextview.setText(String.valueOf(selectedDate.getYear()));

        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,
                selectedDate.getMonthValue() ,
                selectedDate.getYear(),
                this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

        startAnimation(selectedDate, oldDate);
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

    // when click on date on calendar
    @Override
    public void onItemClick(int position, String dayText, int day, int month, int year) {
        if(!dayText.equals("")) {
            Intent intent = new Intent(this, DiaryActivity.class);
            intent.putExtra("day", day );
            intent.putExtra("month", month);
            intent.putExtra("year", year);

            startActivity(intent);

            recreate();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.previousMonthButton:
//                oldDate = selectedDate;
//                selectedDate = selectedDate.minusMonths(1);
//                setMonthView();
//                break;
//            case R.id.nextMonthButton:
//                oldDate = selectedDate;
//                selectedDate = selectedDate.plusMonths(1);
//                setMonthView();
//                break;
            case R.id.btnBackToSplash:
                mediaPlayer.start();
                startActivity(new Intent(MainActivity.this, SplashActivity.class));
                mediaPlayer.release();
                finish();
                break;
            case R.id.monthTextView:
                showDatePickerDialog();
                break;
        }
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
                    oldDate = selectedDate;
                    selectedDate = LocalDate.of(year, month, 1);
                    setMonthView();
                }
        );

        // Show the MonthYearPickerDialog
        monthYearPickerDialog.show();
    }

    private void onSwipeLeft() {
        oldDate = selectedDate;
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    private void onSwipeRight() {
        oldDate = selectedDate;
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    class SwipeGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 100;
        private static final int SWIPE_MAX_OFF_PATH = 250;
        private static final int SWIPE_THRESHOLD_VELOCITY = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    private void startAnimation(LocalDate selectedDate, LocalDate oldDate) {
        // animation
        LinearLayout innerLayout = findViewById(R.id.calendarLayout);

        Animation slideIn, slideOut;

        if ( oldDate == null){
            innerLayout.startAnimation(AnimationUtils.loadAnimation(this, R.anim.popup));
            return;
        }
        else if(selectedDate != null && selectedDate.isBefore(oldDate)) {
            slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_right);
            slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        }
        else {
            slideIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
            slideOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_left);
            innerLayout.startAnimation(slideIn);
        }

        slideOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                calendarRecyclerView.setVisibility(View.VISIBLE);
                innerLayout.startAnimation(slideIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        calendarRecyclerView.setVisibility(View.INVISIBLE);
        innerLayout.startAnimation(slideOut);
    }
}


