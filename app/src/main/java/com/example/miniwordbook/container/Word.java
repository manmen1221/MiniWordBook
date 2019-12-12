package com.example.miniwordbook.container;

public class Word {
    public int id;
    public Book root;
    public String eng,chi,exm;
    public Word(Book root,String eng,String chi,String exm){
        this.root=root;
        this.eng=eng;
        this.chi=chi;
        this.exm=exm;
    }
    public Word(){

    }
}
