package com.donnekt.attendanceapp.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.classroom.ClassroomActivity;
import com.donnekt.attendanceapp.department.DepartmentActivity;
import com.google.android.material.navigation.NavigationView;

public class AdminDashLMenus extends AppCompatActivity {

    // Variables
    SharedPreferences pref;
    Intent intent;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Making sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        //TextView result = (TextView) findViewById(R.id.tvLoggedInUser);
        pref = getSharedPreferences("admin_details", MODE_PRIVATE);
        intent = new Intent(AdminDashLMenus.this, MainActivity.class);

        // Lookup navigation view
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        // Inflate the header view at runtime
        View headerLayout1 = navigationView.inflateHeaderView(R.layout.nav_header);
        //ImageView ivHeaderPhoto = headerLayout.findViewById(R.id.imageView);


        // Getting references to the header:
        if (navigationView.getHeaderCount() > 0) {
            View headerLayout = navigationView.getHeaderView(0);
        }


        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;

            case R.id.add_classroom:
                Toast.makeText(this, "Okay on now Click, Okay on now Click!", Toast.LENGTH_SHORT).show();
                return true;
        }

        // Allowing ActionBarToggle() to handle events
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        Menu menu = navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.add_classroom);
        View actionView = MenuItemCompat.getActionView(menuItem);
        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "You Ok Create a new fragment and specify the fragment to ", Toast.LENGTH_SHORT).show();
            }
        });

    }


    public void selectDrawerItem(MenuItem menuItem) {

        // Create a new fragment and specify the fragment to show based on nav item clicked
        Class ourClass;

        switch (menuItem.getItemId()) {
            case R.id.add_department:
                ourClass = DepartmentActivity.class;
                break;

            case R.id.view_departs:
                ourClass = DepartmentActivity.class;
                break;

            case R.id.add_classroom:
                ourClass = ClassroomActivity.class;
                break;

            default:
                ourClass = AdminDashLMenus.class;
        }

        try {
            // Go to some activity!
            Toast.makeText(this, "You add depart !", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(AdminDashLMenus.this, ourClass));

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Insert the XClass by replacing any existing XClass
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout container = (LinearLayout) findViewById(R.id.content_frame);
        inflater.inflate(R.layout.activity_admin_dashboard, container);


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();

    }


}