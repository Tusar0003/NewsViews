package com.example.newsviews.view.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.newsviews.R;
import com.example.newsviews.presenter.activityPresenter.IntroductionPresenter;
import com.example.newsviews.presenter.adapter.SliderAdapter;
import com.example.newsviews.view.fragment.AlertDialog;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class IntroductionActivity extends AppCompatActivity implements IntroductionPresenter.View {

    private static final String TAG = "IntroductionActivity";

    private IntroductionPresenter mPresenter;

    private ViewPager mViewPager;
    private LinearLayout mDotLayout;
    private SliderAdapter mAdapter;

    private RelativeLayout mIntroductionLayout;
    private LinearLayout mLogInLayout;
    private Button mPreviousButton;
    private Button mNextButton;
    private TextView[] mDots;

    private LoginButton mFBLoginButton;
    private CallbackManager mFBCallbackManager;

    private int mCurrentPage = 0;

    private AlertDialog mAlertDialog;

    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            mPresenter.addDotsIndicator(mDots, i);
            mPresenter.changeButtonStatus(i, mDots);
            mCurrentPage = i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    AccessTokenTracker mAccessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            mPresenter.checkAccessToken(currentAccessToken);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_introduction);

        getSupportActionBar().hide();

        mPresenter = new IntroductionPresenter(this);
        mAlertDialog = new AlertDialog(this);

        mViewPager = findViewById(R.id.view_pager);
        mDotLayout = findViewById(R.id.layout_dot);

        mIntroductionLayout = findViewById(R.id.layout_introduction);
        mLogInLayout = findViewById(R.id.layout_log_in);
        mPreviousButton = findViewById(R.id.button_previous);
        mNextButton = findViewById(R.id.button_next);

        mAdapter = new SliderAdapter(this);
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(mOnPageChangeListener);

        mPresenter.initializeDotsView();
        mPresenter.addDotsIndicator(mDots, 0);

        mFBLoginButton = findViewById(R.id.fb_login_button);

        mFBCallbackManager = CallbackManager.Factory.create();
        mFBLoginButton.setReadPermissions(Arrays.asList("email"));

        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.checkButtonStatus(mNextButton.getText().toString());
                mViewPager.setCurrentItem(mCurrentPage + 1);
            }
        });

        mFBLoginButton.registerCallback(mFBCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e(TAG, "onSuccess: " + loginResult.toString());
            }

            @Override
            public void onCancel() {
                Log.e(TAG, "onCancel: ");
            }

            @Override
            public void onError(FacebookException error) {
                mPresenter.showAlertDialog(error.getMessage());
                Log.e(TAG, "onError: " + error);
            }
        });

        findViewById(R.id.button_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.goToHomeActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FacebookSdk.sdkInitialize(this);
        mPresenter.checkLaunchStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mFBCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void removeAllViews() {
        mDotLayout.removeAllViews();
    }

    @Override
    public void initializeDotsView() {
        mDots = new TextView[3];
    }

    @Override
    public void addDotsViews(TextView dotView) {
        mDotLayout.addView(dotView);
    }

    @Override
    public void setDotColorToWhite(TextView dotView) {
        dotView.setTextColor(getResources().getColor(R.color.colorWhite));
    }

    @Override
    public void disablePreviousButton() {
        mNextButton.setEnabled(true);
        mPreviousButton.setEnabled(false);
        mPreviousButton.setVisibility(android.view.View.GONE);
    }

    @Override
    public void changeNextButtonStatus() {
        mNextButton.setEnabled(true);
        mPreviousButton.setEnabled(true);
        mPreviousButton.setVisibility(android.view.View.VISIBLE);

        mNextButton.setText(getString(R.string.finish));
    }

    @Override
    public void enableBothButton() {
        mNextButton.setEnabled(true);
        mPreviousButton.setEnabled(true);
        mPreviousButton.setVisibility(android.view.View.VISIBLE);

        mNextButton.setText(getString(R.string.next));
        mPreviousButton.setText(getString(R.string.previous));
    }

    @Override
    public void showLogInLayout() {
        mLogInLayout.setVisibility(View.VISIBLE);
        mIntroductionLayout.setVisibility(View.GONE);
    }

    @Override
    public void goToHomeActivity() {
        finish();
        startActivity(new Intent(IntroductionActivity.this, HomeActivity.class));
    }

    @Override
    public void showAlertDialog(String message) {
        mAlertDialog.show(message);
    }
}
