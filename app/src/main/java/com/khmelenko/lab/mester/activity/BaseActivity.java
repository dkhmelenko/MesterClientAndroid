package com.khmelenko.lab.mester.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.khmelenko.lab.mester.MesterApplication;
import com.khmelenko.lab.mester.network.RestClient;

import javax.inject.Inject;

/**
 * Base activity class
 *
 * @author Dmytro Khmelenko
 */
public abstract class BaseActivity extends ActionBarActivity {

    @Inject
    public RestClient mRestClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MesterApplication) getApplication()).inject(this);
    }
}
