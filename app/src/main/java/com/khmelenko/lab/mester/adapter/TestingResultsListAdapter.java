package com.khmelenko.lab.mester.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.network.response.TestingResponse;

import java.util.List;

/**
 * Adapter for the list of testing results
 *
 * @author Dmytro Khmelenko
 */
public class TestingResultsListAdapter extends ArrayAdapter<TestingResponse> {

    private List<TestingResponse> mTests;

    /**
     * Constructor
     *
     * @param context Context
     * @param objects List of objects
     */
    public TestingResultsListAdapter(Context context, List<TestingResponse> objects) {
        super(context, R.layout.testing_results_item, objects);

        mTests = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.testing_results_item, parent, false);

            holder = new ViewHolder();
            holder.id = (TextView) row.findViewById(R.id.testingResultId);
            holder.creationDate = (TextView) row.findViewById(R.id.testingResultCreationDate);
            holder.status = (TextView) row.findViewById(R.id.testingResultStatus);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        TestingResponse test = mTests.get(position);
        holder.id.setText(test.getId());
        holder.creationDate.setText(test.getCreationDate());
        holder.status.setText(test.getStatus());

        return row;
    }

    static class ViewHolder {
        TextView id;
        TextView creationDate;
        TextView status;
    }

}
