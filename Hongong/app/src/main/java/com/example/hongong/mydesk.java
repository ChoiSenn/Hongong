package com.example.hongong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class mydesk extends MainActivity {
    ImageButton desk1, desk2, desk3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydesk);

        desk1 = (ImageButton) findViewById(R.id.desk1);
        desk2 = (ImageButton) findViewById(R.id.desk2);
        desk3 = (ImageButton) findViewById(R.id.desk3);

        desk1.setOnClickListener(new View.OnClickListener() {  // 클릭하면 화면 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), calculator.class);
                startActivity(intent);
            }
        });

        desk2.setOnClickListener(new View.OnClickListener() {  // 클릭하면 화면 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), bookshelf.class);
                startActivity(intent);
            }
        });

        desk3.setOnClickListener(new View.OnClickListener() {  // 클릭하면 화면 이동
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), myprize.class);
                startActivity(intent);
            }
        });
    }
}
