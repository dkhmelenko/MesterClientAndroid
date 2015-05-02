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
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_add_testcase)
public class AddTestcaseActivity extends BaseActivity {

    public static final String EXTRA_PROJECT_ID = "ProjectId";

    @Extra(EXTRA_PROJECT_ID)
    String mProjectId;

    @ViewById(R.id.addTestcaseTitle)
    EditText mTestcaseTitle;

    @ViewById(R.id.addTestcaseDoneBtn)
    Button mAddTestcaseBtn;

    @ViewById(R.id.addTestcaseProgressBar)
    ProgressBar mProgressBar;

    @AfterViews
    protected void init() {
        mTestcaseTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAddTestcaseBtn.setEnabled(s.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Click(R.id.addTestcaseDoneBtn)
    void handleAddTestcaseDone() {
        mAddTestcaseBtn.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);

        String testcaseTitle = mTestcaseTitle.getText().toString();

        mRestClient.addTestcase(mProjectId, testcaseTitle, new OnRestCallComplete() {
            @Override
            public void onSuccess(Object obj) {

                mProgressBar.setVisibility(View.GONE);
                mAddTestcaseBtn.setEnabled(true);

                finish();
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mAddTestcaseBtn.setEnabled(true);

                Toast.makeText(AddTestcaseActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
