package com.example.newsviews.view.fragment;

import android.content.Context;
import android.content.DialogInterface;

import com.example.newsviews.R;

public class AlertDialog {

    private Context mContext;

    public AlertDialog(Context context) {
        mContext = context;
    }

    public void show(String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getString(R.string.error))
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton(mContext.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }
}
