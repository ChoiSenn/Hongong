package com.example.hongong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class todolist extends calendar {
    TextView datetv;
    Button cancle, add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        datetv = (TextView) findViewById(R.id.datetv);
        cancle = (Button) findViewById(R.id.cancle);
        add = (Button) findViewById(R.id.add);

        Intent intent = getIntent();
        final String date = intent.getExtras().getString("selectedDate");
        datetv.setText(date);

        cancle.setOnClickListener(new View.OnClickListener() {  // 취소 누르면 그냥 꺼짐
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
