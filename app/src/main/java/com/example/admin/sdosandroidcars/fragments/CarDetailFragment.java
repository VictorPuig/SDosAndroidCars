package com.example.admin.sdosandroidcars.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.sdosandroidcars.Drawer;
import com.example.admin.sdosandroidcars.R;
import com.example.admin.sdosandroidcars.utils.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CarDetailFragment extends BaseFragment {

    ImageView imageView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        return inflater.inflate(R.layout.car_detail, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("");
        imageView = (ImageView) getView().findViewById(R.id.imageViewCar);

        final Context self = getContext();
        final TextView textViewModel = (TextView) getActivity().findViewById(R.id.textViewCarModel);
        final TextView textViewMaker = (TextView) getActivity().findViewById(R.id.textViewCarMaker);
        final TextView textViewColor = (TextView) getActivity().findViewById(R.id.textViewCarColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (getActivity() != null)
                        Picasso.with(self)
                                .load(getArguments().getString("url"))
                                .error(android.R.drawable.ic_delete)
                                .resize(imageView.getWidth(), 0)
                                .into(imageView, new Callback() {
                                    @Override
                                    public void onSuccess() {

                                        if (getActivity().findViewById(R.id.car_detail_data) != null) {
                                            textViewModel.setText(StringUtils.titleCase(getArguments().getString("model")));

                                            textViewMaker.setText(StringUtils.titleCase(getArguments().getString("maker")));

                                            textViewColor.setText(StringUtils.titleCase(getArguments().getString("color")));

                                            getActivity().findViewById(R.id.car_detail_data).setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                }
            });
        }

        final LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.car_detail_linear_layout);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {

            float startX;
            float linearStartX;

            int halfScreen;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    startX = motionEvent.getX();
                    linearStartX = linearLayout.getX();

                    DisplayMetrics metrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    halfScreen = (int) (metrics.widthPixels * 0.4);

                    return true;

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    if (linearLayout.getX() > halfScreen || linearLayout.getX() + linearLayout.getWidth() < halfScreen)
                        ((Drawer)getActivity()).show();
                        //self.finish();

                    linearLayout.setX(linearStartX + (motionEvent.getX() - startX));

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    linearLayout.setX(linearStartX);
                }

                return true;
            }
        });
    }
}
