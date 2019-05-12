package com.example.newsviews.presenter.fragmentPresenter;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.newsviews.view.fragment.ArticleFragment;

public class ArticlePresenter {

    private static final String TAG = "ArticlePresenter";

    private View mView;

    public ArticlePresenter(ArticleFragment articleFragment) {
        this.mView = articleFragment;
    }

    public void checkBundle(Bundle bundle) {
        if (bundle != null) {
            mView.setWebView(bundle.getString("url"));
        }
    }

    public void dismissProgressBar() {
        if (mView.getProgressBar().getVisibility() == android.view.View.VISIBLE) {
            mView.dismissProgressBar();
        }
    }

    public interface View {
        void setWebView(String url);
        ProgressBar getProgressBar();
        void dismissProgressBar();
    }
}
