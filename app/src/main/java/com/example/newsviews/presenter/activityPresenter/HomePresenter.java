package com.example.newsviews.presenter.activityPresenter;

import android.content.Context;
import android.util.Log;

import com.example.newsviews.R;
import com.example.newsviews.model.preferenecs.Preferences;

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
                break;
            case R.id.menu_about:
                Log.e(TAG, "onNavigationItemSelected: ");
                mView.showAboutDialog();
                break;
            case R.id.menu_exit:
                Log.e(TAG, "onNavigationItemSelected: ");
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

    public void finishActivity() {
        mView.finishActivity();
    }

    public interface View {
        void showLogOutConfirmation();
        void finishActivity();
        void showAboutDialog();
        void showAlertDialog(String message);
    }
}
