package com.example.newsviews.presenter.fragmentPresenter;

import android.text.TextUtils;
import android.util.Log;

import com.example.newsviews.presenter.service.ApiClient;
import com.example.newsviews.presenter.service.ApiService;
import com.example.newsviews.view.fragment.SearchFragment;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenter {

    private static final String TAG = "SearchPresenter";

    private View mView;

    public SearchPresenter(SearchFragment searchFragment) {
        this.mView = searchFragment;
    }

    public void getResult(String number) {
        if (!TextUtils.isEmpty(number)) {
            mView.showProgressBar();

            ApiClient.BASE_URL = "numbersapi.com/";
            ApiClient.setApiClient("http://numbersapi.com/");
            ApiService apiService = ApiClient.getApiClient("http://numbersapi.com/").create(ApiService.class);

            Call<JsonObject> call = apiService.getNumberResponse(number);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        Log.e(TAG, "onResponse: " + response);

                        if (response.isSuccessful()) {
                            Log.e(TAG, "onResponse: " + response.body().get("text"));
                            mView.setResult(response.body().get("text").toString());
                        } else {
                            mView.showAlertDialog(response.message());
                        }

                        mView.dismissProgressBar();
                    } catch (Exception e) {
                        Log.e(TAG, "onResponse: " + e);
                        mView.showAlertDialog(e.getMessage());
                        mView.dismissProgressBar();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t);
                    mView.showAlertDialog(t.getMessage());
                    mView.dismissProgressBar();
                }
            });
        } else {
            mView.setErrorMessage();
        }
    }

    public interface View {
        void setErrorMessage();
        void setResult(String result);
        void showAlertDialog(String message);
        void showProgressBar();
        void dismissProgressBar();
    }
}
