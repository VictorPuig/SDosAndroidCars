package com.example.admin.sdosandroidcars.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.admin.sdosandroidcars.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<String> {

    private Context context;
    private int layoutResourceId;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<String> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(layoutResourceId, parent, false);
        }

        GridViewItem gridViewItem = (GridViewItem) row.findViewById(R.id.gridImage);

        Picasso.with(context)
                .load(getItem(position))
                .error(android.R.drawable.ic_delete)
                .fit()
                .centerCrop()
                .into(gridViewItem);

        return row;
    }
}
