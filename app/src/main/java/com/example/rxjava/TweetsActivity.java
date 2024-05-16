package com.example.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TweetsActivity extends AppCompatActivity {

    private TweetsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tweets);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final ListView listView = (ListView) findViewById(R.id.tweet_list);
        final TextView heading = (TextView) findViewById(R.id.heading_tv);

        Intent intent = getIntent();
        String head = intent.getStringExtra("heading");
        String list = intent.getStringExtra("tweets");

        heading.setText(head);
        List<TweetModel> tweets = convertJsonToList(list);

        adapter = new TweetsAdapter(tweets);
        listView.setDividerHeight(0);
        listView.setAdapter(adapter);
    }
    public static List<TweetModel> convertJsonToList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<TweetModel>>() {}.getType();
        return gson.fromJson(jsonString, listType);
    }
}