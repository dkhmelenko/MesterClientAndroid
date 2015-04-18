package com.khmelenko.lab.mester.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.adapter.TestcasesListAdapter;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.RestClient;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;
import com.khmelenko.lab.mester.network.retrofit.RestClientRetrofit;
import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.parceler.transfuse.annotations.OnResume;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity with the project testcases
 *
 * @author Dmytro Khmelenko
 */
@EActivity(R.layout.activity_testcases)
public class TestcasesActivity extends ActionBarActivity {

    public static final String EXTRA_PROJECT_NAME = "ProjectName";
    public static final String EXTRA_PROJECT_ID = "ProjectId";

    @Extra(EXTRA_PROJECT_NAME)
    String mProjectName;

    @Extra(EXTRA_PROJECT_ID)
    String mProjectId;

    @ViewById(R.id.testcaseProjectName)
    TextView mTestcaseProjectName;

    @ViewById(R.id.testcasesListView)
    ListView mTestcasesListView;

    @ViewById(R.id.testcasesEmptyView)
    TextView mTestcasesEmptyView;

    @ViewById(R.id.addTestcaseBtn)
    FloatingActionButton mAddTestcaseBtn;

    @ViewById(R.id.addTestcaseProgressBar)
    View mProgressBar;

    private TestcasesListAdapter mTestcaseListAdapter;
    private List<TestCaseResponse> mTestcasesList;
    private RestClient restClient;

    @AfterViews
    protected void init() {
        restClient = new RestClientRetrofit();

        String projectName = getString(R.string.testcases_project_name, mProjectName);
        mTestcaseProjectName.setText(projectName);

        mTestcasesList = new ArrayList<>();
        mTestcaseListAdapter = new TestcasesListAdapter(this, mTestcasesList);
        // during loading do not show the empty view text
        mTestcasesEmptyView.setText("");
        mTestcasesListView.setEmptyView(mTestcasesEmptyView);
        mTestcasesListView.setAdapter(mTestcaseListAdapter);

        mTestcasesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TestCaseResponse toDelete = mTestcasesList.get(position);
                startDeletingTestcase(toDelete);
                return false;
            }
        });
    }

    /**
     * Initiates deletion testcase process
     *
     * @param testcase Testcase for deletion
     */
    private void startDeletingTestcase(final TestCaseResponse testcase) {
        String message = getString(R.string.delete_testcase_msg, testcase.getTitle());

        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.delete_testcase_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteTestcase(testcase);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    /**
     * Deletes selected testcase
     *
     * @param testcaseToDelete Testcase for deletion
     */
    private void deleteTestcase(final TestCaseResponse testcaseToDelete) {
        mProgressBar.setVisibility(View.VISIBLE);

        restClient.deleteTestcase(testcaseToDelete.getId(), new OnRestCallComplete() {
            @Override
            public void onSuccess(Object responseBody) {
                mTestcasesList.remove(testcaseToDelete);
                mTestcaseListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);

                Toast.makeText(TestcasesActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Click(R.id.addTestcaseBtn)
    protected void handleAddNewTestcase() {
        AddTestcaseActivity_.intent(this)
                .extra(AddTestcaseActivity.EXTRA_PROJECT_ID, mProjectId)
                .start();
    }

    @OnResume
    protected void onResume() {
        super.onResume();

        restClient.getTestcases(mProjectId, new OnRestCallComplete<List<TestCaseResponse>>() {
            @Override
            public void onSuccess(List<TestCaseResponse> responseBody) {
                mTestcasesList.clear();
                mTestcasesList.addAll(responseBody);
                mTestcaseListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
                mTestcasesEmptyView.setText(R.string.testcases_empty_list);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mTestcasesEmptyView.setText(R.string.testcases_empty_list);

                Toast.makeText(TestcasesActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}