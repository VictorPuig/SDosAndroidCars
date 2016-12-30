package com.example.admin.sdosandroidcars;


// https://guides.codepath.com/android/Endless-Scrolling-with-AdapterViews-and-RecyclerView

import android.util.Log;
import android.widget.AbsListView;

import com.example.admin.sdosandroidcars.api.cars.Car;
import com.example.admin.sdosandroidcars.api.cars.FilteredCarsResultListener;

import java.util.ArrayList;

public class EndlessScrollListener implements AbsListView.OnScrollListener {
    final String TAG = "EndlessScrollListener";

    private final Drawer drawer;

    int visibleItemsThreshold = 2;

    int maxVisibleItems = 8;

    boolean reachedEnd = false;

    boolean isLoading = false;

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    public EndlessScrollListener(Drawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //Log.v(TAG, "onScroll");
        // Si estem esperant a que tornin les dades del servidor no fem res.
        // Si ja no hi ha mes cotxes no fem res.
        if (isLoading || reachedEnd) return;

        Log.d(TAG, "onScroll Check");

        int lastVisibleItem = firstVisibleItem + visibleItemCount;

        // Si l'ultim element visible mes el coixi d'elements no visibles es pasa del nombre total d'elements,
        // hem de carregar de nous.
        if (lastVisibleItem + visibleItemsThreshold >= totalItemCount) {
            Log.d(TAG, "Loading " + (maxVisibleItems - visibleItemCount + visibleItemsThreshold) + " cars");
            isLoading = true;
            drawer.addCarsToGridView(maxVisibleItems - visibleItemCount + visibleItemsThreshold, new FilteredCarsResultListener() {
                @Override
                public void onCarsResult(ArrayList<Car> cars) {
                    Log.d(TAG, "Done loading");

                    if (cars == null) {
                        Log.d(TAG, "Loading error");
                        return;
                    }

                    isLoading = false;
                    if (cars.size() == 0) reachedEnd = true;

                    Log.d(TAG, "EndReached:" + reachedEnd);
                }
            });
        }
    }
}
