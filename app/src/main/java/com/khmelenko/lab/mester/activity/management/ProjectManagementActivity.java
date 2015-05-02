package com.khmelenko.lab.mester.activity.management;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.activity.BaseActivity;
import com.khmelenko.lab.mester.activity.testing.TestingResultsActivity;
import com.khmelenko.lab.mester.activity.testing.TestingResultsActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

/**
 * Provides possibilities for managing project
 *
 * @author Dmytro Khmelenko
 */
@EActivity(R.layout.activity_project_management)
public class ProjectManagementActivity extends BaseActivity {

    public static final String EXTRA_PROJECT_NAME = "ProjectName";
    public static final String EXTRA_PROJECT_ID = "ProjectId";

    @Extra(EXTRA_PROJECT_NAME)
    String mProjectName;

    @Extra(EXTRA_PROJECT_ID)
    String mProjectId;

    @Click(R.id.projectManagementTestcasesBtn)
    protected void handleTestcasesSelection() {
        TestcasesActivity_.intent(this)
                .extra(TestcasesActivity.EXTRA_PROJECT_NAME, mProjectName)
                .extra(TestcasesActivity.EXTRA_PROJECT_ID, mProjectId).start();
    }

    @Click(R.id.projectManagementTestingBtn)
    protected void handleTestingSelection() {
        TestingResultsActivity_.intent(this)
                .extra(TestingResultsActivity.EXTRA_PROJECT_NAME, mProjectName)
                .extra(TestingResultsActivity.EXTRA_PROJECT_ID, mProjectId).start();
    }

}