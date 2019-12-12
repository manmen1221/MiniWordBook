package com.example.miniwordbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miniwordbook.SQL.SQLAction;
import com.example.miniwordbook.container.Search;
import com.example.miniwordbook.container.Word;

import java.util.List;

public class SearchActivity extends AppCompatActivity {
    List list;
    EditText value;
    LinearLayout view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        value=findViewById(R.id.搜索内容);
        value.requestFocus();
        Button bt=findViewById(R.id.搜索);
        view=findViewById(R.id.单词列表);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(value.getText()+"");
            }
        });
    }
    private void search(String value){
        view.removeAllViews();
        list= SQLAction.search(value);
        int size=list.size();
        for(int i=0;i<size;i++){
            Search search=(Search)(list.get(i));
            Word word=WordBook.wordBook().getData().getList().get(search.bookid).getList().get(search.wordid);
            LinearLayout wordView=(LinearLayout)(getLayoutInflater().inflate(R.layout.word,null));
            TextView engView=wordView.findViewById(R.id.wordEng);
            engView.setText(word.eng);
            TextView chiView=wordView.findViewById(R.id.wordChi);
            chiView.setText(word.chi);
            TextView exmView=wordView.findViewById(R.id.wordExm);
            exmView.setText(word.exm);
            TextView rootView=wordView.findViewById(R.id.wordRoot);
            rootView.setVisibility(View.VISIBLE);
            rootView.setText("于单词本："+(search.bookid+1));
            view.addView(wordView);
        }
    }
}
