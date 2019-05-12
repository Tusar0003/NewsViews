package com.example.newsviews.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.newsviews.R;
import com.example.newsviews.presenter.fragmentPresenter.ArticlePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleFragment extends DialogFragment implements ArticlePresenter.View {

    private static final String TAG = "ArticleFragment";

    private WebView mWebView;
    private ArticlePresenter mPresenter;

    private ProgressBar mProgressBar;

    public ArticleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mWebView = view.findViewById(R.id.web_view);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

//        WebViewClientImpl webViewClient = new WebViewClientImpl(getActivity());
//        mWebView.setWebViewClient(webViewClient);

        view.findViewById(R.id.image_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e(TAG, "onPageFinished: ");
                mPresenter.dismissProgressBar();
            }
        });

        Bundle bundle = getArguments();
        mPresenter = new ArticlePresenter(this);
        mPresenter.checkBundle(bundle);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public void setWebView(String url) {
        mWebView.loadUrl(url);
    }

    @Override
    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    @Override
    public void dismissProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
