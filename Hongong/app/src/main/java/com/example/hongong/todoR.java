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
        Log.v("2 : ", String.valueOf(dolist));
        if(dolist == null){
            return "";
        }
        return dolist;
    }

    public void setdolist(String dolist){
        this.dolist = dolist;
    }

    public boolean getfin(){
        if(fin){
            return true;
        } else{
            return false;
        }
    }

    public void setfin(boolean fin){
        this.fin = fin;
    }


    public todoR(String dolist, boolean fin){  // 그룹 생성
        this.dolist = dolist;
        this.fin = fin;
    }


}
