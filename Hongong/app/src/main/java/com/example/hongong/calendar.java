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
import android.widget.ImageButton;
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
    Boolean fin, isChecked;
    ImageButton check;

    FirebaseDatabase database = FirebaseDatabase.getInstance();  // firebase 사용을 위한
    DatabaseReference databaseReference = database.getReference("Hongong");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        cv = (CalendarView) findViewById(R.id.cv);
        date = (TextView) findViewById(R.id.date);
        todoadd = (Button) findViewById(R.id.todoadd);
        list = (TextView) findViewById(R.id.list);
        check = (ImageButton) findViewById(R.id.check);

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

                            check.setVisibility(View.VISIBLE);

                            if(fin){  // 일정이 완료되어있다면 체크 표시
                                isChecked = true;
                                check.setImageResource(R.drawable.checkok);
                            } else{
                                isChecked = false;
                                check.setImageResource(R.drawable.checkno);
                            }

                        } catch (Exception e) {
                            list.setText("일정이 없습니다.");
                            check.setVisibility(View.INVISIBLE);
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

        check.setOnClickListener(new View.OnClickListener() {  // 체크박스 클릭 시 이미지 바꾸고 fin 값 바꿔주기
            @Override
            public void onClick(View view) {
                if(!isChecked){  // 노체크(fin=false)일 경우 체크 상태로 변환
                    databaseReference.child("todo").child(d).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {  // 데이터를 새로 쓰기 위해 값 받아옴
                            todoR result = snapshot.getValue(todoR.class);
                            try {
                                dolist = result.getdolist();
                                fin = result.getfin();

                                isChecked = true;
                                check.setImageResource(R.drawable.checkok);
                                Toast.makeText(getApplicationContext(),"\uD83C\uDF89", Toast.LENGTH_SHORT).show();

                                addtodo(d, dolist, true);
                            } catch (Exception e) {
                                list.setText("일정이 없습니다.");
                                check.setVisibility(View.INVISIBLE);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {  }
                    });

                } else {  // 되돌리기 X
                    Toast.makeText(getApplicationContext(),"이미 끝낸 일정입니다! 수고하셨습니다~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addtodo(String todate, String todo, Boolean fin){  // database로 넘기는 함수
        todoF todoF = new todoF(todate, todo, fin);

        databaseReference.child("todo").child(todate).setValue(todoF);
    }
}
