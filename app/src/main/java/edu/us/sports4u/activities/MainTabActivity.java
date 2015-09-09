package edu.us.sports4u.activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import edu.us.sports4u.R;
import edu.us.sports4u.activities.autorization.LogInActivity;
import edu.us.sports4u.api.BaseActivity;
import edu.us.sports4u.entities.Event;

import java.util.Date;

public class MainTabActivity extends FragmentActivity {
    ViewPager tab;
    TabPagerAdapter tabAdapter;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        tabAdapter = new TabPagerAdapter(getSupportFragmentManager());

        tab = (ViewPager) findViewById(R.id.pager);

        tab.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar = getActionBar();
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        tab.setAdapter(tabAdapter);

        actionBar = getActionBar();
        //Enable Tabs on Action Bar
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabReselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                MainTabActivity.this.tab.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(android.app.ActionBar.Tab tab,
                                        FragmentTransaction ft) {
                // TODO Auto-generated method stub
            }
        };

        //Add New tab
        actionBar.addTab(actionBar.newTab().setText("Events").setTabListener(tabListener));
        //    Intent listEvent = new Intent(this, ListEventActivity.class);
        actionBar.addTab(actionBar.newTab().setText("Calendar").setTabListener(tabListener));
        //    Intent listCalendar = new Intent(this, ListCalendarActivity.class);
      //  actionBar.addTab(actionBar.newTab().setText("Messages").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Options").setTabListener(tabListener));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.create_event_menu, menu);

        return true;
    }

    protected void signOut() {
        BaseActivity.clearApiKey();
        Intent activity = new Intent(this, LogInActivity.class);
        startActivity(activity);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                return true;
            case R.id.sign_out:
                MainTabActivity.this.signOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}