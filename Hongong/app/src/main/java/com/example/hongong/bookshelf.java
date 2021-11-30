package com.example.hongong;

import android.content.Context;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class bookshelf extends mydesk{
    Button btnAdd, btnimage;
    ListView listview;
    View dialogView;
    EditText bookname, booktext;

    SingleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookshelf);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        listview = (ListView) findViewById(R.id.listview);

        adapter = new SingleAdapter();
        adapter.addItem(new BookItems("동물농장1", "재밌었다."));
        adapter.addItem(new BookItems("동물농장2", "재밌었다."));
        adapter.addItem(new BookItems("동물농장3", "재밌었다."));
        adapter.addItem(new BookItems("동물농장4", "재밌었다."));

        listview.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogView = (View) View.inflate(bookshelf.this, R.layout.booksetting, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(bookshelf.this);
                dlg.setTitle("책 정보 입력");
                dlg.setView(dialogView);

                dlg.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bookname = (EditText) dialogView.findViewById(R.id.bookname);
                        booktext = (EditText) dialogView.findViewById(R.id.booktext);

                        String name = bookname.getText().toString();
                        String text = booktext.getText().toString();

                        adapter.addItem(new BookItems(name, text));
                        listview.setAdapter(adapter);
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
                listview.setAdapter(adapter);
            }
        });
    }

    class SingleAdapter<BookItems> extends BaseAdapter{
        ArrayList<com.example.hongong.BookItems> items = new ArrayList<com.example.hongong.BookItems>();

        @Override
        public int getCount() {  // 리스트 항목 개수를 알려줌
            return items.size();
        }

        public void addItem(com.example.hongong.BookItems item){  // 리스트에 아이템 담기
            items.add(item);
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {  // 아이템을 뷰로 보여주는 메소드
            books itemView = new books(getApplicationContext());
            com.example.hongong.BookItems item = items.get(i);
            itemView.setbName(item.getbookname());
            itemView.setbText(item.getbooktext());
            itemView.setbImage();
            return itemView;
        }
    }
}
