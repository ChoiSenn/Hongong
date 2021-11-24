package com.example.hongong;

public class dDayR {
    private String test;
    private int dDay;

    public dDayR() {  }

    public String gettest(){
        return test;
    }

    public void settest(String test){
        this.test = test;
    }

    public int getdDay(){
        return dDay;
    }

    public void setdDay(int dDay){
        this.dDay = dDay;
    }

    public dDayR(String test, int dDay){
        this.test = test;
        this.dDay = dDay;
    }
}
