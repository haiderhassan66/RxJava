package com.example.rxjava;

import androidx.annotation.NonNull;

public class TweetModel {
    public String category;
    public String text;

    public TweetModel(String category, String text){
        this.category = category;
        this.text = text;
    }

    @NonNull
    @Override
    public String toString() {
        return "TweetModel{" +
                "category='" + category + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
