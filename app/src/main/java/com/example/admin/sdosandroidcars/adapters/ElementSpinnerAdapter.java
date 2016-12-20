package com.example.admin.sdosandroidcars.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.admin.sdosandroidcars.api.info.Element;
import com.example.admin.sdosandroidcars.utils.StringUtils;

import java.util.ArrayList;

public class ElementSpinnerAdapter extends ArrayAdapter<Element> {

    private Context context;
    private int layoutResourceId;

    public ElementSpinnerAdapter(Context context, int layoutResourceId, ArrayList<Element> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View item = convertView;

        if (item == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            item = layoutInflater.inflate(layoutResourceId, parent, false);
        }

        String text = StringUtils.titleCase(getItem(position).getName());
        ((TextView) item).setText(text);

        return item;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
