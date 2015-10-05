package com.example.spoti5.ecobussing.SwipeScreens;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by emilaxelsson on 03/10/15.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fragment) {
        super(fragment);
    }



    @Override
    public Fragment getItem(int i) {
        System.out.println("get item called");
        Fragment fragment = new TestFragment();
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT "; //dummy title
    }

}
