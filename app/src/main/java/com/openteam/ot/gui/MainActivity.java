package com.openteam.ot.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.openteam.ot.R;
import com.openteam.ot.gui.fragment.AbstractFragment;
import com.openteam.ot.gui.fragment.CampaignListFragment;
import com.openteam.ot.gui.fragment.MyCampaignListFragment;
import com.openteam.ot.gui.fragment.ProfileFragment;

public class MainActivity extends AbstractActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private NavigationView navigationView;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        //remove default title
        getSupportActionBar().setTitle("");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        centerLogoInDrawerHeader();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_campaigns_list) {
            replaceFragment(new CampaignListFragment());
        } else if (id == R.id.nav_my_campaigns) {
            replaceFragment(new MyCampaignListFragment());
        } else if (id == R.id.nav_profile) {
            replaceFragment(new ProfileFragment());
        } else if (id == R.id.nav_collaborate) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected int getReplaceableFragmentContainerId() {
        return R.id.content_main;
    }

    public void replaceFragment(final AbstractFragment fragment){
        super.replaceFragment(fragment);
        handler.post(new Runnable() {
            @Override
            public void run() {
                toolbarTitle.setText(fragment.getTitle().toUpperCase());
            }
        });
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void centerLogoInDrawerHeader(){
        try{
            RelativeLayout header = (RelativeLayout) navigationView.getHeaderView(0);
            final ImageView logo = (ImageView) header.findViewById(R.id.logo);
            final int statusBarHeight = getStatusBarHeight();
            final int headerHeight = (int) getResources().getDimension(R.dimen.nav_header_height);

            ViewTreeObserver vto = logo.getViewTreeObserver();
            vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                public boolean onPreDraw() {
                    try{
                        logo.getViewTreeObserver().removeOnPreDrawListener(this);
                        int logoHeight = logo.getMeasuredHeight();
                        logo.setY((headerHeight - statusBarHeight - logoHeight)/2 + statusBarHeight);
                    }catch(Exception e){
                        Log.e(TAG, "", e);
                    }
                    return true;
                }
            });
        }catch(Exception e){
            Log.e(TAG, "", e);
        }
    }
}
