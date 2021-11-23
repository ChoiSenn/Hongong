package com.example.hongong;

import android.util.Log;

public class todoF {  // 테이블 같은 느낌... 파이어베이스는 테이블이라는 개념 없음. "키값"
    String todate;
    String todo;
    Boolean fin;

    public todoF(){ }  // 인자가 없는 경우

    public String getdate(){
        return todate;
    }

    public void setdate(String todate){
        this.todate = todate;
    }

    public String getdolist(){
        return todo;
    }

    public void setdolist(String todo){
        this.todo = todo;
    }

    public Boolean getfin(){
        return fin;
    }

    public void setfin(Boolean fin){
        this.fin = fin;
    }

    public todoF(String todate, String todo, Boolean fin){  // 값을 추가할때 사용하는 함수. todolist에서 호출할 것임.
        this.todate = todate;
        this.todo = todo;
        this.fin = fin;
    }
}
