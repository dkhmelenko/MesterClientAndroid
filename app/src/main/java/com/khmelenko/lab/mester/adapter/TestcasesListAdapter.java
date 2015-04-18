package com.khmelenko.lab.mester.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.network.response.TestCaseResponse;

import java.util.List;

/**
 * Defines an adapter for the list of testcases
 *
 * @author Dmytro Khmelenko
 */
public class TestcasesListAdapter extends ArrayAdapter<TestCaseResponse> {

    private List<TestCaseResponse> mTestcases;

    /**
     * Constructor
     *
     * @param context Context
     * @param objects List of objects
     */
    public TestcasesListAdapter(Context context, List<TestCaseResponse> objects) {
        super(context, R.layout.testcase_item, objects);

        mTestcases = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.testcase_item, parent, false);

            holder = new ViewHolder();
            holder.title = (TextView) row.findViewById(R.id.testcaseTitle);
            holder.creationDate = (TextView) row.findViewById(R.id.testcaseCreationDate);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        TestCaseResponse testcase = mTestcases.get(position);
        holder.title.setText(testcase.getTitle());
        holder.creationDate.setText(testcase.getCreationDate());

        return row;
    }

    static class ViewHolder {
        TextView title;
        TextView creationDate;
    }
}
