package com.example.hongong;

public class BookItems {
    String bookname;
    String booktext;
    int bookimageUrl;

    public BookItems(String bookname, String booktext, int bookimageUrl){
        this.bookname = bookname;
        this.booktext = booktext;
        this.bookimageUrl = bookimageUrl;
    }

    public BookItems(String bookname, String booktext){
        this.bookname = bookname;
        this.booktext = booktext;
    }

    public String getbookname(){
        return bookname;
    }

    public String getbooktext(){
        return booktext;
    }

    public int getbookimageUrl(){
        return bookimageUrl;
    }
}
