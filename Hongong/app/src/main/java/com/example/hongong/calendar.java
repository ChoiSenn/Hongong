package com.example.hongong;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class calendar extends MainActivity{
    CalendarView cv;
    TextView date, list, diary, cheer, study;
    Button todoadd;
    static String d;
    String dolist, diarytext, da;
    Boolean fin, isChecked;
    ImageButton check;
    RatingBar rating, setrate;
    View dlgdiary, dlgrating;
    EditText setdiary;
    float ratenum;

    FirebaseDatabase database = FirebaseDatabase.getInstance();  // firebase 사용을 위한
    DatabaseReference databaseReference = database.getReference("Hongong");

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {  // 캘린더뷰에 컨텍스트 메뉴
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.diary, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {  // 컨텍스트 메뉴 눌렸을 때
        switch(item.getItemId()){
            case R.id.diaryvalue:
                dlgdiary = (View) View.inflate(calendar.this, R.layout.diarysetting, null);
                AlertDialog.Builder dlg1 = new AlertDialog.Builder(calendar.this);
                dlg1.setView(dlgdiary);
                dlg1.setPositiveButton("확인",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setdiary = (EditText) dlgdiary.findViewById(R.id.diarytext);
                                diarytext = setdiary.getText().toString();

                                addDiary(d, diarytext);
                            }
                        });
                dlg1.setNegativeButton("취소", null);
                dlg1.show();
                return true;

            case R.id.ratingvalue:
                dlgrating = (View) View.inflate(calendar.this, R.layout.ratingsetting, null);
                AlertDialog.Builder dlg2 = new AlertDialog.Builder(calendar.this);
                dlg2.setView(dlgrating);
                dlg2.setPositiveButton("확인",
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                setrate = (RatingBar) dlgrating.findViewById(R.id.rate);
                                ratenum = setrate.getRating();
                                int ra = (int)ratenum;

                                addRating(d, ra);
                            }
                        });
                dlg2.setNegativeButton("취소", null);
                dlg2.show();
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        cv = (CalendarView) findViewById(R.id.cv);
        date = (TextView) findViewById(R.id.date);
        todoadd = (Button) findViewById(R.id.todoadd);
        list = (TextView) findViewById(R.id.list);
        check = (ImageButton) findViewById(R.id.check);
        diary = (TextView) findViewById(R.id.diary);
        cheer = (TextView) findViewById(R.id.cheer);
        rating = (RatingBar) findViewById(R.id.rating);
        study = (TextView) findViewById(R.id.study);

        dolist = "";
        fin = false;

        registerForContextMenu(todoadd);

        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {  // 캘린더뷰에서 날짜 클릭하면 해당 날짜 선택
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                if(String.valueOf(day).length() == 1){
                    da = String.valueOf(day);
                    da = "0" + da;
                    Log.v("v", da);
                }

                d = year + "/" + (month + 1) + "/" + da;
                date.setText(d);
                Log.v("v", d);

                databaseReference.child("todo").child(d).addValueEventListener(new ValueEventListener() {  // 투두리스트 출력
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                        todoR result = snapshot.getValue(todoR.class);
                        try {
                            dolist = result.getdolist();
                            fin = result.getfin();
                            if (dolist != "") {  // 일정 있으면 출력
                                list.setText(dolist);
                            } else {
                                list.setText("아직 설정된 일정이 없습니다.");
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
                            list.setText("아직 설정된 일정이 없습니다.");
                            check.setVisibility(View.INVISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {  }
                });

                databaseReference.child("diary").child(d).addValueEventListener(new ValueEventListener() {  // 일기 출력
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                        diaryR result = snapshot.getValue(diaryR.class);
                        try {
                            String getdiary = result.getdiary();
                            if (getdiary != "") {  // 일정 있으면 출력
                                diary.setText(getdiary);
                            } else {
                                diary.setText("");
                            }
                        } catch (Exception e) {
                            diary.setText("");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {  }
                });

                databaseReference.child("rating").child(d).addValueEventListener(new ValueEventListener() {  // 기분 출력
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                        ratingR result = snapshot.getValue(ratingR.class);
                        try {
                            float getrating = result.getrating();
                            Log.v("1 : ", String.valueOf(getrating));
                            rating.setVisibility(View.VISIBLE);
                            rating.setRating(getrating);
                        } catch (Exception e) {
                            rating.setVisibility(View.INVISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {  }
                });

                databaseReference.child("study").child(d).addValueEventListener(new ValueEventListener() {  // 공부 시간 출력
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                        timesaveR result = snapshot.getValue(timesaveR.class);
                        try {
                            int alltime = result.gettime();
                            int hour = alltime / (60 * 60);
                            int min = alltime % (60 * 60) / 60;
                            int sec = alltime % 60;

                            study.setText(hour + "시간" + min + "분" + sec + "초");
                        } catch (Exception e) {
                            study.setText("");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {  }
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

    private void addtodo(String todate, String todo, Boolean fin){  // database로 todo 넘기는 함수
        todoF todoF = new todoF(todate, todo, fin);

        databaseReference.child("todo").child(todate).setValue(todoF);
    }

    private void addDiary(String date, String diary){  // diary 넘기는 함수
        diaryR diaryR = new diaryR(date, diary);
        databaseReference.child("diary").child(date).setValue(diaryR);
    }

    private void addRating(String date, int rating){  // rating 넘기는 함수
        ratingR ratingR = new ratingR(date, rating);
        databaseReference.child("rating").child(date).setValue(ratingR);
    }
}
