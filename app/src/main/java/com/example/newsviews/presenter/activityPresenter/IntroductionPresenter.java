package com.example.newsviews.presenter.activityPresenter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.newsviews.R;
import com.example.newsviews.model.Preferences;

public class IntroductionPresenter {

    private static final String TAG = "IntroductionPresenter";

    private Context mContext;
    private View mView;

    public IntroductionPresenter(View view) {
        this.mView = view;
        mContext = (Context) view;
    }

    public void initializeDotsView() {
        mView.initializeDotsView();
    }

    public void addDotsIndicator(TextView[] mDots, int position) {
        mView.removeAllViews();

        for (int i=0;i<mDots.length;i++) {
            mDots[i] = new TextView(mContext);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(mContext.getResources().getColor(R.color.colorTransparentWhite));

            mView.addDotsViews(mDots[i]);
        }

        if (mDots.length > 0) {
            mView.setDotColorToWhite(mDots[position]);
        }
    }

    public void changeButtonStatus(int position, TextView[] mDots) {
        if (position == 0) {
            mView.disablePreviousButton();
        } else if (position == mDots.length - 1) {
            mView.changeNextButtonStatus();
        } else {
            mView.enableBothButton();
        }
    }

    public void checkButtonStatus(String buttonStatus) {
        if (buttonStatus.equalsIgnoreCase("Finish")) {
            Log.e(TAG, "checkButtonStatus: ");

            Preferences preferences = new Preferences(mContext);
            preferences.setStatus();

            mView.goToHomeActivity();
        }
    }

    public void checkLaunchStatus() {
        Preferences preferences = new Preferences(mContext);
        Log.e(TAG, "checkLaunchStatus: " + preferences.isFirstTime());

        if (!preferences.isFirstTime()) {
            mView.goToHomeActivity();
        }
    }

    public interface View {
        void removeAllViews();
        void initializeDotsView();
        void addDotsViews(TextView dotView);
        void setDotColorToWhite(TextView dotView);
        void disablePreviousButton();
        void changeNextButtonStatus();
        void enableBothButton();
        void goToHomeActivity();
    }
}
