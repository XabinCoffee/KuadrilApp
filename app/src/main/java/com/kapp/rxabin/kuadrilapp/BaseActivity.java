package com.kapp.rxabin.kuadrilapp;

import android.app.ProgressDialog;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

public class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    public ProgressBar mProgressBar;

    public void showProgressDialog() {
        if (mProgressBar == null) {
            mProgressBar = new ProgressBar(this);
            mProgressBar.setIndeterminate(true);
        }

        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        if (mProgressBar != null && mProgressBar.getVisibility()==View.VISIBLE) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressBar();
    }

}
