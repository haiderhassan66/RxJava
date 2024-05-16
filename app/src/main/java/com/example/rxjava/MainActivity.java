package com.example.rxjava;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GitHubRepoAdapter adapter;
    private Disposable subscriptionDisposable;
    private Disposable tweetsDisposable;
    private List<String> categories;
    private ListView listView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view_repos);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading...!");
        adapter = new GitHubRepoAdapter();
        listView.setAdapter(adapter);
        itemClickListener();
        getStarredRepos();
    }

    private void itemClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.show();
//                listView.setOnItemClickListener(null);
                getTweets(categories.get(position));
                Log.d(TAG, categories.get(position));
            }
        });
    }

    private void getTweets(String category) {
        tweetsDisposable = GitHubClient.getInstance().getTweets("$..tweets[?(@.category=='" + category + "')]")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        tweetModels -> {
                            Log.d(TAG, "In onNext()"+tweetModels);
                            dialog.dismiss();
                            Intent intent = new Intent(this,TweetsActivity.class);
                            intent.putExtra("heading",category);
                            intent.putExtra("tweets", new Gson().toJson(tweetModels));
                            startActivity(intent);
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            dialog.dismiss();
                            Log.d(TAG, "In onError()"+throwable.getMessage());
                        },
                        ()->{
                            Log.d(TAG, "In onComplete()");
                        }
                );
    }

    private void getStarredRepos() {
        subscriptionDisposable = GitHubClient.getInstance()
//                .getStarredRepos(username)
                .getQuotes()
//                .filter(new Predicate<List<String>>() {
//                    @Override
//                    public boolean test(List<String> strings) throws Throwable {
//                        return strings.stream().collect(Collectors.toList());
//                    }
//                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        gitHubRepos -> {
                            Log.d(TAG, "In onNext()"+ gitHubRepos.stream().distinct().collect(Collectors.toList()));
                            categories = gitHubRepos.stream().distinct().collect(Collectors.toList());
                            adapter.setGitHubRepos(categories);
                        },
                        // onError
                        throwable -> {
                            throwable.printStackTrace();
                            Log.d(TAG, "In onError()");
                        },
                        // onComplete
                        () -> Log.d(TAG, "In onComplete()")
                );
    }

    @Override
    protected void onDestroy() {
        if (subscriptionDisposable != null && !subscriptionDisposable.isDisposed()) {
            subscriptionDisposable.dispose();
        }
        if (tweetsDisposable != null && !tweetsDisposable.isDisposed()){
            tweetsDisposable.dispose();
        }
        super.onDestroy();
    }
}