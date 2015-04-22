package com.khmelenko.lab.mester.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.adapter.ProjectsListAdapter;
import com.khmelenko.lab.mester.network.OnRestCallComplete;
import com.khmelenko.lab.mester.network.RestClient;
import com.khmelenko.lab.mester.network.response.ProjectResponse;
import com.khmelenko.lab.mester.network.retrofit.RestClientRetrofit;
import com.melnykov.fab.FloatingActionButton;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.parceler.transfuse.annotations.OnResume;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * Main application activity
 *
 * @author Dmytro Khmelenko
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends BaseActivity {

    @ViewById(R.id.projectsListView)
    ListView mProjectsListView;

    @ViewById(R.id.projectsEmptyView)
    TextView mProjectsEmptyView;

    @ViewById(R.id.addProjectBtn)
    FloatingActionButton mAddProjectBtn;

    @ViewById(R.id.addProjectProgressBar)
    View mProgressBar;

    private ProjectsListAdapter mProjectsListAdapter;
    private List<ProjectResponse> mProjectsList;


   // @Inject
     // RestClient mRestClient;

    @AfterViews
    protected void init() {
       // mRestClient = new RestClientRetrofit();
        mProjectsList = new ArrayList<>();
        mProjectsListAdapter = new ProjectsListAdapter(this, mProjectsList);
        // during loading do not show the empty view text
        mProjectsEmptyView.setText("");
        mProjectsListView.setEmptyView(mProjectsEmptyView);
        mProjectsListView.setAdapter(mProjectsListAdapter);

        mProjectsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectResponse toDelete = mProjectsList.get(position);
                startDeletingProject(toDelete);
                return false;
            }
        });

        mProjectsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProjectResponse selected = mProjectsList.get(position);
                TestcasesActivity_.intent(MainActivity.this)
                        .extra(TestcasesActivity.EXTRA_PROJECT_NAME, selected.getName())
                        .extra(TestcasesActivity.EXTRA_PROJECT_ID, selected.getId()).start();
            }
        });
    }

    /**
     * Initiates deletion project process
     *
     * @param project Project for deletion
     */
    private void startDeletingProject(final ProjectResponse project) {
        String message = getString(R.string.delete_project_msg, project.getName());

        AlertDialog alert = new AlertDialog.Builder(this)
                .setTitle(R.string.delete_project_title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProject(project);
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
     * Deletes selected project
     *
     * @param projectToDelete Project for deletion
     */
    private void deleteProject(final ProjectResponse projectToDelete) {
        mProgressBar.setVisibility(View.VISIBLE);

        mRestClient.deleteProject(projectToDelete.getId(), new OnRestCallComplete() {
            @Override
            public void onSuccess(Object responseBody) {
                mProjectsList.remove(projectToDelete);
                mProjectsListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Click(R.id.addProjectBtn)
    protected void handleAddNewProject() {
        AddProjectActivity_.intent(this).start();
    }

    @OptionsItem(R.id.action_settings)
    protected void handleActionSettings() {
        // TODO
        Toast.makeText(this, "SETTINGS", Toast.LENGTH_LONG).show();
    }

    @OnResume
    protected void onResume() {
        super.onResume();

        mRestClient.getProjects(new OnRestCallComplete<List<ProjectResponse>>() {
            @Override
            public void onSuccess(List<ProjectResponse> responseBody) {
                mProjectsList.clear();
                mProjectsList.addAll(responseBody);
                mProjectsListAdapter.notifyDataSetChanged();

                mProgressBar.setVisibility(View.GONE);
                mProjectsEmptyView.setText(R.string.projects_empty_list);
            }

            @Override
            public void onFail(int errorCode, String message) {
                mProgressBar.setVisibility(View.GONE);
                mProjectsEmptyView.setText(R.string.projects_empty_list);

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
