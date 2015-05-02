package com.khmelenko.lab.mester.activity.management;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.activity.BaseActivity;
import com.khmelenko.lab.mester.adapter.TestcasesListAdapter;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;
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
public class TestcasesActivity extends BaseActivity {

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

    @AfterViews
    protected void init() {
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

        mTestcasesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TestCaseResponse selected = mTestcasesList.get(position);
                StepsActivity_.intent(TestcasesActivity.this)
                        .extra(StepsActivity.EXTRA_TESTCASE_TITLE, selected.getTitle())
                        .extra(StepsActivity.EXTRA_TESTCASE_ID, selected.getId()).start();
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

        mRestClient.deleteTestcase(testcaseToDelete.getId(), new OnRestCallComplete() {
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

        mRestClient.getTestcases(mProjectId, new OnRestCallComplete<List<TestCaseResponse>>() {
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
