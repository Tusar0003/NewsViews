package com.example.newsviews.model;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String PREFERENCE_NAME = "Preferences";
    private static final String PREFERENCE_KEY_STATUS = "KeyHost";
    private static final String PREFERENCE_KEY_LOG_IN = "KeyLogIn";

    private Context mContext;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        this.mContext = context;
        mPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = mPreferences.edit();
    }

    // Setting the status whether the user is launching app for the first time or not
    public void setStatus() {
        editor.putBoolean(PREFERENCE_KEY_STATUS, false);
        editor.apply();
    }

    // Getting the status
    public boolean isFirstTime() {
        return mPreferences.getBoolean(PREFERENCE_KEY_STATUS, true);
    }

    // Creating user log in session
    public void setUserLoggedIn(boolean status) {
        editor.putBoolean(PREFERENCE_KEY_LOG_IN, status);
        editor.apply();
    }

    // Getting the status
    public boolean isUserLoggedIn() {
        return mPreferences.getBoolean(PREFERENCE_KEY_LOG_IN, false);
    }
}
