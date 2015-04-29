package com.khmelenko.lab.mester.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.network.response.TestingStepResponse;

import java.util.List;

/**
 * Defines an adapter for the list of steps in test
 *
 * @author Dmytro Khmelenko
 */
public class TestListAdapter extends ArrayAdapter<TestingStepResponse> {

    private List<TestingStepResponse> mSteps;

    /**
     * Constructor
     *
     * @param context Context
     * @param objects List of objects
     */
    public TestListAdapter(Context context, List<TestingStepResponse> objects) {
        super(context, R.layout.test_step_item, objects);

        mSteps = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.test_step_item, parent, false);

            holder = new ViewHolder();
            holder.number = (TextView) row.findViewById(R.id.testStepNumber);
            holder.description = (TextView) row.findViewById(R.id.testStepText);
            holder.status = (CheckBox) row.findViewById(R.id.testStepStatus);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        final TestingStepResponse step = mSteps.get(position);
        holder.number.setText(step.getDescription());
        holder.description.setText(step.getCreationDate());
        holder.status.setChecked(false);
        holder.status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    step.setStatus("passed");
                } else {
                    step.setStatus("failed");
                }
            }
        });

        return row;
    }

    static class ViewHolder {
        TextView number;
        TextView description;
        CheckBox status;
    }
}

