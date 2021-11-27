package com.example.hongong;

public class diaryR {
    private String date;
    private String diary;

    public diaryR() {  }

    public String getdate(){
        return date;
    }

    public void setdate(String date){
        this.date = date;
    }

    public String getdiary(){
        return diary;
    }

    public void setdiary(String diary){
        this.diary = diary;
    }

    public diaryR(String date, String diary){
        this.date = date;
        this.diary = diary;
    }
}
