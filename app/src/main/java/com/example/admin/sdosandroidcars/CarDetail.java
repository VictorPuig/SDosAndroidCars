package com.example.admin.sdosandroidcars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.sdosandroidcars.utils.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CarDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_detail);

        final CarDetail self = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        final Intent intent = getIntent();

        final ImageView imageView = (ImageView) findViewById(R.id.imageViewCar);
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Picasso.with(self)
                        .load(intent.getStringExtra("url"))
                        .error(android.R.drawable.ic_delete)
                        .resize(imageView.getWidth(), 0)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                TextView textViewModel = (TextView) findViewById(R.id.textViewCarModel);
                                textViewModel.setText(StringUtils.titleCase(intent.getStringExtra("model")));

                                TextView textViewMaker = (TextView) findViewById(R.id.textViewCarMaker);
                                textViewMaker.setText(StringUtils.titleCase(intent.getStringExtra("maker")));

                                TextView textViewColor = (TextView) findViewById(R.id.textViewCarColor);
                                textViewColor.setText(StringUtils.titleCase(intent.getStringExtra("color")));

                                findViewById(R.id.car_detail_data).setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
        });

    final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.car_detail_linear_layout);
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
                self.getWindowManager().getDefaultDisplay().getMetrics(metrics);
                halfScreen = metrics.widthPixels / 2;

                return true;

            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                if (linearLayout.getX() > halfScreen || linearLayout.getX() + linearLayout.getWidth() < halfScreen)
                    self.finish();

                linearLayout.setX(linearStartX + (motionEvent.getX() - startX));

            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                linearLayout.setX(linearStartX);
            }

            return true;
        }
    });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        else
            return super.onOptionsItemSelected(item);
    }
}
