package com.khmelenko.lab.mester.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.model.TestStatus;
import com.khmelenko.lab.mester.network.response.TestingResponse;
import com.khmelenko.lab.mester.network.response.TestingTestCaseResponse;

import java.util.List;

/**
 * List adapter for the testing details
 *
 * @author Dmytro Khmelenko
 */
public class TestingDetailsListAdapter extends ArrayAdapter<TestingTestCaseResponse> {

    private List<TestingTestCaseResponse> mTests;

    /**
     * Constructor
     *
     * @param context Context
     * @param objects List of objects
     */
    public TestingDetailsListAdapter(Context context, List<TestingTestCaseResponse> objects) {
        super(context, R.layout.testing_details_item, objects);

        mTests = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.testing_details_item, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) row.findViewById(R.id.testingDetailsTestName);
            holder.status = (TextView) row.findViewById(R.id.testingDetailsTestStatus);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        TestingTestCaseResponse test = mTests.get(position);
        holder.name.setText(test.getName());
        holder.status.setText(test.getStatus());
        int colorId = getStatusColor(test.getStatus());
        holder.status.setTextColor(colorId);

        return row;
    }

    /**
     * Gets color for the status
     *
     * @param status Status
     * @return Color ID
     */
    private int getStatusColor(String status) {
        int colorId = 0;
        if (status.equals(TestStatus.DEFAULT.getName())) {
            colorId = getContext().getResources().getColor(R.color.status_default);
        } else if (status.equals(TestStatus.PASSED.getName())) {
            colorId = getContext().getResources().getColor(R.color.status_passed);
        } else if (status.equals(TestStatus.FAILED.getName())) {
            colorId = getContext().getResources().getColor(R.color.status_failed);
        }
        return colorId;
    }

    static class ViewHolder {
        TextView name;
        TextView status;
    }
}