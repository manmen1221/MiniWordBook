package com.example.miniwordbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.miniwordbook.SQL.SQLAction;
import com.example.miniwordbook.container.Book;
import com.example.miniwordbook.container.Word;

public class BookActivity extends AppCompatActivity {

    int bookId;
    Book book;
    LinearLayout listView;
    WordBook wordbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        wordbook=WordBook.getWordBook(this);
        bookId=Integer.parseInt(getIntent().getStringExtra("bookId"))-1;
        book=wordbook.getData().getList().get(bookId);
        listView=findViewById(R.id.单词列表);
        Button button=findViewById(R.id.addWord);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(BookActivity.this);
                dialog.setTitle("添加单词：");
                LayoutInflater li=getLayoutInflater();
                final View v=li.inflate(R.layout.activity_word,null);
                final int wordId=book.getSize();
                final Word w = new Word();
                final EditText editEng = v.findViewById(R.id.editEng);
                final EditText editChi = v.findViewById(R.id.editChi);
                final EditText editExm = v.findViewById(R.id.editExm);
                editEng.setText(w.eng);
                editChi.setText(w.chi);
                editExm.setText(w.exm);
                dialog.setView(v);
                dialog.setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        w.id=wordId;
                        w.eng=editEng.getText()+"";
                        w.chi=editChi.getText()+"";
                        w.exm=editExm.getText()+"";
                        book.getList().add(w);
                        SQLAction.newWord(bookId,wordId,editEng.getText()+"",editChi.getText()+"",editExm.getText()+"");
                        refresh();
                    }
                });
                dialog.show();
            }
        });
        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.removeBook:
                AlertDialog.Builder dialog = new AlertDialog.Builder(BookActivity.this);
                dialog.setTitle("确认删除此单词本吗？");
                dialog.setNeutralButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        removeBook();
                        BookActivity.this.finish();
                    }
                });
                dialog.setPositiveButton("取消",null);
                dialog.show();
                break;
            case R.id.renameBook:
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(BookActivity.this);
                dialog1.setTitle("修改名称");
                View v=getLayoutInflater().inflate(R.layout.book_name,null);
                final EditText et=v.findViewById(R.id.BookEdit);
                et.setText(book.name);
                dialog1.setView(v);
                dialog1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name=et.getText()+"";
                        if(name!=null)
                            if(name.length()>0){
                                book.name=name;
                                SQLAction.saveBook(bookId,name);
                            }
                    }
                });
                dialog1.show();
                break;
            default:
                Toast.makeText(this, "错误：未知操作！", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
    private void refresh(){
        listView.removeAllViews();
        int bookSize=book.getSize();
        for(int i=0;i<bookSize;i++){
            Word word=book.getList().get(i);
            LinearLayout wordView=(LinearLayout)(getLayoutInflater().inflate(R.layout.word,null));
            TextView idView=wordView.findViewById(R.id.wordId);
            idView.setText(word.id+"");
            TextView engView=wordView.findViewById(R.id.wordEng);
            engView.setText(word.eng);
            TextView chiView=wordView.findViewById(R.id.wordChi);
            chiView.setText(word.chi);
            TextView exmView=wordView.findViewById(R.id.wordExm);
            exmView.setText(word.exm);
            listView.addView(wordView);
            wordView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog=new AlertDialog.Builder(BookActivity.this);
                    dialog.setTitle("单词：");
                    LayoutInflater li=getLayoutInflater();
                    final View v=li.inflate(R.layout.activity_word,null);
                    final int wordId=Integer.parseInt(((TextView)view.findViewById(R.id.wordId)).getText()+"");
                    final Word w = book.getList().get(wordId);
                    final EditText editEng = v.findViewById(R.id.editEng);
                    final EditText editChi = v.findViewById(R.id.editChi);
                    final EditText editExm = v.findViewById(R.id.editExm);
                    editEng.setText(w.eng);
                    editChi.setText(w.chi);
                    editExm.setText(w.exm);
                    dialog.setView(v);
                    dialog.setPositiveButton("修改", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            w.eng=editEng.getText()+"";
                            w.chi=editChi.getText()+"";
                            w.exm=editExm.getText()+"";
                            SQLAction.saveWord(bookId,wordId,editEng.getText()+"",editChi.getText()+"",editExm.getText()+"");
                            refresh();
                        }
                    });
                    dialog.setNeutralButton("删除", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            removeWord(wordId);
                            refresh();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }
    public void removeWord(int wordId){
        book.getList().remove(wordId);
        for(int i=wordId;i<book.getSize();i++){
            book.getList().get(i).id-=1;
        }
        SQLAction.removeWord(bookId,wordId);
    }
    public void removeBook(){
        wordbook.getData().getList().remove(bookId);
        for(int i=bookId;i<wordbook.getData().getSize();i++){
            wordbook.getData().getList().get(i).id-=1;
        }
        SQLAction.removeBook(bookId);
    }
}
