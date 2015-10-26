package com.example.spoti5.ecobussing.controller.adapters.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.spoti5.ecobussing.model.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.view.BarDiagram;

/**
 * This is the pager adapter for the ViewPager in ProfileView. New fragments are added in getItem().
 * When this is done there also need to be more cases added in onPageSelected() in ProfileView.
 * Also getCount needs a change.
 *
 * Created by Hampus on 2015-10-12.
 */
public class ProfilePagerAdapter extends FragmentStatePagerAdapter {

    private IProfile thisProfile;
    private boolean isPointsMoney;

    public ProfilePagerAdapter(FragmentManager fragment, IProfile profile, boolean isPointsMoney) {
        super(fragment);
        thisProfile = profile;
        this.isPointsMoney = isPointsMoney;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch(i){
            case 0:
                fragment = BarDiagram.newInstance(thisProfile, BarDiagram.LAST_SEVEN_DAYS, isPointsMoney);
                break;
            case 1:
                fragment = BarDiagram.newInstance(thisProfile, BarDiagram.LAST_SEVEN_WEEKS, isPointsMoney);
                break;
            case 2:
                fragment = BarDiagram.newInstance(thisProfile, BarDiagram.LAST_SEVEN_MONTHS, isPointsMoney);
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

