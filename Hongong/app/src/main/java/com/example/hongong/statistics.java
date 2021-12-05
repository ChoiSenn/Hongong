package com.example.hongong;

import android.app.TabActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class statistics extends TabActivity {
    TextView dayTime, dayDo, tvTime, tvRating, yesTime, tvTime2, yesRating;
    RatingBar dayRating;

    FirebaseDatabase database = FirebaseDatabase.getInstance();  // firebase 사용을 위한
    DatabaseReference databaseReference = database.getReference("Hongong");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        dayTime = (TextView) findViewById(R.id.dayTime);
        dayDo = (TextView) findViewById(R.id.dayDo);
        dayRating = (RatingBar) findViewById(R.id.dayRating);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvRating = (TextView) findViewById(R.id.tvRating);
        yesTime = (TextView) findViewById(R.id.yesTime);
        tvTime2 = (TextView) findViewById(R.id.tvTime2);
        yesRating = (TextView) findViewById(R.id.yesRating);

        TabHost tabHost = getTabHost();  // 탭호스트

        TabHost.TabSpec tabSpecDay = tabHost.newTabSpec("StudyTime").setIndicator("공부 시간");
        tabSpecDay.setContent(R.id.StudyTime);
        tabHost.addTab(tabSpecDay);

        TabHost.TabSpec tabSpecWeek = tabHost.newTabSpec("ToDo").setIndicator("일정 완료");
        tabSpecWeek.setContent(R.id.ToDo);
        tabHost.addTab(tabSpecWeek);

        TabHost.TabSpec tabSpecMonth = tabHost.newTabSpec("Rating").setIndicator("하루 기분");
        tabSpecMonth.setContent(R.id.Rating);
        tabHost.addTab(tabSpecMonth);

        tabHost.setCurrentTab(0);

        long now = System.currentTimeMillis();  // 현재 시간 이용해서 날짜 추출
        Date date = new Date(now);
        SimpleDateFormat d = new SimpleDateFormat("yyyy/MM/dd");
        String day = d.format(date);  // 형식 바꿔서 저장

        Date ydate = new Date(date.getTime()+(1000*60*60*24*-1));
        SimpleDateFormat dSdf = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
        String yesterday = dSdf.format(ydate);  // 어제 날짜

        databaseReference.child("study").child(day).addValueEventListener(new ValueEventListener() {  // 오늘 공부 시간 출력
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                timesaveR result = snapshot.getValue(timesaveR.class);
                databaseReference.child("study").child(yesterday).addValueEventListener(new ValueEventListener() {  // 어제 공부 시간도 출력
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        timesaveR result2 = snapshot.getValue(timesaveR.class);
                        try {
                            int alltime = result.gettime();
                            int hour = alltime / (60 * 60);
                            int min = alltime % (60 * 60) / 60;
                            int sec = alltime % 60;

                            dayTime.setText(hour + "시간" + min + "분" + sec + "초");
                            dayTime.setTextColor(Color.parseColor("#7B57A9"));

                            if(alltime == 0){
                                tvTime.setText("아직 오늘의 공부를 시작하지 않았습니다.");
                            } else if(alltime < 60){
                                tvTime.setText("조금 더 오래 공부해봅시다!");
                            } else if(alltime < 360){
                                tvTime.setText("1시간 넘게 공부했습니다!");
                            } else if(alltime < 720){
                                tvTime.setText("수고했습니다~");
                            }

                            try{
                                int alltime2 = result2.gettime();
                                int hour2 = alltime2 / (60 * 60);
                                int min2 = alltime2 % (60 * 60) / 60;
                                int sec2 = alltime2 % 60;

                                yesTime.setText("어제의 공부 시간 : " + hour2 + "시간" + min2 + "분" + sec2 + "초");
                                yesTime.setTextColor(Color.parseColor("#7B57A9"));

                                if(alltime > alltime2){
                                    tvTime2.setText("어제보다 더 많이 공부했습니다!");
                                } else{
                                    tvTime2.setText("어제에 비해 덜 공부했습니다.");
                                }
                            } catch (Exception e){
                                yesTime.setText("");
                                tvTime2.setText("어제의 공부 시간 기록이 없습니다.");
                            }

                        } catch (Exception e) {
                            dayTime.setText("");
                            tvTime.setText("아직 오늘의 공부를 시작하지 않았습니다.");
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });

        databaseReference.child("todo").child(day).addValueEventListener(new ValueEventListener() {  // 오늘 일정 완료 했는지
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                todoR result = snapshot.getValue(todoR.class);
                try {
                    String dolist = result.getdolist();
                    Boolean fin = result.getfin();
                    if (dolist != "") {  // 일정 있으면 출력
                        if(fin){  // 일정이 완료되어있는지
                            dayDo.setText("오늘의 일정 완료!");
                        } else{
                            dayDo.setText("일정이 완료되지 않았습니다.");
                        }
                    } else {
                        dayDo.setText("아직 설정된 일정이 없습니다.");
                    }
                    dayDo.setTextColor(Color.parseColor("#7B57A9"));
                } catch (Exception e) {
                    dayDo.setText("아직 설정된 일정이 없습니다.");
                    dayDo.setTextColor(Color.parseColor("#7B57A9"));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });

        databaseReference.child("rating").child(day).addValueEventListener(new ValueEventListener() {  // 오늘 기분 출력
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                ratingR result = snapshot.getValue(ratingR.class);
                databaseReference.child("rating").child(yesterday).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ratingR result2 = snapshot.getValue(ratingR.class);
                        try {
                            float getrating = result.getrating();
                            dayRating.setRating(getrating);

                            switch ((int) getrating) {
                                case 1:
                                    tvRating.setText("별로 기분이 좋지 않은 하루였습니다...");
                                    break;
                                case 2:
                                    tvRating.setText("기분이 좋지 않은 하루였습니다.");
                                    break;
                                case 3:
                                    tvRating.setText("평균적인 기분의 하루였습니다.");
                                    break;
                                case 4:
                                    tvRating.setText("기분이 좋은 하루였습니다!");
                                    break;
                                case 5:
                                    tvRating.setText("기분이 굉장히 좋은 하루였습니다~");
                                    break;
                            }
                            try{
                                float getrating2 = result2.getrating();
                                if(getrating > getrating2){
                                    yesRating.setText("오늘은 어제보다 더 기분이 좋습니다.");
                                } else if(getrating == getrating2){
                                    yesRating.setText("오늘과 어제의 기분이 같습니다.");
                                } else{
                                    yesRating.setText("오늘은 어제보다 기분이 나쁩니다.");
                                }
                            }catch (Exception e){
                                yesRating.setText("어제의 기분 기록이 없습니다.");
                            }
                            tvRating.setTextColor(Color.parseColor("#7B57A9"));
                        } catch (Exception e) {
                            tvRating.setText("아직 하루 기분 기록을 하지 않았습니다.");
                            dayRating.setVisibility(View.INVISIBLE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });
    }
}
