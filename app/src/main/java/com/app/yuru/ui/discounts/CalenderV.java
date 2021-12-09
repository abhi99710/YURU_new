package com.app.yuru.ui.discounts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;

import android.os.Bundle;

import com.app.yuru.R;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalenderV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_v);

        List<EventDay> events = new ArrayList<>();

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();




//        events.add(new EventDay(calendar, R.drawable.sample_icon));
//or
//        events.add(new EventDay(calendar, new Drawable()));
//or if you want to specify event label color
//        events.add(new EventDay(calendar, R.drawable.sample_icon, Color.parseColor("#228B22")));

//        calendarView.setDate(calendar);
//        calendarView.setEvents(events);

        calendar.set(2021, 12, 8);

        try {
            calendarView.setDate(calendar);
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        calendar.set(2021, 12, 7);
        List<Calendar> highlighted = new ArrayList<>();
        highlighted.add(calendar);
        calendarView.setSelectedDates(highlighted);


    }
}