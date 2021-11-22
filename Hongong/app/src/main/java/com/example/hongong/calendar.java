package com.example.hongong;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class calendar extends MainActivity{
    CalendarView cv;
    TextView date;
    Button todoadd;
    static String d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        cv = (CalendarView) findViewById(R.id.cv);
        date = (TextView) findViewById(R.id.date);
        todoadd = (Button) findViewById(R.id.todoadd);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {  // 캘린더뷰에서 날짜 클릭하면 해당 날짜 선택
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                d = year + "/" + (month + 1) + "/" + day;
                date.setText(d);
            }
        });

        todoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), todolist.class);
                Toast.makeText(getApplicationContext(), d, Toast.LENGTH_LONG).show();
                intent.putExtra("selectedDate", d);
                startActivity(intent);
            }
        });


    }
}
