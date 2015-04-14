package com.khmelenko.lab.mester.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.network.response.ProjectResponse;

import java.util.List;

/**
 * Defines an adapter for the projects list
 *
 * @author Dmytro Khmelenko
 */
public class ProjectsListAdapter extends ArrayAdapter<ProjectResponse> {

    private List<ProjectResponse> mProjects;

    /**
     * Constructor
     *
     * @param context Context
     * @param objects List of objects
     */
    public ProjectsListAdapter(Context context, List<ProjectResponse> objects) {
        super(context, R.layout.project_item, objects);

        mProjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.project_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) row.findViewById(R.id.projectName);
            holder.creationDate = (TextView) row.findViewById(R.id.projectCreationDate);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ProjectResponse project = mProjects.get(position);
        holder.name.setText(project.getName());
        holder.creationDate.setText(project.getCreationDate());

        return row;
    }

    static class ViewHolder {
        TextView name;
        TextView creationDate;
    }
}
