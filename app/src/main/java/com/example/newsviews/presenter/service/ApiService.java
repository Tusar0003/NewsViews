package com.example.newsviews.presenter.service;

import com.example.newsviews.model.pojo.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("v2/top-headlines")
    Call<ArticleResponse> getNewsHeadLines(@Query("country") String country, @Query("apiKey") String apiKey);
}
