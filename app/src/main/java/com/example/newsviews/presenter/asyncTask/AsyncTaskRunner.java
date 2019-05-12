package com.example.newsviews.presenter.asyncTask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.webkit.WebView;

import com.example.newsviews.view.activity.HomeActivity;

import java.net.URL;

public class AsyncTaskRunner extends AsyncTask<URL, Integer, WebView> {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected WebView doInBackground(URL... urls) {
        return null;
    }

    @Override
    protected void onPostExecute(WebView webView) {
        super.onPostExecute(webView);
    }
}
