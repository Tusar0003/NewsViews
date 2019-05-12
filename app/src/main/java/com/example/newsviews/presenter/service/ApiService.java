package com.example.newsviews.presenter.service;

import com.example.newsviews.model.pojo.ArticleResponse;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v2/top-headlines")
    Call<ArticleResponse> getNewsHeadLines(@Query("country") String country, @Query("apiKey") String apiKey);

    @Headers("Content-Type: application/json")
    @GET("{number}/trivia")
    Call<JsonObject> getNumberResponse(@Path(value = "number") String number);
}
