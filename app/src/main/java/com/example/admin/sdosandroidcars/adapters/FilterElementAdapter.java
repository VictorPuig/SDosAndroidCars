package com.example.admin.sdosandroidcars.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.sdosandroidcars.R;
import com.example.admin.sdosandroidcars.api.info.Element;
import com.example.admin.sdosandroidcars.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;


public class FilterElementAdapter extends BaseExpandableListAdapter {

    private Context context;
    private HashMap<String, ArrayList<Element>> elements;

    public FilterElementAdapter(Context context, HashMap<String, ArrayList<Element>> elements) {
        this.context = context;
        this.elements = elements;
    }

    @Override
    public int getGroupCount() {
        return this.elements.size();
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.elements.get(getGroup(listPosition)).size();
    }

    @Override
    public String getGroup(int listPosition) {
        return (String) this.elements.keySet().toArray()[listPosition];
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.elements.get(getGroup(listPosition)).get(expandedListPosition);
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String name = StringUtils.titleCase(getGroup(groupPosition));

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.filter_list_group, parent);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(name);
        return convertView;
    }

    @Override
    public View getChildView(int listPosition, int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d("FILTERADAPTER", "getChildView");
        final Element element = (Element) getChild(listPosition, expandedListPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                  .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.filter_list_item, parent);
        }

        //CheckBox checkBox = (CheckBox) convertView;
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkboxFilter);
        //checkBox.setText(this.elements.get(listPosition).getName());

        checkBox.setChecked(element.isSelected());
        checkBox.setText(StringUtils.titleCase(element.getName()));
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkbox = (CheckBox) view;

                String TAG = "Checkbox:" + checkbox.getText();

                Log.d(TAG, "onClick cridat");

                element.setSelected(checkbox.isChecked());
            }
        });


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
