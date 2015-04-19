package com.khmelenko.lab.mester.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.khmelenko.lab.mester.R;
import com.khmelenko.lab.mester.network.response.StepResponse;

import java.util.List;

/**
 * Defines an adapter for the list of steps
 *
 * @author Dmytro Khmelenko
 */
public class StepsListAdapter extends ArrayAdapter<StepResponse> {

    private List<StepResponse> mSteps;

    /**
     * Constructor
     *
     * @param context Context
     * @param objects List of steps
     */
    public StepsListAdapter(Context context, List<StepResponse> objects) {
        super(context, R.layout.step_item, objects);

        mSteps = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.step_item, parent, false);

            holder = new ViewHolder();
            holder.number = (TextView) row.findViewById(R.id.stepNumber);
            holder.text = (TextView) row.findViewById(R.id.stepText);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        StepResponse step = mSteps.get(position);
        holder.number.setText(String.valueOf(step.getNumber()));
        holder.text.setText(step.getText());

        return row;
    }

    static class ViewHolder {
        TextView number;
        TextView text;
    }
}

