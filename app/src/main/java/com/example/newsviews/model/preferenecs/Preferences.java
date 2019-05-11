package com.example.newsviews.model.preferenecs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.newsviews.R;

public class Preferences {

    private static final String PREFERENCE_NAME = "Preferences";
    private static final String PREFERENCE_KEY_STATUS = "KeyHost";
    private static final String PREFERENCE_KEY_LOG_IN = "KeyLogIn";
    private static final String PREFERENCE_KEY_LAST_NAME = "KeyLastName";
    private static final String PREFERENCE_KEY_EMAIL = "KeyEmail";
    private static final String PREFERENCE_KEY_IMAGE_URL = "KeyImageUrl";

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

    // Saving the last name of the logged in user
    public void setLastName(String lastName) {
        editor.putString(PREFERENCE_KEY_LAST_NAME, lastName);
        editor.apply();
    }

    // Getting the last name of the logged in user
    public String getLastName() {
        return mPreferences.getString(PREFERENCE_KEY_LAST_NAME, "User Name");
    }

    // Saving the email of the logged in user
    public void setEmail(String email) {
        editor.putString(PREFERENCE_KEY_EMAIL, email);
        editor.apply();
    }

    // Getting the email of the logged in user
    public String getEmail() {
        return mPreferences.getString(PREFERENCE_KEY_EMAIL, "example@email.com");
    }

    // Saving the image url of the logged in user
    public void setImageUrl(String imageUrl) {
        editor.putString(PREFERENCE_KEY_IMAGE_URL, imageUrl);
        editor.apply();
    }

    // Getting the image url of the logged in user
    public String getImageUrl() {
        return mPreferences.getString(PREFERENCE_KEY_IMAGE_URL, "image_url");
    }
}
