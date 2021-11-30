package com.example.hongong;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class books extends LinearLayout {
    TextView bName, bText;
    ImageView bImage;

    public books(Context context){  // 객체 생성자
        super(context);
        init(context);
    }

    public books(Context context, AttributeSet att){
        super(context, att);
        init(context);
    }

    private void init(Context context) {  // 객체 생성 시 실행할 메소드 (onCreate()같은 역할)
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.single_book, this, true);

        bName = (TextView) findViewById(R.id.bName);
        bText = (TextView) findViewById(R.id.bText);
        bImage = (ImageView) findViewById(R.id.bImage);
    }

    // 각각 textview에 내용 삽입하기위한 메소드들
    public void setbName(String name){
        bName.setText(name);
    }

    public void setbText(String text){
        bText.setText(text);
    }

    public void setbImage(){
        bImage.setImageResource(R.drawable.book);
    }
}
