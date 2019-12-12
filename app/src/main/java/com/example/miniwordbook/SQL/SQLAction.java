package com.example.miniwordbook.SQL;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.miniwordbook.WordBook;
import com.example.miniwordbook.container.Book;
import com.example.miniwordbook.container.Search;
import com.example.miniwordbook.container.Word;

import java.util.ArrayList;
import java.util.List;

public class SQLAction {
    public static SQLiteDatabase sql;
    public static void read(WordBook wordBook) {
        /*sql.execSQL("insert into Word (id,book,eng,chi) values(0,0,'shi li dan ci ying wen 1','事例单词中文1')");
        sql.execSQL("insert into Word (id,book,eng,chi) values(1,0,'shi li dan ci ying wen 2','事例单词中文2')");
        sql.execSQL("insert into Word (id,book,eng,chi) values(2,0,'shi li dan ci ying wen 3','事例单词中文3')");
        sql.execSQL("insert into Word (id,book,eng,chi) values(3,0,'shi li dan ci ying wen 4','事例单词中文4')");
        sql.execSQL("insert into Word (id,book,eng,chi) values(4,0,'shi li dan ci ying wen 5','事例单词中文5')");
        sql.execSQL("insert into Word (id,book,eng,chi) values(5,0,'shi li dan ci ying wen 6','事例单词中文6')");
        sql.execSQL("insert into Word (id,book,eng,chi) values(6,0,'shi li dan ci ying wen 7','事例单词中文7')");
        sql.execSQL("insert into Book (id,name) values(0,'事例单词本1')");
        sql.execSQL("insert into Book (id,name) values(1,'事例单词本2')");
        sql.execSQL("insert into Book (id,name) values(2,'事例单词本3')");
        sql.execSQL("insert into Book (id,name) values(3,'事例单词本4')");
        sql.execSQL("insert into Book (id,name) values(4,'事例单词本5')");
        sql.execSQL("insert into Book (id,name) values(5,'事例单词本6')");
        sql.execSQL("insert into Book (id,name) values(6,'事例单词本7')");
        sql.execSQL("insert into Book (id,name) values(7,'事例单词本8')");
        sql.execSQL("insert into Book (id,name) values(8,'事例单词本9')");
        sql.execSQL("insert into Book (id,name) values(9,'事例单词本10')");*/
        Cursor cursor = sql.rawQuery("select count(*) bSize from Book", null);
        cursor.moveToFirst();
        int bSize = cursor.getInt(cursor.getColumnIndex("bSize"));
        cursor.close();
        cursor=sql.rawQuery("select * from Book",null);
        cursor.moveToFirst();
        for(int i=0;i<bSize;i++){
            Book book=new Book();
            book.id=cursor.getInt(cursor.getColumnIndex("id"));
            book.name=cursor.getString(cursor.getColumnIndex("name"));
            Cursor cursor1=sql.rawQuery("select count(*) wSize from Word where book=?",new String[]{ i+"" });
            cursor1.moveToFirst();
            int wSize=cursor1.getInt(cursor1.getColumnIndex("wSize"));
            cursor1.close();
            cursor1=sql.rawQuery("select * from Word where book=?",new String[]{ i+"" });
            cursor1.moveToFirst();
            for(int j=0;j<wSize;j++){
                Word word=new Word();
                word.id=cursor1.getInt(cursor1.getColumnIndex("id"));
                word.eng=cursor1.getString(cursor1.getColumnIndex("eng"));
                word.chi=cursor1.getString(cursor1.getColumnIndex("chi"));
                word.exm=cursor1.getString(cursor1.getColumnIndex("exm"));
                book.getList().add(word);
                cursor1.moveToNext();
            }
            wordBook.getData().getList().add(book);
            cursor.moveToNext();
        }
    }
    public static void newWord(int bookId,int wordId,String eng,String chi,String exm){
        sql.execSQL("insert into Word (id,book,eng,chi,exm) values("+wordId+","+bookId+",'"+eng+"','"+chi+"','"+exm+"')");
    }
    public static void newBook(int bookId,String name){
        sql.execSQL("insert into Book (id,name) values("+bookId+",'"+name+"')");
    }
    public static void saveWord(int bookId,int wordId,String eng,String chi,String exm){
        sql.execSQL("update Word set eng='"+eng+"',chi='"+chi+"',exm='"+exm+"' where id="+wordId+" and book="+bookId);
    }
    public static void saveBook(int bookId,String name){
        sql.execSQL("update Book set name='"+name+"' where id="+bookId);
    }
    public static void removeWord(int bookId,int wordId){
        sql.execSQL("delete from Word where id="+wordId+" and book="+bookId);
        sql.execSQL("update Word set id=id-1 where id>"+ wordId +" and book="+bookId);
    }
    public static void removeBook(int bookId){
        sql.execSQL("delete from Book where id="+bookId);
        sql.execSQL("delete from Word where book="+bookId);
        sql.execSQL("update Book set id=id-1 where id>"+bookId);
    }
    public static List<Search> search(String value){
        List list=new ArrayList();
        int size;

        Cursor cursor = sql.rawQuery("select count(*) Size from word where eng='"+value+"' or chi='"+value+"' or exm='"+value+"'", null);
        cursor.moveToFirst();
        size=cursor.getInt(cursor.getColumnIndex("Size"));
        cursor.close();
        cursor=sql.rawQuery("select * from word where eng='"+value+"' or chi='"+value+"' or exm='"+value+"'", null);
        if(size!=0)cursor.moveToFirst();
        for(int i=0;i<size;i++){
            Search search=new Search();
            search.bookid=cursor.getInt(cursor.getColumnIndex("book"));
            search.wordid=cursor.getInt(cursor.getColumnIndex("id"));
            list.add(search);
            cursor.moveToNext();
        }
        cursor.close();


        cursor = sql.rawQuery("select count(*) Size from word where (eng!='"+value+"' and chi!='"+value+"' and exm!='"+value+"') and (eng like '%"+value+"%' or chi like '%"+value+"%' or exm like '%"+value+"%')", null);
        cursor.moveToFirst();
        size=cursor.getInt(cursor.getColumnIndex("Size"));
        cursor.close();
        cursor=sql.rawQuery("select * from word where (eng!='"+value+"' and chi!='"+value+"' and exm!='"+value+"') and (eng like '%"+value+"%' or chi like '%"+value+"%' or exm like '%"+value+"%')", null);
        if(size!=0)cursor.moveToFirst();
        for(int i=0;i<size;i++){
            Search search=new Search();
            search.bookid=cursor.getInt(cursor.getColumnIndex("book"));
            search.wordid=cursor.getInt(cursor.getColumnIndex("id"));
            list.add(search);
            cursor.moveToNext();
        }
        cursor.close();
        return list;


    }
}
