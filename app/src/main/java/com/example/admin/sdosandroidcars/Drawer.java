package com.example.admin.sdosandroidcars;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import com.example.admin.sdosandroidcars.api.info.Filter;
import com.example.admin.sdosandroidcars.api.info.Info;
import com.example.admin.sdosandroidcars.api.info.InfoResultListener;
import com.example.admin.sdosandroidcars.api.info.Request;
import com.example.admin.sdosandroidcars.api.login.SessionManager;
import com.example.admin.sdosandroidcars.fragments.AddCarFragment;
import com.example.admin.sdosandroidcars.fragments.FilterFragment;
import com.example.admin.sdosandroidcars.fragments.LoginFragment;
import com.example.admin.sdosandroidcars.fragments.SignupFragment;

import java.util.ArrayList;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FilterAvailableListener {

    private static final String TAG = "Drawer";
    public int activityColor = 0;

    public Filter filter;
    public Filter selectedFilter;

    public NavigationView nav;
    private Menu menu;

    private DrawerLayout drawer;

    private GridView gridView;
    GridViewAdapter gridViewAdapter;

    private TextView userView;

    public SessionManager sessionManager;

    public Toolbar toolbar;

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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        userView = (TextView) nav.getHeaderView(0).findViewById(R.id.userInfo);
        setUserTextView();
        // Inicialitza el filter
        getFilter(this);
    }

    public void setUserTextView () {
        if (sessionManager.isLoggedIn())
            this.userView.setText(sessionManager.getuserName());
        else
            this.userView.setText("");
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
        if (id == R.id.reload) {
            Log.d(TAG, "Reload option click");

            final Toast t = Toast.makeText(this, "Reloading...", Toast.LENGTH_SHORT);
            t.show();

            final Drawer self = this;

            final Fragment currentFragment = getFragmentManager().findFragmentById(R.id.content_frame);
            if (currentFragment instanceof FilterFragment) {
                getFilter(true, new FilterAvailableListener() {
                    @Override
                    public void onFilterAvailable(Filter filter) {
                        t.cancel();
                        Toast.makeText(self, "Done.", Toast.LENGTH_SHORT).show();

                        //REPINTA EL FRAGMENT

                        Log.d(TAG, "Repintant fragment actual");
                        FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
                        fragTransaction.detach(currentFragment);
                        fragTransaction.attach(currentFragment);
                        fragTransaction.commit();
                        }
                });
            } else if (currentFragment == null) {
                initGridView();
            }

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
                setUserTextView();
                if (filter != null) {
                    if (SessionManager.isLoggedIn())
                        Toast.makeText(this, "You are already signed in", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "Logouted", Toast.LENGTH_SHORT).show();
                    show();
                }
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment previous = getFragmentManager().findFragmentById(R.id.content_frame);
            if (previous != null)
                ft.remove(previous);
            ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
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

        boolean fragmentRemoved = false;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame);

        if (fragment != null) {
            fragmentRemoved = true;
            ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
            ft.remove(fragment);
            ft.commit();
        }

        setTitle(R.string.app_name);
        uncheckMenuItems();
        menu.getItem(0).setChecked(true);

        if (!filter.getSelected().equals(selectedFilter)) {
            selectedFilter = filter.getSelected();
            initGridView();
        }

        return fragmentRemoved;

        /*FragmentManager fm = getSupportFragmentManager();


        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        List<android.support.v4.app.Fragment> fragments = fm.getFragments();
        if (fragments != null)
            for (android.support.v4.app.Fragment f : fragments) {
                if (f != null) {
                    fragmentRemoved = true;
                    ft.remove(f);
                }
            }
        ft.commit();

        setTitle(R.string.app_name);
        uncheckMenuItems();
        menu.getItem(0).setChecked(true);

        if (!filter.getSelected().equals(selectedFilter)) {
            selectedFilter = filter.getSelected();
            initGridView();
        }

        return fragmentRemoved;*/
    }

    public void addCarsToGridView(int nCars, final FilteredCarsResultListener l) {
        Log.d(TAG, "adding " + nCars + " to gridview");

        if (filter == null)
            return;

        int offset = gridViewAdapter.getCount();

        Request carsRequest = new Request(selectedFilter, offset, nCars);

        Log.d(TAG, "Request json:" + carsRequest.getJSONObject().toString());

        Cars.doGetCars(carsRequest, new FilteredCarsResultListener() {
            @Override
            public void onCarsResult(ArrayList<Car> cars) {
                if (cars == null) {
                    Toast.makeText(getApplicationContext(), "No hi han cotxes!", Toast.LENGTH_SHORT).show();
                    ((TextView) findViewById(R.id.statusCarsView)).setText(R.string.error);
                }
                else {
                    findViewById(R.id.statusCarsView).setVisibility(View.GONE);

                    for (Car car : cars) {
                        gridViewAdapter.add(car);
                    }
                }

                l.onCarsResult(cars);
            }
        });
    }

    public void initGridView() {
        Log.d(TAG, "initGridView cridat");

        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, new ArrayList<Car>());
        gridView.setAdapter(gridViewAdapter);
        gridView.setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS);  // No se si cal pero no fa mal.
        gridView.setOnScrollListener(new EndlessScrollListener(this));
    }

    public void setFilter(Filter filter) {
        Log.d(TAG, "Filters actualitzats");
        this.filter = filter;

        if (selectedFilter == null) {
            selectedFilter = filter.getSelected();
            initGridView();
        }
    }

    public void getFilter (final boolean force, final FilterAvailableListener filterAvailableListener) {
        Log.d(TAG, ".getFilter(force=" + force + ") cridat");

        final Drawer self = this;
        //Si el filtre encara no s'ha descarregat, es buit o forcem la descarrega
        if (filter == null || filter.isEmpty() || force) {
            Log.d(TAG, "Descarregant filters");

            //Cridem al metode static doGetiInfo que ens retorna un filtre
            Info.doGetInfo(new InfoResultListener() {
                @Override
                public void onInfoResult(Filter filter) {
                    //Agafem el filtre retornat i actualitzem el de la clase Drawer (Main)
                    if (self.filter!=null) {
                        Log.d(TAG,"reloading");
                        filter.setSelectedFromFilter(self.filter);
                    }

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

    }
}
