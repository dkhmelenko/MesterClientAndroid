package com.khmelenko.lab.mester.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.adapter.NewTestingListAdapter;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.response.StepResponse;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;
import com.khmelenko.lab.mester.network.response.TestingResponse;
import com.khmelenko.lab.mester.network.response.TestingStepResponse;
import com.khmelenko.lab.mester.network.response.TestingTestCaseResponse;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * New testing activity
 *
 * @author Dmytro Khmelenko
 */
@EActivity(R.layout.activity_new_testing)
public class NewTestingActivity extends BaseActivity {

    public static final String EXTRA_PROJECT_NAME = "ProjectName";
    public static final String EXTRA_PROJECT_ID = "ProjectId";

    private static final int TEST_REQUEST_CODE = 0;

    @Extra(EXTRA_PROJECT_NAME)
    String mProjectName;

    @Extra(EXTRA_PROJECT_ID)
    String mProjectId;

    @ViewById(R.id.newTestingListView)
    ListView mTestsListView;

    @ViewById(R.id.newTestingEmptyView)
    TextView mTestEmptyView;

    @ViewById(R.id.newTestingProgressBar)
    View mProgressBar;

    private NewTestingListAdapter mTestsListAdapter;
    private List<TestingTestCaseResponse> mTests;

    @AfterViews
    protected void init() {
        mTests = new ArrayList<>();
        mTestsListAdapter = new NewTestingListAdapter(this, mTests);
        // during loading do not show the empty view text
        mTestEmptyView.setText("");
        mTestsListView.setEmptyView(mTestEmptyView);
        mTestsListView.setAdapter(mTestsListAdapter);

        mTestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestingTestCaseResponse selected = mTests.get(position);
                Gson gson = new Gson();
                String serializedData = gson.toJson(selected);

                TestActivity_.intent(NewTestingActivity.this)
                        .extra(TestActivity.EXTRA_TESTCASE_TITLE, selected.getName())
                        .extra(TestActivity.EXTRA_TEST_OBJ, serializedData)
                        .startForResult(TEST_REQUEST_CODE);
            }
        });

        loadData();
    }

    @OnActivityResult(TEST_REQUEST_CODE)
    void onResult(Intent data) {
        String testObj = data.getStringExtra(TestActivity.EXTRA_TEST_OBJ);
        Gson gson = new Gson();
        TestingTestCaseResponse completedTest = gson.fromJson(testObj, TestingTestCaseResponse.class);

        for (int i = 0; i < mTests.size(); i++) {
            TestingTestCaseResponse test = mTests.get(i);
            if (test.getId().equals(completedTest.getId())) {
                test.setSteps(completedTest.getSteps());
                break;
            }
        }
    }

    /**
     * Loads testing data
     */
    protected void loadData() {

        mRestClient.generateTests(mProjectId, new OnRestCallComplete<TestingResponse>() {
            @Override
            public void onSuccess(TestingResponse responseBody) {
                handleTestLoaded(responseBody);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mTestEmptyView.setText(R.string.new_testing_empty_list);

                Toast.makeText(NewTestingActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Handles loaded test
     *
     * @param newTest Loaded test
     */
    private void handleTestLoaded(final TestingResponse newTest) {
        // load testcases for the project
        mRestClient.getTestcases(mProjectId, new OnRestCallComplete<List<TestCaseResponse>>() {
            @Override
            public void onSuccess(List<TestCaseResponse> responseBody) {

                // assign loaded testcases
                assignTestcasesToTest(newTest, responseBody);

                mTests.clear();
                mTests.addAll(newTest.getTestCases());
                mTestsListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
                mTestEmptyView.setText(R.string.new_testing_empty_list);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mTestEmptyView.setText(R.string.new_testing_empty_list);

                Toast.makeText(NewTestingActivity.this, message, Toast.LENGTH_LONG).show();
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
        for (TestingStepResponse testingStep : testcase.getSteps()) {
            StepResponse step = findStepById(testingStep.getId(), steps);
            if (step != null) {
                testingStep.setDescription(step.getText());
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
