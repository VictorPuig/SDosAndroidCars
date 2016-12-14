package com.example.admin.sdosandroidcars.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.R;

/**
 * Created by Admin on 16/11/2016.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_login, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Login");

        Button btnCancel = (Button) getView().findViewById(R.id.buttonLoginCancel);
        Button btnOk = (Button) getView().findViewById(R.id.buttonLoginOk);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);

        Drawer mydrawer = (Drawer)getActivity();
        getView().setBackgroundColor(mydrawer.activityColor);
        getView().setClickable(true);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.buttonLoginCancel:
                ((Drawer) getActivity()).show();
                break;
        }
    }
}
