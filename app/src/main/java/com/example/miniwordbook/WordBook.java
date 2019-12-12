package com.example.miniwordbook;

import android.content.Context;

import com.example.miniwordbook.SQL.SQLAction;
import com.example.miniwordbook.SQL.SQL;
import com.example.miniwordbook.container.Data;

public class WordBook {

    //创建//////////////////////////////////////////////////////////////////////////////////////////↓
    public static WordBook getWordBook(Context context){
        if(i==0){
            i++;
            wordBook=new WordBook();
            wordBook.readData(context);
            return wordBook;
        }
        else if(i==1&&wordBook!=null)return wordBook;
        return null;
    }
    public static WordBook wordBook(){
        return wordBook;
    }
    private static WordBook wordBook;
    private static int i=0;
    private WordBook(){
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////↑


    private Data data=new Data();
    public Data getData(){
        return data;
    }
    private void readData(Context context){
        SQLAction.sql=new SQL(context).getWritableDatabase();
        SQLAction.read(wordBook);
    }
}
