package com.example.spoti5.ecobussing.CompanySwipe;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by matildahorppu on 09/10/15.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private CharSequence titles[];
    private int nbrTabs;


    public ViewPagerAdapter(FragmentManager fm, CharSequence titles[], int nbrTabs) {
        super(fm);
        this.titles = titles;
        this.nbrTabs = nbrTabs;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment;

        if (position == 0) {
            fragment = new ConnectCompanyFragment();
        } else {
            fragment = new CreateCompanyFragment();
        }


        return fragment;
    }

    public CharSequence getPageTitle(int position) {

        CharSequence tabTitle = "";

        switch (position) {
            case 0:
                tabTitle = titles[0];
                break;
            case 1:
                tabTitle = titles[1];
                break;
        }

        return tabTitle;
    }

    @Override
    public int getCount() {
        return nbrTabs;
    }
}
