package com.example.admin.sdosandroidcars.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.R;
import com.example.admin.sdosandroidcars.api.cars.Car;
import com.example.admin.sdosandroidcars.fragments.CarDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<Car> {

    private Context context;
    private int layoutResourceId;
    Fragment fragment = null;
    PopupWindow popup = null;
    private final Point p = new Point();
    private final int toolbarHeight;

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList<Car> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getSize(p);

        toolbarHeight = ((Drawer) context).toolbar.getHeight();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        View row = convertView;

        final LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (row == null) {
            row = layoutInflater.inflate(layoutResourceId, parent, false);
        }

        final GridViewItem gridViewItem = (GridViewItem) row.findViewById(R.id.gridImage);

        Picasso.with(context)
                .load(getItem(position).getImgUrl())
                .error(android.R.drawable.ic_delete)
                .fit()
                .centerCrop()
                .into(gridViewItem);

        gridViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("maker", getItem(position).getMaker());
                bundle.putString("model", getItem(position).getName());
                bundle.putString("color", getItem(position).getColor());
                bundle.putString("url", getItem(position).getImgUrl());
                fragment = new CarDetailFragment();
                fragment.setArguments(bundle);

                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = ((Activity)context).getFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }

            }
        });

        return row;
    }
}
