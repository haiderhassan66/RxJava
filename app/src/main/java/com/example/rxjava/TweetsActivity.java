package com.example.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding4.view.RxView;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TweetsActivity extends AppCompatActivity {
    private static final String TAG = TweetsActivity.class.getSimpleName();
    private TweetsAdapter adapter;
    private Disposable disposable;

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

//        clickRx(heading);
//        runFunctionOnIO();
    }

    private static void clickRx(TextView heading) {
        RxView.clicks(heading).subscribe(
                click-> {
                    Log.d("checking", "Text Clicked");
                }
        );
    }

    private void runFunctionOnIO() {
        disposable = Observable.fromRunnable(this::testingLoop)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        onNext -> Log.d(TAG, "In onNext()"),
                        throwable -> {
                            throwable.printStackTrace();
                            Log.d(TAG, "In onError()" + throwable.getMessage());
                        },
                        () -> Log.d(TAG, "In onComplete()")
                );
    }

    public static List<TweetModel> convertJsonToList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<TweetModel>>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }

    public void testingLoop() {
        for (int i = 0; i < 100000; i++) {
//            Log.d(TAG, "position:" +i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable!=null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}