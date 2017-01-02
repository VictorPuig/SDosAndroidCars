package com.example.admin.sdosandroidcars.adapters;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.R;
import com.example.admin.sdosandroidcars.api.cars.Car;
import com.example.admin.sdosandroidcars.utils.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter<Car> {

    private Context context;
    private int layoutResourceId;

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

                Log.d("a ver", "click");

                if (popup != null) {
                    Log.d("a ver", "cancelant");

                    popup.dismiss();
                    popup = null;

                    return;
                }

                final View popUpView = layoutInflater.inflate(R.layout.car_detail, null);

                popup = new PopupWindow(popUpView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);

                ImageView imageViewCar = (ImageView) popUpView.findViewById(R.id.imageViewCar);
                imageViewCar.setImageDrawable(gridViewItem.getDrawable());

                TextView textViewModel = (TextView) popUpView.findViewById(R.id.textViewCarModel);
                textViewModel.setText(StringUtils.titleCase(getItem(position).getName()));

                TextView textViewMaker = (TextView) popUpView.findViewById(R.id.textViewCarMaker);
                textViewMaker.setText(StringUtils.titleCase(getItem(position).getMaker()));

                TextView textViewColor = (TextView) popUpView.findViewById(R.id.textViewCarColor);
                textViewColor.setText(StringUtils.titleCase(getItem(position).getColor()));

                popup.setOutsideTouchable(true);

                popUpView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int x = (p.x / 2) - popUpView.getMeasuredWidth() / 2;
                        int y = (p.y / 2) - popUpView.getMeasuredHeight() / 2 + toolbarHeight / 2;

                        popup.update(x, y, -1, -1);
                    }
                });

                popup.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
            }
        });

        return row;
    }
}
