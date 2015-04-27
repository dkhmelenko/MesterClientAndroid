package com.khmelenko.lab.mester.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.adapter.NewTestingListAdapter;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;
import com.khmelenko.lab.mester.network.response.TestingResponse;
import com.khmelenko.lab.mester.network.response.TestingTestCaseResponse;
import com.khmelenko.lab.mester.network.retrofit.RestClientRetrofit;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.parceler.transfuse.annotations.OnResume;

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
        mRestClient = new RestClientRetrofit();

        mTests = new ArrayList<>();
        mTestsListAdapter = new NewTestingListAdapter(this, mTests);
        // during loading do not show the empty view text
        mTestEmptyView.setText("");
        mTestsListView.setEmptyView(mTestEmptyView);
        mTestsListView.setAdapter(mTestsListAdapter);

        mTestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Show testing details
            }
        });
    }

    @OnResume
    protected void onResume() {
        super.onResume();

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

}
