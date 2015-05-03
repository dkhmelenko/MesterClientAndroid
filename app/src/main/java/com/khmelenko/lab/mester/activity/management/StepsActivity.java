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
import com.khmelenko.lab.mester.adapter.StepsListAdapter;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.response.StepResponse;
import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.parceler.transfuse.annotations.OnResume;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_steps)
public class StepsActivity extends BaseActivity {

    public static final String EXTRA_TESTCASE_TITLE = "TestcaseTitle";
    public static final String EXTRA_TESTCASE_ID = "TestcaseId";

    @Extra(EXTRA_TESTCASE_TITLE)
    String mTestcaseTitle;

    @Extra(EXTRA_TESTCASE_ID)
    String mTestcaseId;

    @ViewById(R.id.stepsTestcaseTitle)
    TextView mStepsTestcaseTitle;

    @ViewById(R.id.stepsListView)
    ListView mStepsListView;

    @ViewById(R.id.stepsEmptyView)
    TextView mStepsEmptyView;

    @ViewById(R.id.addStepBtn)
    FloatingActionButton mAddStepBtn;

    @ViewById(R.id.stepsProgressBar)
    View mProgressBar;

    private StepsListAdapter mStepsListAdapter;
    private List<StepResponse> mStepsList;

    @AfterViews
    protected void init() {
        String testcaseTitle = getString(R.string.steps_testcase_title, mTestcaseTitle);
        mStepsTestcaseTitle.setText(testcaseTitle);

        mStepsList = new ArrayList<>();
        mStepsListAdapter = new StepsListAdapter(this, mStepsList);
        // during loading do not show the empty view text
        mStepsEmptyView.setText("");
        mStepsListView.setEmptyView(mStepsEmptyView);
        mStepsListView.setAdapter(mStepsListAdapter);

        mStepsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                StepResponse toDelete = mStepsList.get(position);
                startDeletingStep(toDelete);
                return true;
            }
        });
    }

    /**
     * Initiates deletion step process
     *
     * @param step Step for deletion
     */
    private void startDeletingStep(final StepResponse step) {
        String message = getString(R.string.delete_step_msg, String.valueOf(step.getNumber()));

        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.delete_step_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteStep(step);
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
     * Deletes selected step
     *
     * @param stepToDelete Step for deletion
     */
    private void deleteStep(final StepResponse stepToDelete) {
        mProgressBar.setVisibility(View.VISIBLE);

        mRestClient.deleteStep(stepToDelete.getId(), new OnRestCallComplete() {
            @Override
            public void onSuccess(Object responseBody) {
                mStepsList.remove(stepToDelete);
                mStepsListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);

                Toast.makeText(StepsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Click(R.id.addStepBtn)
    protected void handleAddNewStep() {
        AddStepActivity_.intent(this)
                .extra(AddStepActivity.EXTRA_TESTCASE_ID, mTestcaseId)
                .start();
    }

    @OnResume
    protected void onResume() {
        super.onResume();

        mRestClient.getTestcaseSteps(mTestcaseId, new OnRestCallComplete<List<StepResponse>>() {
            @Override
            public void onSuccess(List<StepResponse> responseBody) {
                mStepsList.clear();
                mStepsList.addAll(responseBody);
                mStepsListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
                mStepsEmptyView.setText(R.string.steps_empty_list);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mStepsEmptyView.setText(R.string.steps_empty_list);

                Toast.makeText(StepsActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
