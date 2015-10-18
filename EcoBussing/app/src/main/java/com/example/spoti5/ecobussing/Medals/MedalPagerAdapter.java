package com.example.spoti5.ecobussing.Medals;

import android.provider.Settings;
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
        Fragment fragment = null;
        switch (position){

            case 0:
                fragment = MedalFragment.getInstance(MedalFragment.USER_MEDALS);
                break;
            case 1:
                fragment = MedalFragment.getInstance(MedalFragment.COMPANY_MEDALS);
                break;
            case 2:
                fragment = MedalFragment.getInstance(MedalFragment.GLOBAL_MEDALS);
                break;

        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String category = "";
        switch(position){
            case 0:
                category = "Personer";
                break;

            case 1:
                category = "Företag";
                break;
            case 2:
                category = "Global";
        }
        return category; //dummy title
    }
}
