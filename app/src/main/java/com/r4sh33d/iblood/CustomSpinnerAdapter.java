package com.r4sh33d.iblood;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by codedentwickler on 10/12/17.
 */

/**
 * Convenience Class to create ArrayAdapters for Spinners with the first
 * item disabled ( so that it serves as an hint )
 *
 * @param <T>
 */

public class CustomSpinnerAdapter<T> extends ArrayAdapter<T> {

    private List<T> data;

    public CustomSpinnerAdapter(@NonNull Context context, @LayoutRes int resource,
                                @NonNull List<T> objects) {
        super(context, resource, objects);
        this.data = objects;
    }

    @Override
    public boolean isEnabled(int position) {
        return position != 0 && super.isEnabled(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }

    @Nullable
    @Override
    public T getItem(int position) {
        return data.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView tv = (TextView) view;
        if (position == 0) {
            tv.setTextColor(Color.GRAY);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return view;
    }

    public List<T> getData() {
        return data;
    }

    public void replaceData(List<T> data, T hint) {
        this.data.clear();
        this.data.add(hint);
        this.data.addAll(data);
    }

}
