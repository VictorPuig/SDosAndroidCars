package com.example.admin.sdosandroidcars.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.admin.sdosandroidcars.api.info.Element;

import java.util.ArrayList;


public class FilterElementAdapter extends ArrayAdapter<Element> {

    public FilterElementAdapter(Context context, ArrayList<Element> elements) {
        super(context, 0, elements );
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Element element = getItem(position);


        if (convertView == null) {
            convertView = new CheckBox(getContext());
        }

        CheckBox checkBox = (CheckBox) convertView;

        checkBox.setWidth(350);
        checkBox.setHeight(150);
        checkBox.setChecked(element.isSelected());
        checkBox.setText(element.getName());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkbox = (CheckBox) view;

                String TAG = "Checkbox:" + checkbox.getText();

                Log.d(TAG, "onClick cridat");

                element.setSelected(checkbox.isChecked());
            }
        });

        return  checkBox;
    }
}
