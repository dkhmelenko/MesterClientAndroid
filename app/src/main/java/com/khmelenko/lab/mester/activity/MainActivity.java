package com.khmelenko.lab.mester.activity;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
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
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application activity
 *
 * @author Dmytro Khmelenko
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu_main)
public class MainActivity extends ActionBarActivity {

    @ViewById
    protected ListView projectsListView;

    private ProjectsListAdapter mProjectsListAdapter;
    private List<ProjectResponse> mProjectsList;

    @ViewById
    protected TextView projectsEmptyView;

    @ViewById
    protected FloatingActionButton actionButtonAdd;

    @ViewById
    protected View progressBar;

    @AfterViews
    protected void init() {
        mProjectsList = new ArrayList<>();
        mProjectsListAdapter = new ProjectsListAdapter(this, mProjectsList);
        // during loading do not show the empty view text
        projectsEmptyView.setText("");
        projectsListView.setEmptyView(projectsEmptyView);
        projectsListView.setAdapter(mProjectsListAdapter);

        actionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Toast.makeText(MainActivity.this, "ActionButton", Toast.LENGTH_SHORT).show();
            }
        });

        RestClient restClient = new RestClientRetrofit();
        restClient.getProjects(new OnRestCallComplete<List<ProjectResponse>>() {
            @Override
            public void onSuccess(List<ProjectResponse> responseBody) {
                mProjectsList.clear();
                mProjectsList.addAll(responseBody);
                mProjectsListAdapter.notifyDataSetChanged();

                progressBar.setVisibility(View.GONE);
                projectsEmptyView.setText(R.string.projects_empty_list);
            }

            @Override
            public void onFail(int errorCode, String message) {
                progressBar.setVisibility(View.GONE);
                projectsEmptyView.setText(R.string.projects_empty_list);
            }
        });
    }

    @OptionsItem(R.id.action_settings)
    protected void handleActionSettings() {
        Toast.makeText(this, "SETTINGS", Toast.LENGTH_LONG).show();
    }


}
