package com.khmelenko.lab.mester.activity.management;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.activity.BaseActivity;
import com.khmelenko.lab.mester.network.OnRestCallComplete;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_project)
public class AddProjectActivity extends BaseActivity {

    @ViewById(R.id.addProjectName)
    EditText mProjectName;

    @ViewById(R.id.addProjectDoneBtn)
    Button mAddProjectButton;

    @ViewById(R.id.addProjectProgressBar)
    ProgressBar mProgressBar;

    @AfterViews
    protected void init() {
        mProjectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAddProjectButton.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Click(R.id.addProjectDoneBtn)
    void handleAddProjectDone() {
        mAddProjectButton.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);

        String projectName = mProjectName.getText().toString();

        mRestClient.addProject(projectName, new OnRestCallComplete() {
            @Override
            public void onSuccess(Object obj) {

                mProgressBar.setVisibility(View.GONE);
                mAddProjectButton.setEnabled(true);

                finish();
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mAddProjectButton.setEnabled(true);

                Toast.makeText(AddProjectActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
