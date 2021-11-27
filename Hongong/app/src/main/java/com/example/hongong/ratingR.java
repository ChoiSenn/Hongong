package com.example.hongong;

public class ratingR {
    private String date;
    private int rating;

    public ratingR() {  }

    public String getdate(){
        return date;
    }

    public void setdate(String date){
        this.date = date;
    }

    public int getrating(){
        return rating;
    }

    public void setrating(int rating){
        this.rating = rating;
    }

    public ratingR(String date, int rating){
        this.date = date;
        this.rating = rating;
    }
}
