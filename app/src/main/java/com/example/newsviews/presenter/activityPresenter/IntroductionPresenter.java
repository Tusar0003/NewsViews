package com.example.newsviews.presenter.activityPresenter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.example.newsviews.R;
import com.example.newsviews.model.preferenecs.Preferences;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class IntroductionPresenter {

    private static final String TAG = "IntroductionPresenter";

    private Context mContext;
    private View mView;
    private Preferences mPreferences;

    public IntroductionPresenter(View view) {
        this.mView = view;
        mContext = (Context) view;
        mPreferences = new Preferences(mContext);
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
            mPreferences.setStatus();

            mView.showLogInLayout();
        }
    }

    public void checkLaunchStatus() {
        Log.e(TAG, "checkLaunchStatus: " + mPreferences.isFirstTime());

        if (!mPreferences.isFirstTime()) {
            if (mPreferences.isUserLoggedIn()) {
                goToHomeActivity();
                return;
            }

//            LoginManager.getInstance().logOut();
            mView.showLogInLayout();
        }
    }

    public void checkAccessToken(AccessToken currentAccessToken) {
        // If current access token is null that user is log out
        if (currentAccessToken == null) {
            mPreferences.setUserLoggedIn(false);
            Log.e(TAG, "onCurrentAccessTokenChanged: user is logged out.");
        } else {
            mPreferences.setUserLoggedIn(true);
            loadUserProfile(currentAccessToken);
        }
    }

    private void loadUserProfile(AccessToken currentAccessToken) {
        // Gor getting the user info we have to make graph API request
        GraphRequest graphRequest = GraphRequest.newMeRequest(currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    Log.e(TAG, "onCompleted: " + object);

                    String firstName = object.getString("first_name");
                    String lastName = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                    // From ID creating the image url
                    String imageUrl = "https://graph.facebook.com/" + id + "picture?type=normal";

                    Log.e(TAG, "onCompleted: " + firstName + "\n" + lastName + "\n" + email + "\n" +
                            id + "\n" + imageUrl);
                } catch (JSONException e) {
                    Log.e(TAG, "onCompleted: " + e);
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
//        parameters.putString("fields", "first_name, last_name, email, id");
        parameters.putString("fields", "email");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    public void goToHomeActivity() {
        mView.goToHomeActivity();
    }

    public void showAlertDialog(String message) {
        mView.showAlertDialog(message);
    }

    public interface View {
        void removeAllViews();
        void initializeDotsView();
        void addDotsViews(TextView dotView);
        void setDotColorToWhite(TextView dotView);
        void disablePreviousButton();
        void changeNextButtonStatus();
        void enableBothButton();
        void showLogInLayout();
        void goToHomeActivity();
        void showAlertDialog(String message);
    }
}
