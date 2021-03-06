package com.example.admin.sdosandroidcars.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.admin.sdosandroidcars.Drawer;

public class BaseFragment extends Fragment {

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Drawer mydrawer = (Drawer) getActivity();
        getView().setBackgroundColor(mydrawer.activityColor);
        getView().setClickable(true);
    }
}

