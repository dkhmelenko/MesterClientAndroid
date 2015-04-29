package com.khmelenko.lab.mester.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.adapter.TestListAdapter;
import com.khmelenko.lab.mester.network.response.TestingStepResponse;
import com.khmelenko.lab.mester.network.response.TestingTestCaseResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_test)
public class TestActivity extends BaseActivity {

    public static final String EXTRA_TEST_ID = "TestId";
    public static final String EXTRA_TESTCASE_TITLE = "TestcaseTitle";
    public static final String EXTRA_TEST_OBJ = "Test";

    @Extra(EXTRA_TESTCASE_TITLE)
    String mTestcaseTitle;

    @Extra(EXTRA_TEST_ID)
    String mTestId;

    @Extra(EXTRA_TEST_OBJ)
    String mTestObj;

    @ViewById(R.id.testListView)
    ListView mTestListView;

    @ViewById(R.id.testEmptyView)
    TextView mTestEmptyView;

    @ViewById(R.id.testProgressBar)
    View mProgressBar;

    private TestListAdapter mTestListAdapter;
    private TestingTestCaseResponse mTest;

    @AfterViews
    protected void init() {
        Gson gson = new Gson();
        mTest = gson.fromJson(mTestObj, TestingTestCaseResponse.class);

        mTestListAdapter = new TestListAdapter(this, mTest.getSteps());
        // during loading do not show the empty view text
        mTestEmptyView.setText("");
        mTestListView.setEmptyView(mTestEmptyView);
        mTestListView.setAdapter(mTestListAdapter);
    }

    @Click(R.id.testDoneBtn)
    protected void handleTestDone() {
        Gson gson = new Gson();
        String serializedData = gson.toJson(mTest);

        // return results
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TEST_OBJ, serializedData);
        setResult(RESULT_OK, intent);
        finish();
    }

}
