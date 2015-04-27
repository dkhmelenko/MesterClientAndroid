package com.khmelenko.lab.mester.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.network.response.ProjectResponse;
import com.khmelenko.lab.mester.network.response.TestingTestCaseResponse;

import java.util.List;

/**
 * List adapter of the new testing list
 *
 * @author Dmytro Khmelenko
 */
public class NewTestingListAdapter extends ArrayAdapter<TestingTestCaseResponse> {

    private List<TestingTestCaseResponse> mTests;

    /**
     * Constructor
     *
     * @param context Context
     * @param objects List of objects
     */
    public NewTestingListAdapter(Context context, List<TestingTestCaseResponse> objects) {
        super(context, R.layout.new_testing_item, objects);

        mTests = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.new_testing_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) row.findViewById(R.id.newTestingTestName);
            holder.status = (TextView) row.findViewById(R.id.newTestingTestStatus);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        TestingTestCaseResponse test = mTests.get(position);
        holder.name.setText(test.getName());
        holder.status.setText(test.getStatus());

        return row;
    }

    static class ViewHolder {
        TextView name;
        TextView status;
    }
}
