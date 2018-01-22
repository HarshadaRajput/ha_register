package com.example.lenovo.myapp2;

/**
 * Created by lenovo on 05-Jan-18.
 */

class MakeCode {
    static String getCode(String word){
        word = word.toLowerCase();
        if(word.contains(" ")){
            word = word.replaceAll(" ","_");
        }
        return word;
    }
}
