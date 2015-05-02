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

@EActivity(R.layout.activity_add_step)
public class AddStepActivity extends BaseActivity {

    public static final String EXTRA_TESTCASE_ID = "TestcaseId";

    @Extra(EXTRA_TESTCASE_ID)
    String mTestcaseId;

    @ViewById(R.id.addStepNumber)
    EditText mStepNumber;

    @ViewById(R.id.addStepText)
    EditText mStepText;

    @ViewById(R.id.addStepDoneBtn)
    Button mAddStepDoneBtn;

    @ViewById(R.id.addStepProgressBar)
    ProgressBar mProgressBar;

    @AfterViews
    protected void init() {
        mStepNumber.addTextChangedListener(getTextInputHandler());
        mStepText.addTextChangedListener(getTextInputHandler());
    }

    /**
     * Gets text watcher for the input field
     *
     * @return TextWatcher instance
     */
    private TextWatcher getTextInputHandler() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int numberLength = mStepNumber.getText().length();
                int textLength = mStepText.getText().length();
                mAddStepDoneBtn.setEnabled(numberLength > 0 && textLength > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        return watcher;
    }

    @Click(R.id.addStepDoneBtn)
    void handleAddStepDone() {
        mAddStepDoneBtn.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);

        int stepNumber = Integer.parseInt(mStepNumber.getText().toString());
        String stepText = mStepText.getText().toString();

        mRestClient.addStep(mTestcaseId, stepNumber, stepText, new OnRestCallComplete() {
            @Override
            public void onSuccess(Object obj) {

                mProgressBar.setVisibility(View.GONE);
                mAddStepDoneBtn.setEnabled(true);

                finish();
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mAddStepDoneBtn.setEnabled(true);

                Toast.makeText(AddStepActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
