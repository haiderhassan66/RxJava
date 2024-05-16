package com.example.rxjava;



import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("user/{user}/starred")
    Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);

    @GET("issues")
    Observable<List<GitHubRepo>> issues();

    @GET("/v3/b/65424c7354105e766fc9f1df?meta=false")
    @Headers("X-JSON-Path:tweets..category")
    Observable<List<String>> quotes();

    @GET("/v3/b/65424c7354105e766fc9f1df?meta=false")
    Observable<List<TweetModel>> getTweets(@Header("X-JSON-Path") String category);
}
