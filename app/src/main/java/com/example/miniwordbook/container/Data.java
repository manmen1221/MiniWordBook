package com.example.miniwordbook.container;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Book> bookList=new ArrayList<Book>();
    public List<Book> getList(){
        return bookList;
    }
    public int getSize(){
        return bookList.size();
    }
    public Data(){

    }
}
