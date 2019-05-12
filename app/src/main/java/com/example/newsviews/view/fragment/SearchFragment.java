package com.example.newsviews.view.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newsviews.R;
import com.example.newsviews.presenter.fragmentPresenter.SearchPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends DialogFragment implements SearchPresenter.View {

    private EditText mNumberEditText;
    private TextView mResultTextView;

    private SearchPresenter mPresenter;
    private ProgressBar mProgressBar;
    private AlertDialog mAlertDialog;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        mNumberEditText = view.findViewById(R.id.edit_text_number);
        mResultTextView = view.findViewById(R.id.text_view_result);

        mProgressBar = view.findViewById(R.id.progress_bar);
        mPresenter = new SearchPresenter(this);
        mAlertDialog = new AlertDialog(getContext());

        view.findViewById(R.id.button_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getResult(mNumberEditText.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.AppTheme);
    }

    @Override
    public void setErrorMessage() {
        mNumberEditText.setError(getString(R.string.empty));
    }

    @Override
    public void setResult(String result) {
        mResultTextView.setText(result);
    }

    @Override
    public void showAlertDialog(String message) {
        mAlertDialog.show(message);
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}
