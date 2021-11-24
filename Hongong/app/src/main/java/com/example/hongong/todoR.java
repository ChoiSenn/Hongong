package com.example.hongong;

import android.util.Log;

public class todoR {
    private String dolist;
    private boolean fin;

    public todoR() {
        dolist = "";
        fin = false;
    }

    public String getdolist(){
        return dolist;
    }

    public void setdolist(String dolist){
        this.dolist = dolist;
    }

    public boolean getfin(){
        return fin;
    }

    public void setfin(boolean fin){
        this.fin = fin;
    }


    public todoR(String dolist, boolean fin){  // 그룹 생성
        this.dolist = dolist;
        this.fin = fin;
    }


}
