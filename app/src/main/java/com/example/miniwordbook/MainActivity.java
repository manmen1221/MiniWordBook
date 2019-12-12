package com.example.miniwordbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miniwordbook.SQL.SQLAction;
import com.example.miniwordbook.container.Book;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout listView;
    WordBook wordbook;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("帮助");
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dialog.show();
                break;
            default:
                break;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.单词本列表);
        wordbook=WordBook.getWordBook(this);
        Button button=findViewById(R.id.addBook);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("添加单词本");
                View v=getLayoutInflater().inflate(R.layout.book_name,null);
                final EditText et=v.findViewById(R.id.BookEdit);
                dialog.setView(v);
                dialog.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name=et.getText()+"";
                        if(name!=null)
                            if(name.length()>0)
                                addBook(name);
                    }
                });
                dialog.show();
            }
        });
        EditText search=findViewById(R.id.搜索内容);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent itt=new Intent(MainActivity.this,SearchActivity.class);
                startActivityForResult(itt,1);
            }
        });
        refresh();
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1)
            refresh();
    }
    private void addBook(String name){
        Book book=new Book();
        book.name=name;
        int id=wordbook.getData().getSize();
        book.id=id;
        wordbook.getData().getList().add(book);
        SQLAction.newBook(id,name);
        refresh();
    }
    private void refresh(){
        int bookSize=wordbook.getData().getSize();
        List<Book> bookList=wordbook.getData().getList();
        listView.removeAllViews();
        for(int i=0;i<bookSize;i++){
            Book book=bookList.get(i);
            LinearLayout bookView=(LinearLayout)(getLayoutInflater().inflate(R.layout.book,null));
            TextView idView=bookView.findViewById(R.id.bookID);
            idView.setText(book.id+1+"");
            TextView nameView=bookView.findViewById(R.id.bookName);
            nameView.setText("："+StringTool.change(8,book.name));
            TextView sizeView=bookView.findViewById(R.id.bookSize);
            sizeView.setText("单词数量："+book.getSize());
            listView.addView(bookView);
            bookView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
            bookView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView number=view.findViewById(R.id.bookID);
                    String bookId= number.getText()+"";
                    Intent itt=new Intent(MainActivity.this,BookActivity.class);
                    itt.putExtra("bookId",bookId);
                    startActivityForResult(itt,1);

                }
            });
        }
    }
}
