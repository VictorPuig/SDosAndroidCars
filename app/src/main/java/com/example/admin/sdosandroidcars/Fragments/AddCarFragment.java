package com.example.admin.sdosandroidcars.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.R;

/**
 * Created by Admin on 16/11/2016.
 */

public class AddCarFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.frag_addcar, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("AddCar");

        Button btnCancel = (Button) getView().findViewById(R.id.buttonAddCarsCancel);
        Button btnOk = (Button) getView().findViewById(R.id.buttonAddCarsOk);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.buttonAddCarsCancel:
                ((Drawer) getActivity()).show();
                break;
        }
    }
}
