package com.example.miniwordbook;

public class StringTool {
    public static String change(int length,String value){
        if(length<3)return "...";
        if(value.length()<length)return value;
        return value.substring(0,length-1)+"...";
    }
}
