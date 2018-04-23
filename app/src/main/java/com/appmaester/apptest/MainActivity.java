package com.appmaester.apptest;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.MenuItem;
import android.view.View;

import com.appmaester.apptest.Helpers.CustomTypefaceSpan;

/**
 * Created by Nik on 4/23/2018.
 */

public class MainActivity extends AppCompatActivity {

    NavigationView nav_view;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initLeftMenu();
        nav_view.getMenu().performIdentifierAction(R.id.left_item_home,0);
    }

    private void initLeftMenu() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav_view = (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                Fragment frag = null;
                switch (item.getItemId()) {
                    case R.id.left_item_home:
                        frag = HomeFragment.newInstance();
                        setFragment(frag);
                        setActionBarTitle(getString(R.string.app_name));
                        drawer.closeDrawers();
                        return true;
                    case R.id.left_item_favorites:
                        frag = SavedFragment.newInstance();
                        setFragment(frag);
                        setActionBarTitle(getString(R.string.menu_favorites));
                        drawer.closeDrawers();
                        return true;
                }
                drawer.closeDrawers();
                return true;
            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);



    }

    protected void setFragment(Fragment fragment) {
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.content_frame, fragment, fragment.getTag());
        t.commit();
    }

    private void setActionBarTitle(String title){
        Typeface font = Typeface.createFromAsset(this.getAssets(), "fonts/GillSansUltraBold.ttf");
        SpannableStringBuilder ssb = new SpannableStringBuilder(title);
        ssb.setSpan(new CustomTypefaceSpan("", font), 0, title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(ssb);
    }

}
