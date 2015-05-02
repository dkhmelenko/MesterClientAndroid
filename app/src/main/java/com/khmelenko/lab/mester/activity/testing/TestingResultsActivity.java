package com.khmelenko.lab.mester.activity.testing;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.activity.BaseActivity;
import com.khmelenko.lab.mester.adapter.TestingResultsListAdapter;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.response.TestingResponse;
import com.khmelenko.lab.mester.utils.StringUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.parceler.transfuse.annotations.OnResume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_testing_results)
public class TestingResultsActivity extends BaseActivity {

    public static final String EXTRA_PROJECT_NAME = "ProjectName";
    public static final String EXTRA_PROJECT_ID = "ProjectId";

    @Extra(EXTRA_PROJECT_NAME)
    String mProjectName;

    @Extra(EXTRA_PROJECT_ID)
    String mProjectId;

    @ViewById(R.id.testsListView)
    ListView mTestsListView;

    @ViewById(R.id.testsEmptyView)
    TextView mTestEmptyView;

    @ViewById(R.id.testsProgressBar)
    View mProgressBar;

    private TestingResultsListAdapter mTestResultsListAdapter;
    private List<TestingResponse> mTestingResults;

    @AfterViews
    protected void init() {
        mTestingResults = new ArrayList<>();
        mTestResultsListAdapter = new TestingResultsListAdapter(this, mTestingResults);
        // during loading do not show the empty view text
        mTestEmptyView.setText("");
        mTestsListView.setEmptyView(mTestEmptyView);
        mTestsListView.setAdapter(mTestResultsListAdapter);

        mTestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Show testing details
            }
        });
    }

    @Click(R.id.testingResultsNewTestingBtn)
    protected void handleNewTesting() {
        NewTestingActivity_.intent(TestingResultsActivity.this)
                .extra(NewTestingActivity.EXTRA_PROJECT_NAME, mProjectName)
                .extra(NewTestingActivity.EXTRA_PROJECT_ID, mProjectId).start();
    }

    @OnResume
    protected void onResume() {
        super.onResume();

        mRestClient.getTestingResults(mProjectId, new OnRestCallComplete<List<TestingResponse>>() {
            @Override
            public void onSuccess(List<TestingResponse> responseBody) {
                Collections.sort(responseBody, new Comparator<TestingResponse>() {
                    @Override
                    public int compare(TestingResponse lhs, TestingResponse rhs) {
                        Date lhsDate = StringUtils.parseDate(lhs.getCreationDate());
                        Date rhsDate = StringUtils.parseDate(rhs.getCreationDate());
                        return rhsDate.compareTo(lhsDate);
                    }
                });

                mTestingResults.clear();
                mTestingResults.addAll(responseBody);
                mTestResultsListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
                mTestEmptyView.setText(R.string.testing_results_empty_list);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mTestEmptyView.setText(R.string.testing_results_empty_list);

                Toast.makeText(TestingResultsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}
