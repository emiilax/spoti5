package com.example.spoti5.ecobussing.Medals;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by emilaxelsson on 17/10/15.
 */
public class MedalPagerAdapter extends FragmentStatePagerAdapter {

    private FragmentStatePagerAdapter mPagerAdapter;

    public MedalPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:

                break;

        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
