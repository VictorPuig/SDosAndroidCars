package com.example.admin.sdosandroidcars;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Admin on 14/11/2016.
 */

public class AddCar extends Fragment{
    View myView;

    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.content_fragment,container,false);
        return myView;
    }
}
