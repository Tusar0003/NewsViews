package com.example.newsviews.presenter.activityPresenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.newsviews.R;
import com.example.newsviews.model.pojo.Article;
import com.example.newsviews.model.pojo.ArticleResponse;
import com.example.newsviews.model.preferenecs.Preferences;
import com.example.newsviews.presenter.service.ApiClient;
import com.example.newsviews.presenter.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter {

    private static final String TAG = "HomePresenter";

    private Context mContext;
    private View mView;
    private Preferences mPreferences;

    public HomePresenter(Context context) {
        this.mContext = context;
        mView = (View) context;
        mPreferences = new Preferences(context);
    }

    public void onNavigationItemSelected(int itemId) {
        switch (itemId) {
            case R.id.menu_home:
                mView.closeNavigationDrawer();
                break;
            case R.id.menu_about:
                mView.showAboutDialog();
                break;
            case R.id.menu_exit:
                checkLogInStatus();
                break;
        }
    }

    private void checkLogInStatus() {
        if (mPreferences.isUserLoggedIn()) {
            mView.showLogOutConfirmation();
        } else {
            finishActivity();
        }
    }

    public void getArticles() {
        showProgressDialog();

        ApiClient.BASE_URL = "https://newsapi.org/";
        ApiService apiService = ApiClient.getApiClient("https://newsapi.org/").create(ApiService.class);

        Call<ArticleResponse> call = apiService.getNewsHeadLines("us", mContext.getString(R.string.apiKey));
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                try {
                    if (response.isSuccessful() && response.body().getStatus().equalsIgnoreCase("ok")) {
                        if (response.body().getArticles().size() > 0) {
                            List<Article> articleList = new ArrayList<>(response.body().getArticles());
                            mView.setArticleList(articleList);
                        }
                    } else {
                        mView.showAlertDialog(response.message());
                    }
                } catch (Exception e) {
                    mView.showAlertDialog(e.getMessage());
                }

                dismissProgressDialog();
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                dismissProgressDialog();
                mView.showAlertDialog(t.getMessage());
            }
        });
    }

    private void showProgressDialog() {
        if (!mView.getProgressDialog().isShowing()) {
            mView.showProgressDialog();
        }
    }

    private void dismissProgressDialog() {
        if (mView.getProgressDialog().isShowing()) {
            mView.dismissProgressDialog();
        }
    }

    public void finishActivity() {
        mView.finishActivity();
    }

    public interface View {
        void setArticleList(List<Article> articleList);
        void closeNavigationDrawer();
        void showLogOutConfirmation();
        void finishActivity();
        void showAboutDialog();
        ProgressDialog getProgressDialog();
        void showProgressDialog();
        void dismissProgressDialog();
        void showAlertDialog(String message);
    }
}
