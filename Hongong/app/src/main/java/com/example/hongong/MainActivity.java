package com.example.hongong;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Button dayset;
    TextView dday, name;
    View setting;
    EditText testname, testdate;
    ImageButton move1, move2, move3, move4, move5, move6;
    String test;
    int day;

    FirebaseDatabase database = FirebaseDatabase.getInstance();  // firebase 사용을 위한
    DatabaseReference databaseReference = database.getReference("Hongong");

    @Override
    protected void onCreate(Bundle savedInstanceState) {  // 메인 화면
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayset = (Button) findViewById(R.id.dayset);
        dday = (TextView) findViewById(R.id.dday);
        name = (TextView) findViewById(R.id.name);
        move1 = (ImageButton) findViewById(R.id.move1);
        move2 = (ImageButton) findViewById(R.id.move2);
        move3 = (ImageButton) findViewById(R.id.move3);
        move4 = (ImageButton) findViewById(R.id.move4);
        move5 = (ImageButton) findViewById(R.id.move5);
        move6 = (ImageButton) findViewById(R.id.move6);

        databaseReference.child("dDay").addValueEventListener(new ValueEventListener() {  // D-day 출력
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
                dDayR result = snapshot.getValue(dDayR.class);

                try {
                    test = result.gettest();
                    day = result.getdDay();

                    name.setText(test + " 시험까지");
                    dday.setText("D-" + day);

                } catch (Exception e) {  }  // 없으면 그냥 그대로
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {  }
        });

        dayset.setOnClickListener(new View.OnClickListener() {  // 변경 버튼을 눌러서 D-day 설정 변경
            @Override
            public void onClick(View view) {
                setting = (View) View.inflate(MainActivity.this, R.layout.ddaysetting, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("D-day 설정");
                dlg.setView(setting);
                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {  // 확인을 눌렀을 경우 D-day에 반영
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                testname = (EditText) setting.findViewById(R.id.testname);
                                testdate = (EditText) setting.findViewById(R.id.testdate);

                                test = testname.getText().toString();
                                day = Integer.parseInt(testdate.getText().toString());

                                name.setText(test + " 시험까지");
                                dday.setText("D-" + testdate.getText().toString());

                                addDDay(test, day);
                            }
                        });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        move1.setOnClickListener(new View.OnClickListener() {  // 이미지버튼들을 클릭하면 각 해당하는 액티비티로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), calendar.class);
                startActivity(intent);
            }
        });

        move2.setOnClickListener(new View.OnClickListener() {  // 이미지버튼들을 클릭하면 각 해당하는 액티비티로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), mydesk.class);
                startActivity(intent);
            }
        });

        move3.setOnClickListener(new View.OnClickListener() {  // 이미지버튼들을 클릭하면 각 해당하는 액티비티로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), timer.class);
                startActivity(intent);
            }
        });

        move4.setOnClickListener(new View.OnClickListener() {  // 이미지버튼들을 클릭하면 각 해당하는 액티비티로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), statistics.class);
                startActivity(intent);
            }
        });

        move5.setOnClickListener(new View.OnClickListener() {  // 이미지버튼들을 클릭하면 각 해당하는 액티비티로 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), search.class);
                startActivity(intent);
            }
        });

        move6.setOnClickListener(new View.OnClickListener() {  // 이미지버튼들을 클릭하면 각 해당하는 액티비티로 이동
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.google.co.kr/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    private void addDDay(String test, int dDay){  // D-day를 database로 넘기는 함수
        dDayR dDayR = new dDayR(test, dDay);

        databaseReference.child("dDay").setValue(dDayR);
    }
}
