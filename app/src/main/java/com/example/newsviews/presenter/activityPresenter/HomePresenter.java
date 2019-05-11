package com.example.newsviews.presenter.activityPresenter;

import android.content.Context;

public class HomePresenter {

    private Context mContext;
    private View mView;

    public HomePresenter(Context context) {
        this.mContext = context;
        mView = (View) context;
    }

    public interface View {
        void showAlertDialog(String message);
    }
}
