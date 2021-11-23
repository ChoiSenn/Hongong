package com.example.hongong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class todolist extends calendar {
    TextView datetv;
    Button cancle, add;
    EditText text;

    String todate;
    String todo;
    int num;

    FirebaseDatabase database = FirebaseDatabase.getInstance();  // firebase 사용을 위한
    DatabaseReference databaseReference = database.getReference("message");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        datetv = (TextView) findViewById(R.id.datetv);
        cancle = (Button) findViewById(R.id.cancle);
        add = (Button) findViewById(R.id.add);
        text = (EditText) findViewById(R.id.todo);

        Intent intent = getIntent();
        final String date = intent.getExtras().getString("selectedDate");
        datetv.setText(date);

        cancle.setOnClickListener(new View.OnClickListener() {  // 취소 누르면 그냥 꺼짐
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {  // 추가 버튼 눌렸을 때
            @Override
            public void onClick(View view) {
                String todate = date;
                String todo = text.getText().toString();

//                databaseReference.child("todo").child(todate).addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {  // 값 받아오기
//                        todoR result = snapshot.getValue(todoR.class);
//                        try {  // 일정이 몇 개 있는지
//
//                        } catch (Exception e) {  }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
                addtodo(todate, todo, false);
                finish();
            }
        });
    }

    private void addtodo(String todate, String todo, Boolean fin){  // database로 넘기는 함수
        todoF todoF = new todoF(todate, todo, fin);

        databaseReference.child("todo").child(todate).setValue(todoF);
    }
}
