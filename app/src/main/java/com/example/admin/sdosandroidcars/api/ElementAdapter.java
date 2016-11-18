package com.example.admin.sdosandroidcars.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.example.admin.sdosandroidcars.api.info.Element;

import java.util.ArrayList;


public class ElementAdapter extends ArrayAdapter<Element> {

    public ElementAdapter(Context context, ArrayList<Element> elements) {
        super(context, 0, elements );
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Element element = getItem(position);


        if (convertView == null) {
            convertView = new CheckBox(getContext());
        }

        final CheckBox checkBox = (CheckBox) convertView;

        checkBox.setWidth(350);
        checkBox.setHeight(150);
        checkBox.setText(element.getName());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                element.setSelected(checkBox.isSelected());
            }
        });

        return  checkBox;
    }
}
