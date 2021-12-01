package com.example.hongong;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class myprize extends mydesk {
    Gallery gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myprize);

        gallery = (Gallery) findViewById(R.id.gallery);
        MyGalleryAdapter galAdapter = new MyGalleryAdapter(this);
        gallery.setAdapter(galAdapter);
    }

    public class MyGalleryAdapter extends BaseAdapter{
        Context context;
        Integer[] posterID = {R.drawable.prize1, R.drawable.prize2, R.drawable.prize3, R.drawable.prize4,
                R.drawable.prize5, R.drawable.prize6, R.drawable.prize7, R.drawable.prize8};
        String[] prizetext = {"국가자격증 취득 목록", "파이썬마스터 1급 자격증", "ADsP 자격증", "국가공인 자격증 모음",
            "ITQ OA 자격증", "정보처리산업기사", "바리스타 자격증 2급", "자바 자격증 OCPJ"};

        public MyGalleryAdapter(Context c){
            context = c;
        }

        @Override
        public int getCount() {
            return posterID.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new Gallery.LayoutParams(200, 300));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(5, 5, 5, 5);

            imageView.setImageResource(posterID[position]);

            final int pos = position;
            imageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    ImageView poster = (ImageView) findViewById(R.id.poster);
                    TextView tv = (TextView) findViewById(R.id.tv);
                    poster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    poster.setImageResource(posterID[pos]);
                    tv.setText(prizetext[pos]);
                    return false;
                }
            });

            return imageView;
        }
    }
}
