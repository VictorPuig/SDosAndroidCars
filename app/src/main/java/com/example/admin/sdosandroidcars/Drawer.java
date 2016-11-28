package com.example.admin.sdosandroidcars;

import android.os.Bundle;
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

import com.example.admin.sdosandroidcars.api.info.Filter;
import com.example.admin.sdosandroidcars.api.info.Info;
import com.example.admin.sdosandroidcars.api.info.InfoResultListener;

import java.util.List;

public class Drawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Drawer";
    public Filter filter;
    private NavigationView nav;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        nav = (NavigationView) findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        menu = nav.getMenu();

        // Inicialitza el filter
        getFilter(new FilterAvailableListener() {
            @Override
            public void onFilterAvailable(Filter filter) {
                //:)
                Log.d(TAG, "Filter inicials descarregats");
            }
        });
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

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
                fragment = new AddCarFragment();
                break;
            case R.id.nav_login:
                fragment = new LoginFragment();
                uncheckMenuItems();
                break;
            case R.id.nav_logout:
                fragment = new LogoutFragment();
                uncheckMenuItems();
                break;
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

        return fragmentRemoved;
    }

    public void setFilter(Filter filter) {
        Log.d(TAG, "Filters actualitzats");
        this.filter = filter;
    }

    public void getFilter (final FilterAvailableListener filterAvailableListener) {
        Log.d(TAG, ".getFilter() cridat");

        final Drawer self = this;

        if (filter == null || filter.isEmpty()) {
            Info.doGetInfo(new InfoResultListener() {
                @Override
                public void onInfoResult(Filter filter) {
                    self.setFilter(filter);
                    filterAvailableListener.onFilterAvailable(filter);
                }
            });
        }

        else {
            Log.d(TAG, "Ja tenim els filters descarregats. Cridant listener");
            filterAvailableListener.onFilterAvailable(filter);
        }
    }
}
