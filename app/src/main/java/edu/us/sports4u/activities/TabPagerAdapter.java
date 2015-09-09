package edu.us.sports4u.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import edu.us.sports4u.activities.tabs.AccountTab;
import edu.us.sports4u.activities.tabs.CalendarTab;
import edu.us.sports4u.activities.tabs.EventsTab;
import edu.us.sports4u.activities.tabs.MessagesTab;

import java.util.ArrayList;
import java.util.List;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    protected List<Fragment> tabs;

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);

        tabs = new ArrayList<Fragment>();
        tabs.add(new EventsTab());
        tabs.add(new CalendarTab());
      //  tabs.add(new MessagesTab());
        tabs.add(new AccountTab());
    }

    @Override
    public Fragment getItem(int i) {
        if (i > -1 && i < tabs.size())
            return tabs.get(i);

        return null;
    }

    @Override
    public int getCount() {
        return tabs.size(); //No of Tabs
    }
}