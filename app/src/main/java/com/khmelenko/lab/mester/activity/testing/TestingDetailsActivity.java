package com.khmelenko.lab.mester.activity.testing;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.activity.BaseActivity;
import com.khmelenko.lab.mester.adapter.TestListAdapter;
import com.khmelenko.lab.mester.adapter.TestingDetailsListAdapter;
import com.khmelenko.lab.mester.model.TestStatus;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.response.StepResponse;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;
import com.khmelenko.lab.mester.network.response.TestingResponse;
import com.khmelenko.lab.mester.network.response.TestingStepResponse;
import com.khmelenko.lab.mester.network.response.TestingTestCaseResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Testing details activity
 * @author Dmytro Khmelenko
 */
@EActivity(R.layout.activity_testing_details)
public class TestingDetailsActivity extends BaseActivity {

    public static final String EXTRA_TESTING_DETAILS_OBJ = "TestingDetails";

    @Extra(EXTRA_TESTING_DETAILS_OBJ)
    String mTestingDetailsObj;

    @ViewById(R.id.testingDetailsListView)
    ListView mTestDetailsListView;

    @ViewById(R.id.testingDetailsEmptyView)
    TextView mTestingDetailsEmptyView;

    @ViewById(R.id.testingDetailsProgressBar)
    View mProgressBar;

    private TestingDetailsListAdapter mTestListAdapter;
    private TestingResponse mTestingDetails;
    private List<TestingTestCaseResponse> mTests;

    @AfterViews
    protected void init() {
        Gson gson = new Gson();
        mTestingDetails = gson.fromJson(mTestingDetailsObj, TestingResponse.class);
        mTests = new ArrayList<>();

        mTestListAdapter = new TestingDetailsListAdapter(this, mTests);
        // during loading do not show the empty view text
        mTestingDetailsEmptyView.setText("");
        mTestDetailsListView.setEmptyView(mTestingDetailsEmptyView);
        mTestDetailsListView.setAdapter(mTestListAdapter);

        loadTestcaseDetails();
    }

    /**
     * Loads testcase details
     */
    private void loadTestcaseDetails() {
        // load testcases for the project
        mRestClient.getTestcases(mTestingDetails.getProjectId(), new OnRestCallComplete<List<TestCaseResponse>>() {
            @Override
            public void onSuccess(List<TestCaseResponse> responseBody) {

                // assign loaded testcases
                assignTestcasesToTest(mTestingDetails, responseBody);

                mTests.clear();
                mTests.addAll(mTestingDetails.getTestCases());
                mTestListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
                mTestingDetailsEmptyView.setText(R.string.testing_details_empty_list);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mTestingDetailsEmptyView.setText(R.string.testing_details_empty_list);

                Toast.makeText(TestingDetailsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Assigns testcases to the test
     *
     * @param newTest   New test
     * @param testcases Testcases
     */
    private void assignTestcasesToTest(TestingResponse newTest, List<TestCaseResponse> testcases) {
        for (TestingTestCaseResponse testingTestCase : newTest.getTestCases()) {
            TestCaseResponse testcase = findTestcaseById(testingTestCase.getTestcaseId(), testcases);
            if (testcase != null) {
                testingTestCase.setName(testcase.getTitle());
                assignTestStepsToTest(testingTestCase, testcase.getSteps());
            }
        }
    }

    /**
     * Assigns steps to the testcase
     *
     * @param testcase testcase
     * @param steps    List of steps
     */
    private void assignTestStepsToTest(TestingTestCaseResponse testcase, List<StepResponse> steps) {
        if (testcase.getSteps() != null) {
            for (TestingStepResponse testingStep : testcase.getSteps()) {
                StepResponse step = findStepById(testingStep.getTestStepId(), steps);
                if (step != null) {
                    testingStep.setDescription(step.getText());
                    testingStep.setNumber(step.getNumber());
                }
            }
        }
    }

    /**
     * Searches testcase by id
     *
     * @param testcaseId Testcase ID
     * @param testcases  List of testcases
     * @return Found testcase or null
     */
    private TestCaseResponse findTestcaseById(String testcaseId, List<TestCaseResponse> testcases) {
        TestCaseResponse foundTestcase = null;
        for (TestCaseResponse testcase : testcases) {
            if (testcaseId.equals(testcase.getId())) {
                foundTestcase = testcase;
                break;
            }
        }
        return foundTestcase;
    }

    /**
     * Searches step by id
     *
     * @param stepId Step ID
     * @param steps  List of steps
     * @return Found step or null
     */
    private StepResponse findStepById(String stepId, List<StepResponse> steps) {
        StepResponse foundStep = null;
        for (StepResponse step : steps) {
            if (stepId.equals(step.getId())) {
                foundStep = step;
                break;
            }
        }
        return foundStep;
    }
}
