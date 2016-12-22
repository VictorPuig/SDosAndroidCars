package com.example.admin.sdosandroidcars;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.sdosandroidcars.adapters.GridViewAdapter;
import com.example.admin.sdosandroidcars.api.cars.Car;
import com.example.admin.sdosandroidcars.api.cars.Cars;
import com.example.admin.sdosandroidcars.api.cars.FilteredCarsResultListener;
import com.example.admin.sdosandroidcars.api.info.Request;
import com.example.admin.sdosandroidcars.api.login.SessionManager;
import com.example.admin.sdosandroidcars.fragments.AddCarFragment;
import com.example.admin.sdosandroidcars.fragments.FilterFragment;
import com.example.admin.sdosandroidcars.fragments.LoginFragment;
import com.example.admin.sdosandroidcars.api.info.Filter;
import com.example.admin.sdosandroidcars.api.info.Info;
import com.example.admin.sdosandroidcars.api.info.InfoResultListener;
import com.example.admin.sdosandroidcars.fragments.SignupFragment;

import java.util.ArrayList;
import java.util.List;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FilterAvailableListener {

    private static final String TAG = "Drawer";
    public int activityColor = 0;

    public Filter filter;
    public Filter selectedFilter;

    private NavigationView nav;
    private Menu menu;

    private DrawerLayout drawer;
    private GridView gridView;
    public int retryCount = 0;
    public SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_drawer);

        sessionManager = new SessionManager(this);

        Drawable background = getWindow().getDecorView().getBackground();

        activityColor = Color.TRANSPARENT;

        if (background instanceof ColorDrawable)
            activityColor = ((ColorDrawable) background).getColor();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

                public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                    View view = getWindow().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                invalidateOptionsMenu();
            }

        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        gridView = (GridView) findViewById(R.id.gridView);

        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        menu = nav.getMenu();

        // Inicialitza el filter
        getFilter(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else if (!show()) {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.testItem) {
            Intent i = new Intent(this, TestLogin.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    //metode que deselecciona tots els items del menu drawer
    private void uncheckMenuItems() {
        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setChecked(false);
    }

    public void doGetCars () {
        Request carsRequest = new Request(selectedFilter, 0, 20);  //TODO: Limit dinamic

        Log.d(TAG, "Request json:" + carsRequest.getJSONObject().toString());

        Log.d(TAG, "Filter inicials descarregats");
        Cars.doGetCars(carsRequest, new FilteredCarsResultListener() {
            @Override
            public void onCarsResult(ArrayList<Car> cars) {
                Log.d(TAG, "DoGetCars cridat");

                if (cars == null) {
                    Toast.makeText(getApplicationContext(), "No hi han cotxes!", Toast.LENGTH_SHORT).show();
                    ((TextView) findViewById(R.id.statusCarsView)).setText("Error");
                }
                else {
                    findViewById(R.id.statusCarsView).setVisibility(View.GONE);
                    ArrayList<String> urls = new ArrayList<>();
                    for (Car car : cars) {
                        urls.add("http://" + Constants.API_HOST + "/" + car.getImgUrl());
                    }

                    for (String url : urls)
                        Log.d("URLS", "url: " + url);

                    GridViewAdapter gridViewAdapter = new GridViewAdapter(getApplicationContext(), R.layout.grid_item_layout, urls);
                    gridView.setAdapter(gridViewAdapter);
                }
            }
        });
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_home:
                show();
                break;
            case R.id.nav_filter:
                fragment = new FilterFragment();
                break;
            case R.id.nav_addCar:
                if (SessionManager.isLoggedIn()) {
                    fragment = new AddCarFragment();
                }
                else {
                    Toast.makeText(this, "You must logIn to add a new car", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.nav_login:
                if (!SessionManager.isLoggedIn()) {
                    fragment = new LoginFragment();
                    uncheckMenuItems();
                }
                else {
                    Toast.makeText(this, "Already loged in, Try logout first", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.nav_signup:
                if (!SessionManager.isLoggedIn()) {
                    fragment = new SignupFragment();
                    uncheckMenuItems();
                }
                else {
                    Toast.makeText(this, "Already loged in, Try logout first", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.nav_logout:
                SessionManager.logoutUser();
                show();

        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    /*
    Retorna true si ha amagat algun fragment, false ni ho hi habia res per amagar
     */
    public boolean show() {
        Log.d(TAG, "show cridat");

        FragmentManager fm = getSupportFragmentManager();

        boolean fragmentRemoved = false;
        FragmentTransaction ft = fm.beginTransaction();
        List<Fragment> fragments = fm.getFragments();
        if (fragments != null)
            for (Fragment f : fragments) {
                if (f != null) {
                    fragmentRemoved = true;
                    ft.remove(f);
                }
            }
        ft.commit();

        setTitle(R.string.app_name);
        uncheckMenuItems();
        menu.getItem(0).setChecked(true);

        if (!filter.getSelected().equals(selectedFilter) && filter.getSelected()!=null) {
            selectedFilter = filter.getSelected();
            doGetCars();
        }

        return fragmentRemoved;
    }

    public void setFilter(Filter filter) {
        Log.d(TAG, "Filters actualitzats");
        this.filter = filter;

        if (selectedFilter == null) {
            selectedFilter = filter.getSelected();
            doGetCars();
        }
    }

    public void getFilter (boolean force, final FilterAvailableListener filterAvailableListener) {
        Log.d(TAG, ".getFilter() cridat");

        final Drawer self = this;
        //Si el filtre encara no s'ha descarregat
        if (filter == null || filter.isEmpty() || force) {  // True es per que sempre entri #HACKS
            //Cridem al metode static doGetiInfo que ens retorna un filtre
            Info.doGetInfo(new InfoResultListener() {
                @Override
                public void onInfoResult(Filter filter) {
                    //Agafem el filtre retornat i actualitzem el de la clase Drawer (Main)
                    self.setFilter(filter);
                    //Avisem que ja tenim un filtre disponible i el pasem
                    filterAvailableListener.onFilterAvailable(filter);
                    /*if (self != filterAvailableListener)
                        self.onFilterAvailable(filter);*/
                }
            });
        }

        //Si ja tenim el filtre descarregat, avisem i el pasem
        else {
            Log.d(TAG, "Ja tenim els filters descarregats. Cridant listener");
            filterAvailableListener.onFilterAvailable(filter);
            /*if (self != filterAvailableListener)
                self.onFilterAvailable(filter);*/
        }
    }

    public void getFilter(FilterAvailableListener filterAvailableListener) {
        getFilter(false, filterAvailableListener);
    }

    @Override
    public void onFilterAvailable(Filter filter) {
        if (filter.isEmpty() && retryCount <= 2) {
            retryCount++;
            getFilter(this);
        }
        else {
            retryCount = 0;
        }
    }
}
