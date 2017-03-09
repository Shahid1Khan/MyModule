package com.mymodule.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.mymodule.R;
import com.mymodule.drawersetup.FragmentDrawer;
import com.mymodule.fragments.FragmentGallery;
import com.mymodule.fragments.FragmentHome;
import com.mymodule.fragments.FragmentRecyclerView;
import com.mymodule.models.MyDetail;
import com.mymodule.mysharedprefrence.MyPrefData;
import com.mymodule.utils.InitializeFragment;

public class DashboardActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {


    public static Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private static String TAG = DashboardActivity.class.getSimpleName();

    DrawerLayout drawerLayout;

    public static DashboardActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        activity=this;
        init();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, drawerLayout, mToolbar);
        drawerFragment.setDrawerListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Window w = getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            } catch (Exception e) {
            }
        }


        // display the first navigation drawer view on app launch

        displayView(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.itemsearch:
                Toast.makeText(this, "This is Search Icon", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {


        for (int i = 0; i < DashboardActivity.this.getSupportFragmentManager().getBackStackEntryCount() - 1; i++) {
            DashboardActivity.this.getSupportFragmentManager().popBackStack();
        }

        switch (position) {
            case 0:
                initFragment("add", true, new FragmentHome());
                break;
            case 1:
                initFragment("add", true, new FragmentGallery());
                break;
            case 2:
                initFragment("add", true, new FragmentRecyclerView());
                break;
            case 3:
                new MyPrefData(this).setMyDetails(new MyDetail());
                finish();
                break;

        }

    }

    private void initFragment(String addOrReplace, Boolean addToBackStack, Fragment fragment) {
        new InitializeFragment(DashboardActivity.this, addOrReplace).initFragment(fragment, addToBackStack, getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

}
