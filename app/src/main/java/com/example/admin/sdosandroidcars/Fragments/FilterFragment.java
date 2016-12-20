package com.example.admin.sdosandroidcars.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.FilterAvailableListener;
import com.example.admin.sdosandroidcars.R;
import com.example.admin.sdosandroidcars.adapters.FilterElementAdapter;
import com.example.admin.sdosandroidcars.api.info.Filter;

public class FilterFragment extends BaseFragment {

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

        ((Drawer) getActivity()).getFilter(new FilterAvailableListener() {
            @Override
            public void onFilterAvailable(Filter filter) {
                try {
                    if (filter.isEmpty()) {
                        Toast.makeText(getContext(), "No hi ha filter!", Toast.LENGTH_SHORT).show();
                        ((TextView) getView().findViewById(R.id.statusTextView)).setText("Error");

                    } else {
                        ((View) getView().findViewById(R.id.loadingLayout)).setVisibility(View.GONE);
                        ((View) getView().findViewById(R.id.dataLayoutParent)).setVisibility(View.VISIBLE);

                        FilterElementAdapter makerAdapter = new FilterElementAdapter(getContext(), filter.getMakers());

                        ListView makerListView = (ListView) getView().findViewById(R.id.makers_list);
                        makerListView.setAdapter(makerAdapter);

                        FilterElementAdapter colorAdapter = new FilterElementAdapter(getContext(), filter.getColors());

                        ListView colorListView = (ListView) getView().findViewById(R.id.colors_list);
                        colorListView.setAdapter(colorAdapter);
                    }

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
