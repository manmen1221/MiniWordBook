package com.example.miniwordbook.container;

import java.util.ArrayList;
import java.util.List;

public class Book {
    public int id;
    public String name;

    public int getSize() {
        return wordList.size();
    }

    private List<Word> wordList = new ArrayList<Word>();

    public List<Word> getList() {
        return wordList;
    }

    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }
}
