package com.example.hongong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class calendar extends MainActivity{
    CalendarView cv;
    TextView date, list;
    Button todoadd;
    static String d;
    String dolist;
    Boolean fin;

    FirebaseDatabase database = FirebaseDatabase.getInstance();  // firebase 사용을 위한
    DatabaseReference databaseReference = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        cv = (CalendarView) findViewById(R.id.cv);
        date = (TextView) findViewById(R.id.date);
        todoadd = (Button) findViewById(R.id.todoadd);
        list = (TextView) findViewById(R.id.list);

        dolist = "";
        fin = false;

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {  // 캘린더뷰에서 날짜 클릭하면 해당 날짜 선택
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                d = year + "/" + (month + 1) + "/" + day;
                date.setText(d);

                databaseReference.child("todo").child(d).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                        todoR result = snapshot.getValue(todoR.class);

                        try {
                            dolist = result.getdolist();
                            fin = result.getfin();

                            if (dolist != "") {  // 일정 있으면 출력
                                list.setText(dolist);
                            } else {
                                list.setText("일정이 없습니다.");
                            }
                        } catch (Exception e) {
                            list.setText("일정이 없습니다.");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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
