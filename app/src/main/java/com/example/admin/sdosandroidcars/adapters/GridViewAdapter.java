package com.example.admin.sdosandroidcars.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.sdosandroidcars.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Admin on 07/12/2016.
 */

public class GridViewAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResourceId;
    private ArrayList data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            row = layoutInflater.inflate(layoutResourceId, parent, false);
        }

        ImageView imageView = (ImageView) row.findViewById(R.id.gridImage);

        Picasso.with(context)
                .load((String)getItem(position)).into(imageView);


        return row;
    }
}
