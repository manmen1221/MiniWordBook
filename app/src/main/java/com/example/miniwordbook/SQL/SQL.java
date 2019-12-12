package com.example.miniwordbook.SQL;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL extends SQLiteOpenHelper {

    //创建//////////////////////////////////////////////////////////////////////////////////////////↓
    public SQL(Context contex){
        super(contex, "WordBook.db",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Book (" +
                "id integer primary key autoincrement," +
                "name text)");
        db.execSQL("create table Word(" +
                "id integer," +
                "book integer," +
                "eng text," +
                "chi text," +
                "exm text," +
                "primary key(id,book) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    ////////////////////////////////////////////////////////////////////////////////////////////////↑
}
