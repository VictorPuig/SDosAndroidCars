package com.example.admin.sdosandroidcars.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.FilterAvailableListener;
import com.example.admin.sdosandroidcars.R;
import com.example.admin.sdosandroidcars.adapters.FilterElementAdapter;
import com.example.admin.sdosandroidcars.api.info.Element;
import com.example.admin.sdosandroidcars.api.info.Filter;

import java.util.ArrayList;
import java.util.HashMap;

public class FilterFragment extends BaseFragment implements FilterAvailableListener {
    Filter filter;

    private static final String TAG = "Filter";

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_filter, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Filter");

        ((Drawer) getActivity()).getFilter(this);
    }

    public void setFilter (Filter filter) {
        this.filter = filter;
    }

    @Override
    public void onFilterAvailable(Filter filter) {
        try {
            if (filter.isEmpty()) {
                Toast.makeText(getContext(), "No hi ha filter!", Toast.LENGTH_SHORT).show();
                ((TextView) getView().findViewById(R.id.statusTextView)).setText(R.string.error);

            } else {
                (getView().findViewById(R.id.loadingLayout)).setVisibility(View.GONE);
                (getView().findViewById(R.id.dataLayoutParent)).setVisibility(View.VISIBLE);

                HashMap<String, ArrayList<Element>> filterList = new HashMap<>();
                filterList.put("makers",filter.getMakers());
                filterList.put("colors",filter.getColors());

                FilterElementAdapter expendableListAdapter = new FilterElementAdapter(getContext(),filterList);
                ExpandableListView expandableListView = (ExpandableListView) getView().findViewById(R.id.expandable_list);
                expandableListView.setAdapter(expendableListAdapter);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
