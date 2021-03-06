package com.example.spoti5.ecobussing.controller.adapters.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.spoti5.ecobussing.controller.profile.interfaces.IProfile;
import com.example.spoti5.ecobussing.controller.viewcontroller.fragments.BarDiagramFragment;

/**
 * This is the pager adapter for the ViewPager in ProfileViewFragment. New fragments are added in getItem().
 * When this is done there also need to be more cases added in onPageSelected() in ProfileViewFragment.
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
                fragment = BarDiagramFragment.newInstance(thisProfile, BarDiagramFragment.LAST_SEVEN_DAYS, isPointsMoney);
                break;
            case 1:
                fragment = BarDiagramFragment.newInstance(thisProfile, BarDiagramFragment.LAST_SEVEN_WEEKS, isPointsMoney);
                break;
            case 2:
                fragment = BarDiagramFragment.newInstance(thisProfile, BarDiagramFragment.LAST_SEVEN_MONTHS, isPointsMoney);

                break;
        }

        return fragment;
    }
    // Used to update the views
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
        //return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return 3;
    }
}

