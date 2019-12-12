package com.example.miniwordbook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.miniwordbook.SQL.SQL;

public class DatabaseProvider extends ContentProvider {
    private static UriMatcher uriMatcher;
    private SQL dbHelper;
    public static final int BOOK_DIR=0,BOOK_ITEM=1,WORD_DIR=2,WORD_ITEM=3;
    public static final String AUTHORITY="com.example.miniwordbook.provider";
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"word",WORD_DIR);
        uriMatcher.addURI(AUTHORITY,"word/#",WORD_ITEM);
    }
    public DatabaseProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        SQLiteDatabase sql=dbHelper.getReadableDatabase();
        int rows=0;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                rows=sql.delete("Word",selection,selectionArgs);
                break;
            default:
                break;

        }
        return rows;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        SQLiteDatabase sql=dbHelper.getReadableDatabase();
        Uri u=null;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                long id=sql.insert("Word",null,values);
                u=Uri.parse("content://"+AUTHORITY+"/book/"+id);
                break;
            default:
                break;
        }
        return u;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        dbHelper=new SQL(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        SQLiteDatabase sql=dbHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                cursor=sql.query("Word",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        SQLiteDatabase sql=dbHelper.getReadableDatabase();
        int rows=0;
        switch (uriMatcher.match(uri)){
            case WORD_DIR:
                rows=sql.update("Word",values,selection,selectionArgs);
                break;
            default:
                break;

        }
        return rows;
    }
}
